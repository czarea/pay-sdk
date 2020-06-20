package com.czarea.pay.sdk.spring.boot.starter;

import com.czarea.pay.sdk.alipay.config.AliPayConfig;
import com.czarea.pay.sdk.alipay.service.AliPaysdkService;
import com.czarea.pay.sdk.alipay.service.impl.AliPaysdkServiceImpl;
import com.czarea.pay.sdk.wxpay.config.WxPayConfig;
import com.czarea.pay.sdk.wxpay.service.WxBillSdkService;
import com.czarea.pay.sdk.wxpay.service.WxPaysdkService;
import com.czarea.pay.sdk.wxpay.service.impl.WxBillSdkServiceImpl;
import com.czarea.pay.sdk.wxpay.service.impl.WxPaysdkServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for paysdk
 *
 * @author zhouzx
 */
@Configuration
@EnableConfigurationProperties(value = {AliPayConfig.class, WxPayConfig.class})
public class PaysdkAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PaysdkAutoConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = "alipay", name = "use")
    public AliPaysdkService aliPayService(AliPayConfig aliPayConfig) {
        logger.debug("init AliPayService success!");
        return new AliPaysdkServiceImpl(aliPayConfig);
    }

    @Bean
    @ConditionalOnProperty(prefix = "wxpay", name = "use", havingValue = "true")
    public WxPaysdkService weixinPayService(WxPayConfig weixinPayConfig) throws Exception {
        logger.debug("init WeixinPaySDKService successs!");
        return new WxPaysdkServiceImpl(weixinPayConfig);
    }

    @Bean
    @ConditionalOnProperty(prefix = "wxpay", name = "downbill", havingValue = "true")
    public WxBillSdkService wxBillSdkService(WxPayConfig weixinPayConfig) throws Exception {
        logger.debug("init WxBillSdkService successs!");
        return new WxBillSdkServiceImpl(weixinPayConfig);
    }
}
