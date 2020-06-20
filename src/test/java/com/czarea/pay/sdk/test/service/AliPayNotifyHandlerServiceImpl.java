package com.czarea.pay.sdk.test.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.czarea.pay.sdk.alipay.service.AliPayNotifyHandlerService;

/**
 * 实现支付宝支付结果通知
 *
 * @author zhouzx
 */
@Component
public class AliPayNotifyHandlerServiceImpl implements AliPayNotifyHandlerService {

    /**
     * 业务处理
     */
    @Override
    public String handler(Map<String, String> datas) {
        return "success";
    }

}
