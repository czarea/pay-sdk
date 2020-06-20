package com.czarea.pay.sdk.test.wxpay;

import com.czarea.pay.sdk.test.PaySdkApplication;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.czarea.pay.sdk.wxpay.request.WxPayMicropayRequest;
import com.czarea.pay.sdk.wxpay.request.WxPayRefundQueryRequest;
import com.czarea.pay.sdk.wxpay.request.WxPayRefundRequest;
import com.czarea.pay.sdk.wxpay.request.WxPayUnifiedOrderRequest;
import com.czarea.pay.sdk.wxpay.response.WxPayOrderCloseResponse;
import com.czarea.pay.sdk.wxpay.response.WxPayOrderQueryResponse;
import com.czarea.pay.sdk.wxpay.response.WxPayRefundQueryResponse;
import com.czarea.pay.sdk.wxpay.response.WxPayRefundResponse;
import com.czarea.pay.sdk.wxpay.response.WxPayUnifiedOrderResponse;
import com.czarea.pay.sdk.wxpay.service.WxPaysdkService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaySdkApplication.class,webEnvironment=WebEnvironment.RANDOM_PORT)
public class ServiceTest {
    @Autowired
    private WxPaysdkService weixinPaysdkService;

    @Test
    public void testUnifiedOrder() throws Exception {
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody("test");
        request.setTotalFee(1);
        request.setTradeType("APP");
        request.setOutTradeNo("1111111111111");
        WxPayUnifiedOrderResponse res = weixinPaysdkService.unifiedOrder(request);
        System.out.println(res);
    }

    @Test
    public void testAPPPay() throws Exception {
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody("test");
        request.setTotalFee(1);
        request.setTradeType("APP");
        request.setOutTradeNo("1111111111111");
        @SuppressWarnings("unchecked")
        Map<String, String> res =
                (Map<String, String>) weixinPaysdkService.appPay(request).getData();
        System.out.println(res);
    }

    @Test
    public void testMicropay() throws Exception {
        WxPayMicropayRequest request =  new WxPayMicropayRequest();
        request.setBody("测试");
        request.setAuthCode("134819255087088144");
        request.setOutTradeNo("test2018020711116");
        request.setTotalFee(1);
        weixinPaysdkService.micropay(request );
    }


    /**
     * 测试查询订单
     *
     * @throws Exception
     */
    @Test
    public void testQueyrOrder() throws Exception {
        long start = System.currentTimeMillis();
        WxPayOrderQueryResponse response =
                weixinPaysdkService.queryOrder("4200000007201712018280406250", null);
        long end = System.currentTimeMillis();

        System.out.println("cost time " + (end - start) + " res " + response);
    }

    /**
     * 测试关闭订单
     *
     * @throws Exception
     */
    @Test
    public void testClosedOrder() throws Exception {
        WxPayOrderCloseResponse response = weixinPaysdkService.closedOrder("1111111111111");
        System.out.println(response);
    }

    /**
     * 测试退款，需要证书
     *
     * @throws Exception
     */
    @Test
    public void testRefund() throws Exception {
        WxPayRefundRequest request = new WxPayRefundRequest();
        request.setTransactionId("4200000007201712018280406250");
        request.setOutTradeNo("wxno936495044442370048");
        request.setOutRefundNo("re4200000007201712018280406250");
        request.setRefundFee(1);
        request.setRefundDesc("test");
        request.setTotalFee(1);
        WxPayRefundResponse response = weixinPaysdkService.refund(request);
        System.out.println(response);
    }

    @Test
    public void testRefundQuery() throws Exception {
        long start = System.currentTimeMillis();
        WxPayRefundQueryRequest request = new WxPayRefundQueryRequest();
        request.setTransactionId("4200000007201712018280406250");
        WxPayRefundQueryResponse response = weixinPaysdkService.refundQuery(request);
        long end = System.currentTimeMillis();
        System.out.println("cost time " + (end - start) + " res " + response);
    }
}
