package com.czarea.pay.sdk.wxpay.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *   注释中各行对应含义：
 *   字段名
 *   字段
 *   必填
 *   示例值
 *   类型
 *   说明
 * Created by Binary Wang on 2016-11-28.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayRedpackQueryRequest extends WxPayBaseRequest {

    /**
     * 商户订单号
     * mch_billno
     * 是
     * 10000098201411111234567890
     * String(28)
     * 商户发放红包的商户订单号
     */
    @XStreamAlias("mch_billno")
    private String mchBillNo;

    /**
     * 订单类型
     * bill_type
     * 是
     * MCHT
     * String(32)
     * MCHT:通过商户订单号获取红包信息。
     */
    @XStreamAlias("bill_type")
    private String billType;

    @Override
    protected void checkConstraints() {

    }
}