package com.czarea.pay.sdk.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.czarea.pay.sdk.alipay.config.AliPayConfig;
import com.czarea.pay.sdk.alipay.service.AliPaysdkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 支付宝支付接口实现类
 *
 * @author zhouzx
 */
public class AliPaysdkServiceImpl implements AliPaysdkService {

    private static final Logger logger = LoggerFactory.getLogger(AliPaysdkServiceImpl.class);

    private static final String API_GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 支付宝配置类
     */
    private AliPayConfig aliPayConfig;

    AliPaysdkServiceImpl() {
    }

    public AliPaysdkServiceImpl(AliPayConfig aliPayConfig) {
        this.aliPayConfig = aliPayConfig;
    }

    @Override
    public AlipayTradeAppPayResponse appPay(AlipayTradeAppPayModel appPayModel) throws Exception {
        // 校验请求
        validateRequest(appPayModel);

        AlipayClient alipayClient = new DefaultAlipayClient(API_GATEWAY_URL,
            aliPayConfig.getAppId(), aliPayConfig.getPrivateKey(), aliPayConfig.getFormat(),
            aliPayConfig.getInputCharset(), aliPayConfig.getAlipayPublicKey(),
            aliPayConfig.getSignType());
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        request.setBizModel(appPayModel);
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return response;
        } catch (AlipayApiException e) {
            logger.error("get order string error", e);
            throw new RuntimeException("get order string error");
        }
    }

    private boolean validateRequest(AlipayTradeAppPayModel appPayModel) throws Exception {
        if (appPayModel == null) {
            throw new RuntimeException("AliPayModel can not be null");
        }

        if (StringUtils.isEmpty(appPayModel.getBody())) {
            throw new RuntimeException("AliPayModel's body can not be null");
        }

        if (StringUtils.isEmpty(appPayModel.getSubject())) {
            throw new RuntimeException("AliPayModel's Subject can not be null");
        }
        if (StringUtils.isEmpty(appPayModel.getOutTradeNo())) {
            throw new RuntimeException("AliPayModel's OutTradeNo can not be null");
        }
        if (StringUtils.isEmpty(appPayModel.getTotalAmount())) {
            throw new RuntimeException("AliPayModel's TotalAmount can not be null");
        }
        if (StringUtils.isEmpty(appPayModel.getProductCode())) {
            throw new RuntimeException("AliPayModel's ProductCode can not be null");
        }

        return true;
    }

}
