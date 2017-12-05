package com.kmob.paysdk.wxpay.response;

/**
 * 微信退款账单
 *
 * @author verne
 */
public class WxRefundBillResponse extends WxAllBillResponse{
    private String refundBeginTime;
    private String refundSuccessedTime;
    public String getRefundBeginTime() {
        return refundBeginTime;
    }
    public void setRefundBeginTime(String refundBeginTime) {
        this.refundBeginTime = refundBeginTime;
    }
    public String getRefundSuccessedTime() {
        return refundSuccessedTime;
    }
    public void setRefundSuccessedTime(String refundSuccessedTime) {
        this.refundSuccessedTime = refundSuccessedTime;
    }
    
}
