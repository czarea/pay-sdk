package com.kmob.paysdk.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kmob.paysdk.wxpay.response.WeixinNotifyResponse;
import com.kmob.paysdk.wxpay.service.WeixinNotifyHandlerService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeixinNotifyHandlerService remoteService;


    @Test
    public void testWeixinPayNotifyTest() throws Exception {
        // RemoteService has been injected into the reverser bean
        WeixinNotifyResponse res = new WeixinNotifyResponse();
        given(this.remoteService.notifyHandler(null)).willReturn(res);
        this.mvc.perform(post("/weixinPay/notify").content("ssssss").accept(MediaType.APPLICATION_ATOM_XML))
                .andExpect(status().isOk()).andExpect(content().string(""));
    }

    @Test
    public void testExample() throws Exception {
        this.mvc.perform(get("/weixinPay/test").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string("success"));
    }

}
