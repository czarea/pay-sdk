package com.kmob.paysdk.wxpay.service;

import java.util.Map;

import com.kmob.paysdk.wxpay.model.WeixinPayRequest;

/**
 * 微信支付抽象接口
 *
 * @author verne
 */
public interface WeixinPayService {
    /**
     * 统一下单
     * 
     * @param weixinPayModel
     * @return 下单结果xml解析后的map类型，详细参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @throws Exception
     */
    Map<String, String> unifiedOrder(WeixinPayRequest weixinPayModel) throws Exception;
    
    
    Map<String, String> queryOrder(WeixinPayRequest weixinPayModel) throws Exception;
    
    
    /**
     * 
     * @param outTradeNo
     * @return
     * @throws Exception
     */
    Map<String, String> closedOrder(String outTradeNo) throws Exception;
}
