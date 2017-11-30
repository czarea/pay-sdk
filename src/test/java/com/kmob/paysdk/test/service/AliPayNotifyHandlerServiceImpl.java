package com.kmob.paysdk.test.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmob.paysdk.alipay.service.AliPayNotifyHandlerService;

@Component
public class AliPayNotifyHandlerServiceImpl implements AliPayNotifyHandlerService {

    @Override
    public String handler(Map<String, String> datas) {
        return "success";
    }

}
