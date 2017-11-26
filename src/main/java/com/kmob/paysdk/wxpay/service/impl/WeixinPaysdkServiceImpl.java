package com.kmob.paysdk.wxpay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmob.paysdk.wxpay.config.WeixinPayConfig;
import com.kmob.paysdk.wxpay.model.WeixinDownLoadBillRequest;
import com.kmob.paysdk.wxpay.model.WeixinPayRequest;
import com.kmob.paysdk.wxpay.model.WeixinRefundRequest;
import com.kmob.paysdk.wxpay.service.WeixinPaysdkService;
import com.kmob.paysdk.wxpay.transport.WeixinPay;

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
    
    private WeixinPay weixinpay;
    private WeixinPayConfig weixinPayConfig;
    
    public WeixinPaysdkServiceImpl(WeixinPayConfig weixinPayConfig) throws Exception {
        weixinpay = new WeixinPay(weixinPayConfig);
        this.weixinPayConfig = weixinPayConfig;
    }

    @Override
    public Map<String, String> unifiedOrder(WeixinPayRequest weixinPayModel) throws Exception {
        Map<String, String> data =  new HashMap<String,String>();
        data.put(BODY_KEY, weixinPayModel.getBody());
        data.put(OUT_TRADE_NO_KEY, weixinPayModel.getOutTradeNo());
        data.put(DEVICE_INFO_KEY, weixinPayModel.getDeviceInfo());
        data.put(FEE_TYPE_KEY, weixinPayModel.getFeeType());
        data.put(TOTAL_FEE_KEY, weixinPayModel.getTotalAmount());
        data.put(SPBILL_CREATE_IP_KEY, weixinPayModel.getSpbillCreateIp());
        data.put(NOTIFY_URL_KEY, weixinPayConfig.getNotifyUrl());
        data.put(TRADE_TYPE_KEY, weixinPayModel.getTradeType());
        data.put(PRODUCT_ID_KEY, weixinPayModel.getProductId());
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

}
