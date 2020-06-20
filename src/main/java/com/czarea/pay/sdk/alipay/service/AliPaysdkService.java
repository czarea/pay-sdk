package com.czarea.pay.sdk.alipay.service;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/**
 * 支付宝支付抽象接口类
 *
 * @author zhouzx
 */
public interface AliPaysdkService {

    /**
     * APP支付
     *
     * @param appPayModel 支付请求实体
     * @return alipay.trade.app.pay response {@link AlipayTradeAppPayResponse}
     * @throws Exception 支付请求出错时抛出异常
     */
    AlipayTradeAppPayResponse appPay(AlipayTradeAppPayModel appPayModel) throws Exception;
}
