package com.kmob.paysdk.test.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmob.paysdk.parallel.DefaultThreadFactory;
import com.kmob.paysdk.util.MapUtil;
import com.kmob.paysdk.util.xml.XStreamInitializer;
import com.kmob.paysdk.wxpay.response.WxSuccessBillResponse;
import com.kmob.paysdk.wxpay.transport.WeixinPayUtil;
import com.thoughtworks.xstream.XStream;

public class XmlTest {
    private static final Logger logger = LoggerFactory.getLogger(XmlTest.class);

    @Test
    public void testUseDom() throws Exception {
        long start = System.currentTimeMillis();
        ThreadFactory namedThreadFactory = new DefaultThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(4, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 2; i++) {
            final int j = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    WxSuccessBillResponse res = new WxSuccessBillResponse();
                    res.setMchId("adba"+j);
                    res.setAppId("aaa"+j);
                    res.setBankType("ALL"+j);
                    res.setFeeType("CNY");
                    res.setTotalFee("100");
                    String xml;
                    try {
                        xml = WeixinPayUtil.mapToXml(MapUtil.beanToStringMap(res));
                        System.out.println(xml);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            });
        }
        long end = System.currentTimeMillis();
        logger.debug("cost time {}", (end - start));
        Thread.sleep(5000);
    }

    @Test
    public void testUseXS() throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        
        long start = System.currentTimeMillis();
        XStream xstream = XStreamInitializer.getInstance();
        ThreadFactory namedThreadFactory = new DefaultThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(4, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(100000), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        
        int times = 10000;
        
        CountDownLatch timesLatch = new CountDownLatch(times);
        
        for (int i = 0; i < times; i++) {
            final int j = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    WxSuccessBillResponse res = new WxSuccessBillResponse();
                    res.setMchId("adba"+j);
                    res.setAppId("aaa"+j);
                    res.setBankType("ALL"+j);
                    res.setFeeType("CNY"+j);
                    res.setTotalFee("100");
                    xstream.processAnnotations(WxSuccessBillResponse.class);
                    String xml = xstream.toXML(res);
                    WxSuccessBillResponse xss = (WxSuccessBillResponse) xstream.fromXML(xml);
                    System.out.println(xss);
                    timesLatch.countDown();
                }

            });

        }
        
        startLatch.countDown();
        
        
        timesLatch.await();
        
        long end = System.currentTimeMillis();
        logger.debug("cost time {}", (end - start));
    }
    
    @Test
    public void domXmlToBean() {
        
    }
    
    @Test
    public void xsXmlToBean() {
        
    }
    
    @Test
    public void testXStream() {
        long start = System.currentTimeMillis();
        int times = 1000000;
        for(int i=0;i<times;i++) {
            XStreamInitializer.getInstance();
        }
        long end = System.currentTimeMillis();
        logger.debug("create xstream {} times cost time {}", times,(end - start));
    }
}
