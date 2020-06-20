package com.czarea.pay.sdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 企业付款返回结果
 *
 * @author zhouzx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxEntPayResponse extends WxPayBaseResponse {

    /**
     * 商户appid
     */
    @XStreamAlias("mch_appid")
    private String mchAppid;

    /**
     * 设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    // ############以下字段在return_code 和result_code都为SUCCESS的时候有返回##############
    /**
     * 商户订单号
     */
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    /**
     * 微信订单号
     */
    @XStreamAlias("payment_no")
    private String paymentNo;

    /**
     * 微信支付成功时间
     */
    @XStreamAlias("payment_time")
    private String paymentTime;

}
