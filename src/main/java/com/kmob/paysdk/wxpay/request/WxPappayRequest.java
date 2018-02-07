package com.kmob.paysdk.wxpay.request;

import com.kmob.paysdk.annotation.Required;
import com.kmob.paysdk.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 委托代扣
 *
 * @author verne
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPappayRequest extends WxPayBaseRequest {
    @Required
    private String body;
    private String detail;
    private int attach;
    @Required
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
    @Required
    @XStreamAlias("out_trade_no")
    private String totalFee;
    @XStreamAlias("fee_type")
    private String feeType;


    @Required
    @XStreamAlias("spbill_create_ip")
    private String spbill_create_ip;

    @XStreamAlias("goods_tag")
    private String goods_tag;
    @XStreamAlias("trade_type")
    private String trade_type;
    @XStreamAlias("contract_id")
    private String contract_id;

    private String clientip;
    private String deviceid;
    private String mobile;
    private String email;
    private String qq;
    private String openid;
    private String creid;
    private String outerid;
    private String timestamp;

    @Override
    protected void checkConstraints() throws WxPayException {
        // TODO Auto-generated method stub

    }

}
