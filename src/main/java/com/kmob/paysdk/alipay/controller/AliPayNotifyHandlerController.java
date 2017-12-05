package com.kmob.paysdk.alipay.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmob.paysdk.alipay.service.AliPayNotifyHandlerService;
import com.kmob.paysdk.wxpay.service.WxNotifyHandlerService;


/**
 * 支付宝支付通知
 *
 * @author verne
 */
@Configuration
@ConditionalOnProperty(prefix = "alipay", name = "useController")
@ConditionalOnBean(WxNotifyHandlerService.class)
@Controller
@RequestMapping("/alipay")
public class AliPayNotifyHandlerController {
    private static final Logger logger =
            LoggerFactory.getLogger(AliPayNotifyHandlerController.class);
    /**
     * 业务处理实现类
     */
    private AliPayNotifyHandlerService aliPayNotifyHandlerService;

    public AliPayNotifyHandlerController(AliPayNotifyHandlerService aliPayNotifyHandlerService) {
        this.aliPayNotifyHandlerService = aliPayNotifyHandlerService;
    }

    /**
     * 支付宝支付通知处理
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("payNotify")
    @ResponseBody
    public String payback(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();

        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1)
                        ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        String respontToAliPay = aliPayNotifyHandlerService.handler(params);

        logger.info("alipay notify data is {} ,return to alipay data is {}", params,
                respontToAliPay);
        return respontToAliPay;
    }
}
