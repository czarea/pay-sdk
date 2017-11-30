package com.kmob.paysdk.wxpay.controller;

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

import com.kmob.paysdk.util.MapperUtil;
import com.kmob.paysdk.wxpay.response.WeixinNotifyResponse;
import com.kmob.paysdk.wxpay.service.WeixinNotifyHandlerService;
import com.kmob.paysdk.wxpay.transport.WeixinPayUtil;


/**
 * 微信支付通知
 *
 * @author verne
 */
@Configuration
@ConditionalOnProperty(prefix = "wxpay", name = "useController")
@ConditionalOnBean(WeixinNotifyHandlerService.class)
@Controller
@RequestMapping("/weixinPay")
public class WeixinPayNotifyHandlerController {
    private static final Logger logger =
            LoggerFactory.getLogger(WeixinPayNotifyHandlerController.class);
    private WeixinNotifyHandlerService weixinNotifyHandlerService;

    public WeixinPayNotifyHandlerController(WeixinNotifyHandlerService weixinNotifyHandlerService) {
        this.weixinNotifyHandlerService = weixinNotifyHandlerService;
    }


    /**
     * 微信支付通知处理
     * 
     * @param request
     * @param response
     * @throws IOException
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
            writer.write("weixin notify request xml data is empty!");
            writer.flush();
            return;
        }

        Map<String, String> map = null;
        try {
            map = WeixinPayUtil.xmlToMap(result);
        } catch (Exception e) {
            logger.error("weixin notify request xml data is {}", result, e);
        }
        try {
            WeixinNotifyResponse responseNotify = weixinNotifyHandlerService.notifyHandler(map);

            String backToWeixinXml =
                    WeixinPayUtil.mapToXml(MapperUtil.beanToStringMap(responseNotify));
            logger.info("weixin payback notify xml is {} ,return to weixin xml is {}", result,
                    backToWeixinXml);
            writer.write(backToWeixinXml);
            writer.flush();
        } catch (Exception e) {
            logger.error("wxpay order notify to xml string error ,the response is {}", result, e);
        }
    }
}
