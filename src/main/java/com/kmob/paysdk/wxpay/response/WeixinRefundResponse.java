package com.kmob.paysdk.wxpay.response;

/**
 * 退款微信响应数据
 *
 * @author verne
 */
public class WeixinRefundResponse {
    
    private String result_code;
    private String return_code;
    private String return_msg;
    private String err_code;
    private String err_code_des;
    
    /**
     * 微信订单号
     */
    private String nonce_str;
    /**
     * 系统内部订单号
     */
    private String sign;

    /**
     * 退款单号
     */
    private String transaction_id;

    /**
     * 订单金额
     */
    private String out_trade_no;

    /**
     * 退款金额
     */
    private String out_refund_no;

    /**
     * 货币种类
     */
    private String refund_id;
    
    /**
     * 退款原因
     */
    private String refund_fee;
    
    /**
     * 
     */
    private String settlement_refund_fee;
    private String fee_type;
    private String cash_fee;
    private String cash_fee_type ;
    private String cash_refund_fee;
    private String coupon_refund_fee;
    private String coupon_refund_count;
    

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }


    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
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

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getSettlement_refund_fee() {
        return settlement_refund_fee;
    }

    public void setSettlement_refund_fee(String settlement_refund_fee) {
        this.settlement_refund_fee = settlement_refund_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public String getCash_refund_fee() {
        return cash_refund_fee;
    }

    public void setCash_refund_fee(String cash_refund_fee) {
        this.cash_refund_fee = cash_refund_fee;
    }

    public String getCoupon_refund_fee() {
        return coupon_refund_fee;
    }

    public void setCoupon_refund_fee(String coupon_refund_fee) {
        this.coupon_refund_fee = coupon_refund_fee;
    }

    public String getCoupon_refund_count() {
        return coupon_refund_count;
    }

    public void setCoupon_refund_count(String coupon_refund_count) {
        this.coupon_refund_count = coupon_refund_count;
    }

    @Override
    public String toString() {
        return "WeixinRefundResponse [result_code=" + result_code + ", return_code=" + return_code
                + ", return_msg=" + return_msg + ", err_code=" + err_code + ", err_code_des="
                + err_code_des + ", nonce_str=" + nonce_str + ", sign=" + sign + ", transaction_id="
                + transaction_id + ", out_trade_no=" + out_trade_no + ", out_refund_no="
                + out_refund_no + ", refund_id=" + refund_id + ", refund_fee=" + refund_fee
                + ", settlement_refund_fee=" + settlement_refund_fee + ", fee_type=" + fee_type
                + ", cash_fee=" + cash_fee + ", cash_fee_type=" + cash_fee_type
                + ", cash_refund_fee=" + cash_refund_fee + ", coupon_refund_fee="
                + coupon_refund_fee + ", coupon_refund_count=" + coupon_refund_count + "]";
    }
    
}
