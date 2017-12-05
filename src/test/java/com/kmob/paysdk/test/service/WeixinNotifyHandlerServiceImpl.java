package com.kmob.paysdk.test.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kmob.paysdk.wxpay.response.WxNotifyResponse;
import com.kmob.paysdk.wxpay.service.WxNotifyHandlerService;

/**
 * 实现微信支付通知业务处理
 *
 * @author verne
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
