package com.kmob.paysdk.test.util;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.kmob.paysdk.util.MapperUtil;
import com.kmob.paysdk.wxpay.request.WeixinPayRequest;
import com.kmob.paysdk.wxpay.transport.WeixinPayUtil;

public class MapperUtilTest {
    @Test
    public void test() throws Exception {
        WeixinPayRequest request = new WeixinPayRequest();
        request.setBody("test");
        request.setDevice_info("app");
        Map<String,String> map = MapperUtil.beanToStringMap(request);
        Assert.assertEquals(map.get("body"), "test");
        String xml = WeixinPayUtil.mapToXml(map);
        System.out.println(xml);
        System.out.println(map);
    }
}
