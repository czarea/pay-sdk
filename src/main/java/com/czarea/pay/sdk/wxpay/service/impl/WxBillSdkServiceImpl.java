package com.czarea.pay.sdk.wxpay.service.impl;

import com.czarea.pay.sdk.exception.WxPayException;
import com.czarea.pay.sdk.util.BeanUtils;
import com.czarea.pay.sdk.util.WxBillUtil;
import com.czarea.pay.sdk.wxpay.config.WxPayConfig;
import com.czarea.pay.sdk.wxpay.constant.WxPayConstants;
import com.czarea.pay.sdk.wxpay.constant.WxPayConstants.SignType;
import com.czarea.pay.sdk.wxpay.request.WxPayBaseRequest;
import com.czarea.pay.sdk.wxpay.request.WxPayDownloadBillRequest;
import com.czarea.pay.sdk.wxpay.response.WxAllBillResponse;
import com.czarea.pay.sdk.wxpay.response.WxPayBillBaseResponse;
import com.czarea.pay.sdk.wxpay.response.WxRefundBillResponse;
import com.czarea.pay.sdk.wxpay.response.WxSuccessBillResponse;
import com.czarea.pay.sdk.wxpay.service.WxBillSdkService;
import com.czarea.pay.sdk.wxpay.transport.WxPayUtil;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxBillSdkServiceImpl implements WxBillSdkService {

    private static final Logger logger = LoggerFactory.getLogger(WxBillSdkServiceImpl.class);

    private WxPayConfig weixinPayConfig;
    private SignType signType;
    private String url;

    public WxBillSdkServiceImpl(WxPayConfig weixinPayConfig) {
        this.weixinPayConfig = weixinPayConfig;
        if (weixinPayConfig.isUseSandbox()) {
            this.signType = SignType.MD5; // 沙箱环境
            url = WxPayConstants.SANDBOX_DOWNLOADBILL_URL_SUFFIX;
        } else {
            this.signType = SignType.HMACSHA256;
            url = WxPayConstants.DOWNLOADBILL_URL_SUFFIX;
        }
    }

    @Override
    public WxPayBillBaseResponse downloadSuccessBill(WxPayDownloadBillRequest request, boolean gzip)
        throws Exception {
        WxPayBillBaseResponse response = new WxPayBillBaseResponse();
        fillRequestData(request);
        String requestStr = request.toXML();
        String httpResponse = this.post(url, requestStr, gzip);
        if (gzip) {
            response.setFileName(httpResponse);
        } else {
            List<WxSuccessBillResponse> sussceeList = WxBillUtil.getSuccessBill(httpResponse);
            response.setSussceeList(sussceeList);
        }
        return response;
    }

    @Override
    public WxPayBillBaseResponse downloadALLBill(WxPayDownloadBillRequest request, boolean gzip)
        throws Exception {
        WxPayBillBaseResponse response = new WxPayBillBaseResponse();
        fillRequestData(request);
        String requestStr = request.toXML();
        String httpResponse = this.post(url, requestStr, gzip);
        if (gzip) {
            response.setFileName(httpResponse);
        } else {
            List<WxAllBillResponse> sussceeList = WxBillUtil.getAllBill(httpResponse);
            response.setAllList(sussceeList);
        }
        return response;
    }

    @Override
    public WxPayBillBaseResponse downloadRefundBill(WxPayDownloadBillRequest request, boolean gzip)
        throws Exception {
        WxPayBillBaseResponse response = new WxPayBillBaseResponse();
        fillRequestData(request);
        String requestStr = request.toXML();
        String httpResponse = this.post(url, requestStr, gzip);
        if (gzip) {
            response.setFileName(httpResponse);
        } else {
            List<WxRefundBillResponse> sussceeList = WxBillUtil.getRefundBill(httpResponse);
            response.setRefundList(sussceeList);
        }
        return response;
    }

    protected String post(String url, String requestStr, boolean gzip) throws WxPayException {
        try {
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            url = WxPayConstants.WX_PAY_BASE_URL + url;
            HttpPost httpPost = new HttpPost(url);

            httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(weixinPayConfig.getHttpConnectTimeoutMs())
                .setConnectTimeout(weixinPayConfig.getHttpConnectTimeoutMs())
                .setSocketTimeout(weixinPayConfig.getHttpReadTimeoutMs()).build());

            try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
                httpPost.setEntity(new StringEntity(new String(
                    requestStr.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
                CloseableHttpResponse response = httpclient.execute(httpPost);

                Header[] contentTypeHeaders = response.getHeaders("Content-Type");
                boolean gzipContentType = false;

                for (Header head : contentTypeHeaders) {
                    if (head.getValue().contains("application/x-gzip")) {
                        gzipContentType = true;
                        break;
                    }
                }

                HttpEntity httpEntity = response.getEntity();
                if (!gzipContentType) {
                    String content = EntityUtils.toString(httpEntity, "UTF-8");
                    return content;
                } else {
                    // TODO 有可能会重复
                    SimpleDateFormat allTime = new SimpleDateFormat("yyyyMMddHHmmss");
                    String fileName = weixinPayConfig.getBillPath() + allTime.format(new Date())
                        + WxPayUtil.generateNonceStr() + ".gzip";
                    Files.copy(httpEntity.getContent(), Paths.get(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
                    return fileName;
                }
            } finally {
                httpPost.releaseConnection();
            }
        } catch (Exception e) {
            logger.error("\n【请求地址】：{}\n【请求数据】：\n{}\n【响应数据】：\n{}", url, requestStr, e);
            throw new WxPayException(e.getMessage(), e);
        }
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
        System.out.println(reqData);
        String sign = WxPayUtil.generateSignature(reqData, weixinPayConfig.getKey(), signType);
        request.checkFields();
        request.setSign(sign);
    }

}
