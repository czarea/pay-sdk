package com.kmob.paysdk.wxpay.model;

/**
 * 退款请求实体
 *
 * @author verne
 */
public class WeixinRefundRequest {
    /**
     * 微信订单号
     */
    private String transactionId;
    /**
     * 系统内部订单号
     */
    private String outTradeNo;

    /**
     * 退款单号
     */
    private String outRefundNo;

    /**
     * 订单金额
     */
    private String totalAmount;

    /**
     * 退款金额
     */
    private String refundAmount;

    /**
     * 货币种类
     */
    private String refundFeeType;
    
    /**
     * 退款原因
     */
    private String refundDesc;
    
    /**
     * 退款资金来源
     * <p>
     * 仅针对老资金流商户使用 REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
     * REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
     * 
     */
    private String refundAccount;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundFeeType() {
        return refundFeeType;
    }

    public void setRefundFeeType(String refundFeeType) {
        this.refundFeeType = refundFeeType;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }


}
