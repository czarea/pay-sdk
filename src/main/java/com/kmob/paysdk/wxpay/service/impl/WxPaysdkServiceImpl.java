package com.kmob.paysdk.wxpay.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.kmob.paysdk.dto.ResultInfo;
import com.kmob.paysdk.exception.WxPayException;
import com.kmob.paysdk.util.BeanUtils;
import com.kmob.paysdk.util.MapUtil;
import com.kmob.paysdk.util.Underline2Camel;
import com.kmob.paysdk.wxpay.ParameterKeyConstants;
import com.kmob.paysdk.wxpay.config.WxPayConfig;
import com.kmob.paysdk.wxpay.constant.WxPayConstants;
import com.kmob.paysdk.wxpay.constant.WxPayConstants.SignType;
import com.kmob.paysdk.wxpay.request.WxEntrustRequest;
import com.kmob.paysdk.wxpay.request.WxPayBaseRequest;
import com.kmob.paysdk.wxpay.request.WxPayMicropayRequest;
import com.kmob.paysdk.wxpay.request.WxPayOrderCloseRequest;
import com.kmob.paysdk.wxpay.request.WxPayOrderQueryRequest;
import com.kmob.paysdk.wxpay.request.WxPayOrderReverseRequest;
import com.kmob.paysdk.wxpay.request.WxPayRefundQueryRequest;
import com.kmob.paysdk.wxpay.request.WxPayRefundRequest;
import com.kmob.paysdk.wxpay.request.WxPayUnifiedOrderRequest;
import com.kmob.paysdk.wxpay.response.WxNotifyResponse;
import com.kmob.paysdk.wxpay.response.WxPayMicropayResponse;
import com.kmob.paysdk.wxpay.response.WxPayOrderCloseResponse;
import com.kmob.paysdk.wxpay.response.WxPayOrderQueryResponse;
import com.kmob.paysdk.wxpay.response.WxPayRefundQueryResponse;
import com.kmob.paysdk.wxpay.response.WxPayRefundResponse;
import com.kmob.paysdk.wxpay.response.WxPayReverseResponse;
import com.kmob.paysdk.wxpay.response.WxPayUnifiedOrderResponse;
import com.kmob.paysdk.wxpay.service.WxNotifyHandlerService;
import com.kmob.paysdk.wxpay.service.WxPaysdkService;
import com.kmob.paysdk.wxpay.transport.WxPayUtil;

/**
 * 微信支付sdk服务类
 *
 * @author verne
 */
public class WxPaysdkServiceImpl implements WxPaysdkService {
    private static final Logger logger = LoggerFactory.getLogger(WxPaysdkServiceImpl.class);

    private static final String WX_RESULT_SUCCESS = "SUCCESS";
    private static final String PACKAGE_VALUE = "Sign=WXPay";

    /**
     * 返回错误实体
     */
    private final static int UNIFIED_ORDER_ERROR = 300;

    private WxPayConfig weixinPayConfig;
    private SignType signType;

    public WxPaysdkServiceImpl(WxPayConfig weixinPayConfig) throws Exception {
        this.weixinPayConfig = weixinPayConfig;
        if (weixinPayConfig.isUseSandbox()) {
            this.signType = SignType.MD5; // 沙箱环境
        } else {
            this.signType = SignType.HMACSHA256;
        }
    }

    @Override
    public ResultInfo appPay(WxPayUnifiedOrderRequest request) throws Exception {
        ResultInfo result = new ResultInfo();
        WxPayUnifiedOrderResponse response = unifiedOrder(request);
        if (!WX_RESULT_SUCCESS.equals(response.getResultCode())) {
            result.setCode(UNIFIED_ORDER_ERROR);
            result.setMsg(response.getReturnMsg());
            return result;
        }

        SortedMap<String, String> parameters = new TreeMap<String, String>();

        String timestamp = String.valueOf(WxPayUtil.getCurrentTimestamp());
        parameters.put(ParameterKeyConstants.appPay.APPID, weixinPayConfig.getAppId());
        parameters.put(ParameterKeyConstants.appPay.PARTNERID, weixinPayConfig.getMchID());
        parameters.put(ParameterKeyConstants.appPay.PREPAYID, response.getPrepayId());
        parameters.put(ParameterKeyConstants.appPay.PACKAGE_VALUE, PACKAGE_VALUE);
        parameters.put(ParameterKeyConstants.appPay.NONCESTR, response.getNonceStr());
        parameters.put(ParameterKeyConstants.appPay.TIMESTAMP, timestamp);
        StringBuffer sb = new StringBuffer();
        Set<Entry<String, String>> es = parameters.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k);
                sb.append("=");
                sb.append(v);
                sb.append("&");
            }
        }
        sb.append("key=" + weixinPayConfig.getKey());

        String sign = WxPayUtil.HMACSHA256(sb.toString(), weixinPayConfig.getKey());
        parameters.put(ParameterKeyConstants.appPay.SIGN, sign);
        result.setData(parameters);
        return result;
    }


    @Override
    public WxPayUnifiedOrderResponse unifiedOrder(WxPayUnifiedOrderRequest request)
            throws Exception {
        request.checkFields();
        if (StringUtils.isEmpty(request.getNotifyURL())) {
            request.setNotifyURL(weixinPayConfig.getNotifyUrl());
        }
        fillRequestData(request);
        String requestStr = request.toXML();
        String url;
        if (weixinPayConfig.isUseSandbox()) {
            url = WxPayConstants.SANDBOX_UNIFIEDORDER_URL_SUFFIX;
        } else {
            url = WxPayConstants.UNIFIEDORDER_URL_SUFFIX;
        }
        String response = this.post(url, requestStr, false);

        return WxPayUnifiedOrderResponse.fromXML(response, WxPayUnifiedOrderResponse.class);
    }

    @Override
    public WxPayOrderQueryResponse queryOrder(String transactionId, String tradeNo)
            throws Exception {
        WxPayOrderQueryRequest request = new WxPayOrderQueryRequest();
        String url;
        if (weixinPayConfig.isUseSandbox()) {
            url = WxPayConstants.SANDBOX_ORDERQUERY_URL_SUFFIX;
        } else {
            url = WxPayConstants.ORDERQUERY_URL_SUFFIX;
        }
        if (!StringUtils.isEmpty(transactionId)) {
            request.setTransactionId(transactionId);
            fillRequestData(request);
            String requestStr = request.toXML();
            String responseStr = this.post(url, requestStr, false);
            return WxPayOrderQueryResponse.fromXML(responseStr, WxPayOrderQueryResponse.class);
        } else if (!StringUtils.isEmpty(tradeNo)) {
            request.setOutTradeNo(tradeNo);
            fillRequestData(request);
            String requestStr = request.toXML();
            String responseStr = this.post(url, requestStr, false);
            return WxPayOrderQueryResponse.fromXML(responseStr, WxPayOrderQueryResponse.class);

        } else {
            WxPayOrderQueryResponse error = new WxPayOrderQueryResponse();
            error.setResultCode("failed");
            error.setReturnMsg("参数不能为空！");
            return error;
        }
    }

    @Override
    public WxPayOrderCloseResponse closedOrder(String outTradeNo) throws Exception {
        WxPayOrderCloseRequest request = new WxPayOrderCloseRequest();
        request.setOutTradeNo(outTradeNo);
        fillRequestData(request);
        String url;
        if (weixinPayConfig.isUseSandbox()) {
            url = WxPayConstants.SANDBOX_CLOSEORDER_URL_SUFFIX;
        } else {
            url = WxPayConstants.CLOSEORDER_URL_SUFFIX;
        }
        String responseContent = this.post(url, request.toXML(), false);
        WxPayOrderCloseResponse result =
                WxPayOrderCloseResponse.fromXML(responseContent, WxPayOrderCloseResponse.class);
        return result;
    }

    @Override
    public WxPayReverseResponse reverseOrder(String transactionId, String outTradeNo)
            throws Exception {
        WxPayOrderReverseRequest request = new WxPayOrderReverseRequest();
        String url;
        if (weixinPayConfig.isUseSandbox()) {
            url = WxPayConstants.SANDBOX_REVERSE_URL_SUFFIX;
        } else {
            url = WxPayConstants.REVERSE_URL_SUFFIX;
        }
        if (!StringUtils.isEmpty(transactionId)) {
            request.setTransactionId(transactionId);
            fillRequestData(request);
            String responseContent = this.post(url, request.toXML(), false);
            WxPayReverseResponse result =
                    WxPayReverseResponse.fromXML(responseContent, WxPayReverseResponse.class);
            return result;
        } else if (!StringUtils.isEmpty(outTradeNo)) {
            request.setOutTradeNo(outTradeNo);
            fillRequestData(request);
            String responseContent = this.post(url, request.toXML(), false);
            WxPayReverseResponse result =
                    WxPayReverseResponse.fromXML(responseContent, WxPayReverseResponse.class);
            return result;
        } else {
            WxPayReverseResponse error = new WxPayReverseResponse();
            error.setResultCode("failed");
            error.setReturnMsg("参数不能为空！");
            return error;
        }
    }

    @Override
    public WxPayRefundResponse refund(WxPayRefundRequest request) throws Exception {
        String url;
        if (weixinPayConfig.isUseSandbox()) {
            url = WxPayConstants.SANDBOX_REFUND_URL_SUFFIX;
        } else {
            url = WxPayConstants.REFUND_URL_SUFFIX;
        }
        fillRequestData(request);
        fillRequestData(request);
        String responseContent = this.post(url, request.toXML(), false);
        WxPayRefundResponse result =
                WxPayRefundResponse.fromXML(responseContent, WxPayRefundResponse.class);
        return result;
    }

    @Override
    public WxPayRefundQueryResponse refundQuery(WxPayRefundQueryRequest request) throws Exception {
        String url;
        if (weixinPayConfig.isUseSandbox()) {
            url = WxPayConstants.SANDBOX_REFUND_URL_SUFFIX;
        } else {
            url = WxPayConstants.REFUNDQUERY_URL_SUFFIX;
        }
        fillRequestData(request);
        String requestStr = request.toXML();
        String response = this.post(url, requestStr, false);
        return WxPayRefundQueryResponse.fromXML(response, WxPayRefundQueryResponse.class);
    }

    @Override
    public WxNotifyResponse notify(HttpServletRequest request, HttpServletResponse response,
            WxNotifyHandlerService notifyService) throws Exception {
        PrintWriter writer = response.getWriter();
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");

        if (StringUtils.isEmpty(result)) {
            writer.write("request parameter is empty!");
            throw new Exception("weixin notify request parameter is empty!");
        }

        Map<String, String> notifyRequestData = WxPayUtil.xmlToMap(result);


        WxNotifyResponse notifyResponse = notifyService.notifyHandler(notifyRequestData);

        return notifyResponse;
    }

    private void fillRequestData(WxPayBaseRequest request) throws Exception {
        request.setAppid(weixinPayConfig.getAppID());
        request.setMchId(weixinPayConfig.getMchID());
        request.setNonceStr(WxPayUtil.generateUUID());
        if (weixinPayConfig.isUseSandbox()) {
            request.setSignType(WxPayConstants.MD5);
        } else {
            request.setSignType(WxPayConstants.HMACSHA256);
        }
        Map<String, String> reqData = BeanUtils.xmlBean2Map(request);
        String sign = WxPayUtil.generateSignature(reqData, weixinPayConfig.getKey(), signType);
        request.checkFields();
        request.setSign(sign);
    }

    protected String post(String url, String requestStr, boolean ssl) throws WxPayException {
        try {
            long start = System.currentTimeMillis();
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            if (ssl) {
                SSLContext sslContext = SSLContext.getInstance("TLS");

                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                        new String[] {"TLSv1"}, null, new DefaultHostnameVerifier());
                httpClientBuilder.setSSLSocketFactory(sslsf);
            }
            url = WxPayConstants.WX_PAY_BASE_URL + url;
            HttpPost httpPost = new HttpPost(url);

            httpPost.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(weixinPayConfig.getHttpConnectTimeoutMs())
                    .setConnectTimeout(weixinPayConfig.getHttpConnectTimeoutMs())
                    .setSocketTimeout(weixinPayConfig.getHttpReadTimeoutMs()).build());

            try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
                httpPost.setEntity(new StringEntity(new String(
                        requestStr.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
                try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                    String responseString =
                            EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    long end = System.currentTimeMillis();
                    logger.debug("\n【请求地址】：{}\n【请求数据】：\n{}\n【响应数据】：\n{}\n【耗时】\n{}", url, requestStr,
                            responseString, (end - start));
                    return responseString;
                }
            } finally {
                httpPost.releaseConnection();
            }
        } catch (Exception e) {
            logger.error("\n【请求地址】：{}\n【请求数据】：\n{}\n【响应数据】：\n{}", url, requestStr, e);
            throw new WxPayException(e.getMessage(), e);
        }
    }

    @Override
    public WxPayOrderQueryResponse micropay(WxPayMicropayRequest request) throws Exception {
        fillRequestData(request);
        String url = "/pay/micropay";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayMicropayResponse result =
                WxPayMicropayResponse.fromXML(responseContent, WxPayMicropayResponse.class);

        WxPayOrderQueryResponse response = new WxPayOrderQueryResponse();

        org.apache.commons.beanutils.BeanUtils.copyProperties(response, result);

        if ("SUCCESS".equals(result.getReturnCode())) {
            String resultCode = result.getResultCode();
            String errCode = result.getErrCode();
            if (resultCode.equals("SUCCESS")) {
                return response;
            } else {
                if (errCode.equals("USERPAYING")) {
                    for (int i = 0; i < weixinPayConfig.getMicropayTimes(); i++) {
                        response = this.queryOrder("", request.getOutTradeNo());
                        logger.info(
                                "WXmicropay return error ,retry {} times query order ,the result is {}",
                                (i + 1), response);
                        String tradeState = response.getTradeState();
                        if (tradeState != null && tradeState.equals("SUCCESS")) {
                            return response;
                        }
                        Thread.sleep(weixinPayConfig.getMicropayInterval());
                    }
                }
            }
        }

        return response;
    }

    @Override
    public String entrustWeb(WxEntrustRequest request) throws Exception {
        request.setAppid(weixinPayConfig.getAppID());
        request.setMchId(weixinPayConfig.getMchID());
        request.setSignType(WxPayConstants.MD5);
        Map<String, String> reqData = BeanUtils.xmlBean2Map(request);
        String sign = WxPayUtil.generateSignature(reqData, weixinPayConfig.getKey(), signType);
        request.checkFields();
        request.setSign(sign);
        
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(WxPayConstants.WX_PAY_BASE_URL + WxPayConstants.ENTRUSTWEB_URL_SUFFIX);
        Map<String, String> datas = MapUtil.beanToStringMap(request);
        Set<Entry<String, String>> entries = datas.entrySet();
        boolean first = true;
        for (Entry<String, String> entry : entries) {
            String name = Underline2Camel.camelToUnderline(entry.getKey());
            String value = entry.getValue();
            if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
                if (first) {
                    first = false;
                } else {
                    urlBuffer.append("&");
                }
                if (value.startsWith("http") || value.startsWith("https")) {
                    urlBuffer.append(name).append("=").append(URLEncoder.encode(value, "UTF-8"));
                } else {
                    urlBuffer.append(name).append("=").append(value);
                }
            }
        }
        return urlBuffer.toString();
    }

}
