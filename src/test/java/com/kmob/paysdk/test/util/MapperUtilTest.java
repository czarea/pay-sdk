package com.kmob.paysdk.test.util;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.kmob.paysdk.util.MapUtil;
import com.kmob.paysdk.wxpay.request.WxPayBaseRequest;
import com.kmob.paysdk.wxpay.request.WxPayDownloadBillRequest;
import com.kmob.paysdk.wxpay.transport.WxPayUtil;

public class MapperUtilTest {
    @Test
    public void test() throws Exception {
        WxPayBaseRequest request = new WxPayDownloadBillRequest();
        request.setAppid("1234");
        request.setMchId("5678");
        Map<String,String> map = MapUtil.beanToStringMap(request);
        Assert.assertEquals(map.get("appid"), "1234");
        String xml = WxPayUtil.mapToXml(map);
        System.out.println(xml);
        System.out.println(map);
    }
}
