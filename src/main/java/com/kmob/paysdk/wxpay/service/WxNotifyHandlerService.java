package com.kmob.paysdk.wxpay.service;

import java.util.Map;

import com.kmob.paysdk.wxpay.response.WxNotifyResponse;

/**
 * 支付通知回调
 *
 * @author verne
 */
public interface WxNotifyHandlerService {
    /**
     * 支付通知回调业务处理
     * 
     * @param datas
     * @return
     */
    WxNotifyResponse notifyHandler(Map<String,String> datas);
}
