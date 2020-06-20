package com.czarea.pay.sdk.wxpay.response;

import com.czarea.pay.sdk.util.MapUtil;
import com.czarea.pay.sdk.wxpay.transport.WxPayUtil;

/**
 * 返回微信实体
 *
 * @author zhouzx
 */
public class WxNotifyResponse {

    /**
     * 返回状态码
     */
    private String return_code;
    /**
     * 返回信息
     */
    private String return_msg;

    private static String ERROR_RESPONSE;

    public WxNotifyResponse() {
    }

    public WxNotifyResponse(String return_code, String return_msg) {
        this.return_code = return_code;
        this.return_msg = return_msg;
    }


    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public static String errorXml() {
        if (ERROR_RESPONSE == null) {
            synchronized (WxNotifyResponse.class) {
                if (ERROR_RESPONSE == null) {
                    try {
                        ERROR_RESPONSE = WxPayUtil.mapToXml(MapUtil.beanToStringMap(new WxNotifyResponse("ERROR", "ERROR")));
                    } catch (Exception e) {
                    }
                }
            }
            return ERROR_RESPONSE;
        }
        return ERROR_RESPONSE;
    }

}
