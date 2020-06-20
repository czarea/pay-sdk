package com.czarea.pay.sdk.test.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.czarea.pay.sdk.wxpay.response.WxNotifyResponse;
import com.czarea.pay.sdk.wxpay.service.WxNotifyHandlerService;

/**
 * 实现微信支付通知业务处理
 *
 * @author zhouzx
 */
@Service
public class WeixinNotifyHandlerServiceImpl implements WxNotifyHandlerService {

    @Override
    public WxNotifyResponse notifyHandler(Map<String, String> datas) {
        // TODO Auto-generated method stub
        WxNotifyResponse res = new WxNotifyResponse();
        res.setReturn_code("SUCCESS");
        res.setReturn_msg("SUCCESS");
        return res;
    }

}
