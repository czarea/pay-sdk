package com.kmob.paysdk.test.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmob.paysdk.alipay.service.AliPayNotifyHandlerService;

/**
 * 实现支付宝支付结果通知
 *
 * @author verne
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
