package com.czarea.pay.sdk.wxpay.service;

import com.czarea.pay.sdk.wxpay.response.WxNotifyResponse;
import java.util.Map;

/**
 * 支付通知回调
 *
 * @author zhouzx
 */
public interface WxNotifyHandlerService {

    /**
     * 支付通知回调业务处理
     */
    WxNotifyResponse notifyHandler(Map<String, String> datas);
}
