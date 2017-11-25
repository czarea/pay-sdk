package com.kmob.paysdk.spring.boot.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kmob.paysdk.alipay.config.AliPayConfig;
import com.kmob.paysdk.alipay.service.AliPayService;
import com.kmob.paysdk.alipay.service.impl.AliPayServiceImpl;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for paysdk
 * 
 * 
 *
 * @author verne
 */
@Configuration
@EnableConfigurationProperties(value = {AliPayConfig.class})
public class PaysdkAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(PaysdkAutoConfiguration.class);
    
    @Bean
    @ConditionalOnProperty(prefix="alipay",name="init")
    public AliPayService aliPayService(AliPayConfig aliPayConfig) {
        logger.debug("init AliPayService ........................................");
        return new AliPayServiceImpl(aliPayConfig);
    }
}
