package com.czarea.pay.sdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 微信支付结果共用属性类
 *
 * @author zhouzx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
@XStreamAlias("xml")
public class WxPappayResponse extends WxPayBaseResponse {

    @XStreamAlias("device_info")
    private String deviceInfo;
}
