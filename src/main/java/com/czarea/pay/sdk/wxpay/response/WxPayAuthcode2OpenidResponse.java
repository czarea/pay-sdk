package com.czarea.pay.sdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  授权码查询openid接口请求结果类
 * </pre>
 *
 * @author zhouzx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayAuthcode2OpenidResponse extends WxPayBaseResponse {

    /**
     * <pre>
     *   用户标识
     *   openid
     *   是
     *   String(128)
     *   用户在商户appid下的唯一标识
     * </pre>
     */
    @XStreamAlias("openid")
    private String openid;

}
