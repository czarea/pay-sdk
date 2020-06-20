package com.czarea.pay.sdk.wxpay.response;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * 微信错误码说明，请阅读：
 * <a href="http://mp.weixin.qq.com/wiki/10/6380dc743053a91c544ffd2b7c959166.html">全局返回码说明</a>.
 *
 * @author zhouzx
 */
@Data
@Builder
public class WxError implements Serializable {

    private static final long serialVersionUID = 7869786563361406291L;

    private int errorCode;

    private String errorMsg;

    private String json;

    public static WxError fromJson(String json) {
        return JSON.parseObject(json, WxError.class);
    }

    @Override
    public String toString() {
        if (this.json != null) {
            return this.json;
        }
        return "错误: Code=" + this.errorCode + ", Msg=" + this.errorMsg;
    }

}
