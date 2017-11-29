package com.kmob.paysdk.wxpay.service;

import java.util.Map;

import com.kmob.paysdk.dto.ResultInfo;

/**
 * 支付通知回调
 *
 * @author verne
 */
public interface NotifyService {
    /**
     * 支付通知回调业务处理
     * 
     * @param datas
     * @return
     */
    ResultInfo notifyHandler(Map<String,String> datas);
}
