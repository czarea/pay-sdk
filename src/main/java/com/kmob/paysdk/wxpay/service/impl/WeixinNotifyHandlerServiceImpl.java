package com.kmob.paysdk.wxpay.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kmob.paysdk.wxpay.response.WeixinNotifyResponse;
import com.kmob.paysdk.wxpay.service.WeixinNotifyHandlerService;

@Service
public class WeixinNotifyHandlerServiceImpl implements WeixinNotifyHandlerService {

    @Override
    public WeixinNotifyResponse notifyHandler(Map<String, String> datas) {
        // TODO Auto-generated method stub
        WeixinNotifyResponse res = new WeixinNotifyResponse();
        res.setReturn_code("SUCCESS");
        res.setReturn_msg("SUCCESS");
        return res;
    }

}
