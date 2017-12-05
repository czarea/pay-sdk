package com.kmob.paysdk.spring.boot.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kmob.paysdk.alipay.config.AliPayConfig;
import com.kmob.paysdk.alipay.service.AliPaysdkService;
import com.kmob.paysdk.alipay.service.impl.AliPaysdkServiceImpl;
import com.kmob.paysdk.wxpay.config.WeixinPayConfig;
import com.kmob.paysdk.wxpay.service.WxBillSdkService;
import com.kmob.paysdk.wxpay.service.WxPaysdkService;
import com.kmob.paysdk.wxpay.service.impl.WxBillSdkServiceImpl;
import com.kmob.paysdk.wxpay.service.impl.WxPaysdkServiceImpl;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for paysdk
 * 
 * 
 *
 * @author verne
 */
@Configuration
@EnableConfigurationProperties(value = {AliPayConfig.class, WeixinPayConfig.class})
public class PaysdkAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(PaysdkAutoConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = "alipay", name = "use")
    public AliPaysdkService aliPayService(AliPayConfig aliPayConfig) {
        logger.debug("init AliPayService success!");
        return new AliPaysdkServiceImpl(aliPayConfig);
    }

    @Bean
    @ConditionalOnProperty(prefix = "wxpay", name = "use",havingValue="true")
    public WxPaysdkService weixinPayService(WeixinPayConfig weixinPayConfig) throws Exception {
        logger.debug("init WeixinPayService successs!");
        return new WxPaysdkServiceImpl(weixinPayConfig);
    }
    
    @Bean
    @ConditionalOnProperty(prefix = "wxpay", name = "downbill",havingValue="true")
    public WxBillSdkService wxBillSdkService(WeixinPayConfig weixinPayConfig) throws Exception {
        logger.debug("init WxBillSdkService successs!");
        return new WxBillSdkServiceImpl(weixinPayConfig);
    }
}
