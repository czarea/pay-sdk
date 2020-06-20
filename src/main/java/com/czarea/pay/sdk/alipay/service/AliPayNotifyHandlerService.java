package com.czarea.pay.sdk.alipay.service;

import java.util.Map;

public interface AliPayNotifyHandlerService {

    /**
     * 支付结果通知
     *
     * @param datas 微信支付结果通知数据
     * @return 返回支付宝信息
     */
    String handler(Map<String, String> datas);
}
