package com.kmob.paysdk.wxpay.service;

import java.util.Map;

import com.kmob.paysdk.wxpay.response.WeixinNotifyResponse;

/**
 * 支付通知回调
 *
 * @author verne
 */
public interface WeixinNotifyHandlerService {
    /**
     * 支付通知回调业务处理
     * 
     * @param datas
     * @return
     */
    WeixinNotifyResponse notifyHandler(Map<String,String> datas);
}
