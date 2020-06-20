package com.czarea.pay.sdk.wxpay.controller;

import com.czarea.pay.sdk.util.MapUtil;
import com.czarea.pay.sdk.wxpay.response.WxNotifyResponse;
import com.czarea.pay.sdk.wxpay.service.WxNotifyHandlerService;
import com.czarea.pay.sdk.wxpay.transport.WxPayUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 微信支付通知
 *
 * @author zhouzx
 */
@Configuration
@ConditionalOnProperty(prefix = "wxpay", name = "useController")
@ConditionalOnBean(WxNotifyHandlerService.class)
@Controller
@RequestMapping("/weixinPay")
public class WxPayNotifyHandlerController {

    private static final Logger logger =
        LoggerFactory.getLogger(WxPayNotifyHandlerController.class);
    private WxNotifyHandlerService weixinNotifyHandlerService;

    public WxPayNotifyHandlerController(WxNotifyHandlerService weixinNotifyHandlerService) {
        this.weixinNotifyHandlerService = weixinNotifyHandlerService;
    }

    /**
     * 微信支付通知处理
     */
    @PostMapping("notify")
    public void payback(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        PrintWriter writer = response.getWriter();
        InputStream inStream = request.getInputStream();
        response.addHeader("Content-Type", "text/xml");
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        logger.debug("weixin notify request xml data is {}", result);
        if (StringUtils.isEmpty(result)) {
            writer.write(WxNotifyResponse.errorXml());
            writer.flush();
            return;
        }

        Map<String, String> map = null;
        try {
            map = WxPayUtil.xmlToMap(result);
        } catch (Exception e) {
            logger.error("weixin notify request xml data is {}", result, e);
            writer.write(WxNotifyResponse.errorXml());
            writer.flush();
            return;
        }
        try {
            WxNotifyResponse responseNotify = weixinNotifyHandlerService.notifyHandler(map);

            String backToWeixinXml =
                WxPayUtil.mapToXml(MapUtil.beanToStringMap(responseNotify));
            logger.info("weixin payback notify xml is {} ,return to weixin xml is {}", result,
                backToWeixinXml);
            writer.write(backToWeixinXml);
            writer.flush();
        } catch (Exception e) {
            logger.error("wxpay order notify to xml string error ,the response is {}", result, e);
            writer.write(WxNotifyResponse.errorXml());
            writer.flush();
        }
    }
}
