package com.kmob.paysdk.wxpay.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.kmob.paysdk.dto.ResultInfo;
import com.kmob.paysdk.util.MapperUtil;
import com.kmob.paysdk.wxpay.ParameterKeyConstants;
import com.kmob.paysdk.wxpay.config.WeixinPayConfig;
import com.kmob.paysdk.wxpay.request.WeixinDownLoadBillRequest;
import com.kmob.paysdk.wxpay.request.WeixinPayRequest;
import com.kmob.paysdk.wxpay.request.WeixinRefundRequest;
import com.kmob.paysdk.wxpay.response.WeixinNotifyResponse;
import com.kmob.paysdk.wxpay.service.WeixinNotifyHandlerService;
import com.kmob.paysdk.wxpay.service.WeixinPaysdkService;
import com.kmob.paysdk.wxpay.transport.WeixinPay;
import com.kmob.paysdk.wxpay.transport.WeixinPayUtil;

/**
 * 微信支付sdk服务类
 *
 * @author verne
 */
public class WeixinPaysdkServiceImpl implements WeixinPaysdkService{
    private static final Logger logger = LoggerFactory.getLogger(WeixinPaysdkServiceImpl.class);
    
    private static final String WX_RESULT_SUCCESS = "SUCCESS";
    private static final String PACKAGE_VALUE = "Sign=WXPay";
    
    
    
    /**
     * 返回错误实体
     */
    private final static int UNIFIED_ORDER_ERROR = 300;
    
    private WeixinPay weixinpay;
    private WeixinPayConfig weixinPayConfig;
    
    public WeixinPaysdkServiceImpl(WeixinPayConfig weixinPayConfig) throws Exception {
        weixinpay = new WeixinPay(weixinPayConfig);
        this.weixinPayConfig = weixinPayConfig;
    }
    
    @Override
    public ResultInfo appPay(Map<String, String> params) throws Exception {
        ResultInfo result = new ResultInfo();
        Map<String,String> unifiedOrderResponse = weixinpay.unifiedOrder(params);
        if (!WX_RESULT_SUCCESS.equals(unifiedOrderResponse.get(ParameterKeyConstants.unifiedResponse.RESULT_CODE))) {
            result.setCode(UNIFIED_ORDER_ERROR);
            result.setMsg(unifiedOrderResponse.get(ParameterKeyConstants.unifiedResponse.RETURN_MSG));
            return result;
        }
        
        SortedMap<String,String> parameters = new TreeMap<String,String>();
        
        String timestamp = String.valueOf(WeixinPayUtil.getCurrentTimestamp());
        parameters.put(ParameterKeyConstants.appPay.APPID, weixinPayConfig.getAppId());
        parameters.put(ParameterKeyConstants.appPay.PARTNERID, weixinPayConfig.getMchID());
        parameters.put(ParameterKeyConstants.appPay.PREPAYID, unifiedOrderResponse.get(ParameterKeyConstants.unifiedResponse.PREPAY_ID));
        parameters.put(ParameterKeyConstants.appPay.PACKAGE_VALUE, PACKAGE_VALUE);
        parameters.put(ParameterKeyConstants.appPay.NONCESTR, unifiedOrderResponse.get(ParameterKeyConstants.unifiedResponse.NONCE_STR));
        parameters.put(ParameterKeyConstants.appPay.TIMESTAMP, timestamp);
        StringBuffer sb = new StringBuffer();
        Set<Entry<String, String>> es = parameters.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        while(it.hasNext()) {
            Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k);
                sb.append("=");
                sb.append(v);
                sb.append("&");
            }
        }
        sb.append("key=" + weixinPayConfig.getKey());
        
        String sign = WeixinPayUtil.HMACSHA256(sb.toString(),weixinPayConfig.getKey());
        parameters.put(ParameterKeyConstants.appPay.SIGN, sign);
        result.setData(parameters);
        return result;
    }

    @Override
    public Map<String, String> unifiedOrder(Map<String, String> params) throws Exception {
        Map<String,String> response = weixinpay.unifiedOrder(params );
        logger.debug("weixin unified order request is {},response is {}",params,response);
        return response;
    }
    
    
    @Override
    public Map<String, String> unifiedOrder(WeixinPayRequest weixinPayModel) throws Exception {
        Map<String, String> data = MapperUtil.beanToStringMap(weixinPayModel);
        Map<String,String> response = weixinpay.unifiedOrder(data );
        
        logger.debug("weixin unified order request is {},response is {}",weixinPayModel,response);
        return response;
    }

    @Override
    public Map<String, String> queryOrder(String transactionId,String tradeNo) throws Exception {
        Map<String, String> reqData = new HashMap<String,String>();
        if(!StringUtils.isEmpty(transactionId)) {
            reqData.put(ParameterKeyConstants.notifyRequest.TRANSACTION_ID, transactionId);
            return weixinpay.orderQuery(reqData );
        }else if(!StringUtils.isEmpty(tradeNo)) {
            reqData.put(ParameterKeyConstants.notifyRequest.OUT_TRADE_NO, transactionId);
            return weixinpay.orderQuery(reqData );
        }else {
            reqData.put(ParameterKeyConstants.unifiedResponse.RETURN_CODE, "FAIL");
            reqData.put(ParameterKeyConstants.unifiedResponse.RETURN_MSG, "参数不能为空！");
            return reqData;
        }
    }

    @Override
    public Map<String, String> closedOrder(String outTradeNo) throws Exception {
        Map<String, String> reqData = new HashMap<String,String>();
        if(!StringUtils.isEmpty(outTradeNo)) {
            reqData.put(ParameterKeyConstants.notifyRequest.OUT_TRADE_NO, outTradeNo);
            return weixinpay.closeOrder(reqData );
        }else {
            reqData.put(ParameterKeyConstants.unifiedResponse.RETURN_CODE, "FAIL");
            reqData.put(ParameterKeyConstants.unifiedResponse.RETURN_MSG, "商户订单号不能为空！");
            return reqData;
        }
    }

    @Override
    public Map<String, String> reverseOrder(String transactionId, String outTradeNo)
            throws Exception {
        Map<String, String> reqData = new HashMap<String,String>();
        if(!StringUtils.isEmpty(outTradeNo)) {
            reqData.put(ParameterKeyConstants.notifyRequest.TRANSACTION_ID, transactionId);
            reqData.put(ParameterKeyConstants.notifyRequest.OUT_TRADE_NO, outTradeNo);
            return weixinpay.reverse(reqData );
        }else {
            reqData.put(ParameterKeyConstants.unifiedResponse.RETURN_CODE, "FAIL");
            reqData.put(ParameterKeyConstants.unifiedResponse.RETURN_MSG, "商户订单号不能为空！");
            return reqData;
        }
    }

    @Override
    public Map<String, String> refund(WeixinRefundRequest request) throws Exception {
        Map<String, String> reqData = MapperUtil.beanToStringMap(request);
        return weixinpay.refund(reqData );
    }

    @Override
    public Map<String, String> refundQuery(WeixinRefundRequest request) throws Exception {
        Map<String, String> reqData = MapperUtil.beanToStringMap(request);
        return weixinpay.refundQuery(reqData);
    }

    @Override
    public Map<String, String> downloadBill(WeixinDownLoadBillRequest request) throws Exception {
        // TODO Auto-generated method stub
        Map<String, String> reqData = MapperUtil.beanToStringMap(request);
        weixinpay.downloadBill(reqData);
        return null;
    }

    @Override
    public WeixinNotifyResponse notify(HttpServletRequest request, HttpServletResponse response,
            WeixinNotifyHandlerService notifyService) throws Exception{
        // TODO Auto-generated method stub
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

        Map<String, String> notifyRequestData = WeixinPayUtil.xmlToMap(result);
        
        
        WeixinNotifyResponse notifyResponse = notifyService.notifyHandler(notifyRequestData);
        
        return notifyResponse;
    }

}
