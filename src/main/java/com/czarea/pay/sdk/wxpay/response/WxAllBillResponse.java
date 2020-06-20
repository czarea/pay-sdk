package com.czarea.pay.sdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信所有账单，包括成功和退款的
 *
 * @author zhouzx
 */
@XStreamAlias("xml")
public class WxAllBillResponse extends WxSuccessBillResponse {

    private String transactionRefundId;
    private String refundId;
    private String refundFee;
    private String couponRefundDiscount;
    private String refundType;
    private String refundStatus;


    public String getTransactionRefundId() {
        return transactionRefundId;
    }

    public void setTransactionRefundId(String transactionRefundId) {
        this.transactionRefundId = transactionRefundId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getCouponRefundDiscount() {
        return couponRefundDiscount;
    }

    public void setCouponRefundDiscount(String couponRefundDiscount) {
        this.couponRefundDiscount = couponRefundDiscount;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

}
