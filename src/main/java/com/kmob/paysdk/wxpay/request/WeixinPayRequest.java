package com.kmob.paysdk.wxpay.request;

/**
 * 微信支付请求model
 *
 * @author verne
 */
public class WeixinPayRequest {
    /**
     * 终端设备号(门店号或收银设备ID)，默认请传"WEB"
     */
    private String device_info;
    /**
     * 随机字符串，不长于32位
     */
    private String nonce_str;
    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，APP默认未HMAC-SHA256，公众号为MD5
     */
    private String sign_type;

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
    private String out_trade_no;

    /**
     * 符合ISO
     * 4217标准的三位字母代码，默认人民币：CNY,其他值列表详见货币类型:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_2
     */
    private String fee_type;

    /**
     * 订单总金额，单位为分
     */
    private String total_fee;

    /**
     * 用户端实际ip
     */
    private String spbill_create_ip;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
     */
    private String time_start;

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010
     */
    private String time_exipre;

    /**
     * 订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠:https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1
     */
    private String goods_tag;

    /**
     * 支付类型:JSAPI，NATIVE，APP等，说明详见参数规定:https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
     */
    private String trade_type;

    /**
     * no_credit--指定不能使用信用卡支付
     */
    private String limit_pay;

    /**
     * 商品ID
     */
    private String product_id;

    /**
     * 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息
     */
    private String scene_info;


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

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
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

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_exipre() {
        return time_exipre;
    }

    public void setTime_exipre(String time_exipre) {
        this.time_exipre = time_exipre;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getScene_info() {
        return scene_info;
    }

    public void setScene_info(String scene_info) {
        this.scene_info = scene_info;
    }

    @Override
    public String toString() {
        return "WeixinPayRequest [device_info=" + device_info + ", nonce_str=" + nonce_str
                + ", sign_type=" + sign_type + ", body=" + body + ", detail=" + detail + ", attach="
                + attach + ", out_trade_no=" + out_trade_no + ", fee_type=" + fee_type
                + ", total_fee=" + total_fee + ", spbill_create_ip=" + spbill_create_ip
                + ", time_start=" + time_start + ", time_exipre=" + time_exipre + ", goods_tag="
                + goods_tag + ", trade_type=" + trade_type + ", limit_pay=" + limit_pay
                + ", product_id=" + product_id + ", scene_info=" + scene_info + "]";
    }

}
