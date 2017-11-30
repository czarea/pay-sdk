package com.kmob.paysdk.wxpay.response;

/**
 * 微信统一下单返回数据实体
 *
 * @author verne
 */
public class WeixinPayResponse {
    /**
     * appid
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 自定义参数，可以为请求支付的终端设备号等
     */
    private String device_info;

    /**
     * 微信返回的随机字符串
     */
    private String nonce_str;
    /**
     * 微信返回的签名值
     */
    private String sign;

    /**
     * SUCCESS/FAIL
     */
    private String result_code;
    private String return_code;
    private String return_msg;
    private String err_code;
    private String err_code_des;
    /**
     * 交易类型，取值为：JSAPI，NATIVE，APP等
     */
    private String trade_type;
    /**
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    private String prepay_id;
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getMch_id() {
        return mch_id;
    }
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }
    public String getDevice_info() {
        return device_info;
    }
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
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
    public String getTrade_type() {
        return trade_type;
    }
    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
    public String getPrepay_id() {
        return prepay_id;
    }
    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }
    @Override
    public String toString() {
        return "WeixinPayResponse [appid=" + appid + ", mch_id=" + mch_id + ", device_info="
                + device_info + ", nonce_str=" + nonce_str + ", sign=" + sign + ", result_code="
                + result_code + ", return_code=" + return_code + ", return_msg=" + return_msg
                + ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", trade_type="
                + trade_type + ", prepay_id=" + prepay_id + "]";
    }

}
