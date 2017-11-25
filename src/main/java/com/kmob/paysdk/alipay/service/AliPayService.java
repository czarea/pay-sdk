package com.kmob.paysdk.alipay.service;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.kmob.paysdk.alipay.model.AliPayRequest;

/**
 * 支付宝支付抽象接口类
 *
 * @author verne
 */
public interface AliPayService {
    /**
     * APP支付
     * 
     * @param aliPayModel 支付请求实体
     * @return alipay.trade.app.pay response {@link AlipayTradeAppPayResponse}
     * @throws Exception 支付请求出错时抛出异常
     */
    AlipayTradeAppPayResponse appPay(AliPayRequest aliPayModel) throws Exception;
}
