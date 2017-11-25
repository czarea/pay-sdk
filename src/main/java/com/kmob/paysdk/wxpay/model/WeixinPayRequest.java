package com.kmob.paysdk.wxpay.model;

/**
 * 微信支付请求model
 *
 * @author verne
 */
public class WeixinPayRequest {
    /**
     * 终端设备号(门店号或收银设备ID)，默认请传"WEB"
     */
    private String deviceInfo;
    /**
     * 随机字符串，不长于32位
     */
    private String nonceStr;
    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    private String signType;

    /**
     * 商品描述交易字段格式根据不同的应用场景按照以下格式：
     * <p>
     * APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     */
    private String body;
    /**
     * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
     */
    private String detail;

    /**
     * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    private String attach;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
     */
    private String outTradeNo;

    /**
     * 符合ISO
     * 4217标准的三位字母代码，默认人民币：CNY,其他值列表详见货币类型:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_2
     */
    private String feeType;

    /**
     * 订单总金额，单位为分
     */
    private String totalAmount;

    /**
     * 用户端实际ip
     */
    private String spbillCreateIp;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
     */
    private String timeStart;

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010
     */
    private String timeExipre;

    /**
     * 订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠:https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1
     */
    private String goodsTag;

    /**
     * 支付类型:JSAPI，NATIVE，APP等，说明详见参数规定:https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
     */
    private String tradeType;

    /**
     * no_credit--指定不能使用信用卡支付
     */
    private String limitPay;

    /**
     * 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息
     */
    private String scenInfo;

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExipre() {
        return timeExipre;
    }

    public void setTimeExipre(String timeExipre) {
        this.timeExipre = timeExipre;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getScenInfo() {
        return scenInfo;
    }

    public void setScenInfo(String scenInfo) {
        this.scenInfo = scenInfo;
    }

}
