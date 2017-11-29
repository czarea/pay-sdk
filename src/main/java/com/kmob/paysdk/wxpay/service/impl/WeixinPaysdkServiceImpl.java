package com.kmob.paysdk.wxpay.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
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
import com.kmob.paysdk.wxpay.config.WeixinPayConfig;
import com.kmob.paysdk.wxpay.model.ParameterConstans;
import com.kmob.paysdk.wxpay.model.WeixinDownLoadBillRequest;
import com.kmob.paysdk.wxpay.model.WeixinNotifyResponse;
import com.kmob.paysdk.wxpay.model.WeixinPayRequest;
import com.kmob.paysdk.wxpay.model.WeixinRefundRequest;
import com.kmob.paysdk.wxpay.service.NotifyService;
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
    
    private static final String BODY_KEY = "body";
    private static final String OUT_TRADE_NO_KEY = "out_trade_no";
    private static final String DEVICE_INFO_KEY = "device_info";
    private static final String FEE_TYPE_KEY = "fee_type";
    private static final String TOTAL_FEE_KEY = "total_fee";
    private static final String SPBILL_CREATE_IP_KEY = "spbill_create_ip";
    private static final String NOTIFY_URL_KEY = "notify_url";
    private static final String TRADE_TYPE_KEY = "trade_type";
    private static final String PRODUCT_ID_KEY = "product_id";
    private static final String WX_RESULT_SUCCESS = "SUCCESS";
    private static final String TOTALFEE_KEY = "totalAmount";
    private static final String PACKAGE_VALUE = "Sign=WXPay";
    private static final String WX_RESULT_MSG_KEY = "return_code";
    private static final String WX_RESULT_CODE_KEY = "result_code";
    private static final String TRANSACTION_ID_KEY = "transaction_id";
    
    
    
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
        if (!WX_RESULT_SUCCESS.equals(unifiedOrderResponse.get(WX_RESULT_CODE_KEY))) {
            result.setCode(UNIFIED_ORDER_ERROR);
            result.setMsg(unifiedOrderResponse.get(WX_RESULT_MSG_KEY));
            return result;
        }
        
        SortedMap<String,String> parameters = new TreeMap<String,String>();
        
        String timestamp = String.valueOf(WeixinPayUtil.getCurrentTimestamp());
        parameters.put(ParameterConstans.appPay.APPID, unifiedOrderResponse.get("appid"));
        parameters.put(ParameterConstans.appPay.PARTNERID, unifiedOrderResponse.get("mch_id"));
        parameters.put(ParameterConstans.appPay.PREPAYID, unifiedOrderResponse.get("prepay_id"));
        parameters.put(ParameterConstans.appPay.PACKAGE_VALUE, PACKAGE_VALUE);
        parameters.put(ParameterConstans.appPay.NONCESTR, unifiedOrderResponse.get("nonce_str"));
        parameters.put(ParameterConstans.appPay.TIMESTAMP, timestamp);
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
        parameters.put(ParameterConstans.appPay.SIGN, sign);
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
    public Map<String, String> queryOrder(String tradeNo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> closedOrder(String outTradeNo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> reverseOrder(String transactionId, String outTradeNo)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> refund(WeixinRefundRequest request) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> refundQuery(WeixinRefundRequest request) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> downloadBill(WeixinDownLoadBillRequest request) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WeixinNotifyResponse notify(HttpServletRequest request, HttpServletResponse response,
            NotifyService notifyService) throws Exception{
        // TODO Auto-generated method stub
        WeixinNotifyResponse res = new WeixinNotifyResponse();
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
            return res;
        }

        Map<String, String> map = WeixinPayUtil.xmlToMap(result);
        
        return res;
    }

}
