package com.kmob.paysdk.wxpay;

/**
 * 参数字符串常量
 *
 * @author verne
 */
public class ParameterKeyConstants {

    public static class unifiedOrderRequest {
        /**
         * 终端设备号(门店号或收银设备ID)，默认请传"WEB"
         */
        public static final String DEVICE_INFO = "device_info";
        /**
         * 随机字符串，不长于32位
         */
        public static final String NONCE_STR = "nonce_str";
        /**
         * 签名类型，目前支持HMAC-SHA256和MD5，APP默认未HMAC-SHA256，公众号为MD5
         */
        public static final String SIGN_TYPE = "sign_type";

        /**
         * 商品描述交易字段格式根据不同的应用场景按照以下格式：
         * <p>
         * APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
         */
        public static final String BODY = "body";
        /**
         * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
         */
        public static final String DETAIL = "detail";

        /**
         * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
         */
        public static final String ATTACH = "attach";

        /**
         * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
         */
        public static final String OUT_TRADE_NO = "out_trade_no";

        /**
         * 符合ISO
         * 4217标准的三位字母代码，默认人民币：CNY,其他值列表详见货币类型:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_2
         */
        public static final String FEE_TYPE = "fee_type";

        /**
         * 订单总金额，单位为分
         */
        public static final String TOTAL_FEE = "total_fee";

        /**
         * 用户端实际ip
         */
        public static final String SPBILL_CREATE_IP = "spbill_create_ip";

        /**
         * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
         */
        public static final String TIME_START = "time_start";

        /**
         * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010
         */
        public static final String TIME_EXIPRE = "time_exipre";

        /**
         * 订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠:https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1
         */
        public static final String GOODS_TAG = "goods_tag";

        /**
         * 支付类型:JSAPI，NATIVE，APP等，说明详见参数规定:https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
         */
        public static final String TRADE_TYPE = "trade_type";

        /**
         * no_credit--指定不能使用信用卡支付
         */
        public static final String LIMIT_PAY = "limit_pay";

        /**
         * 商品ID
         */
        public static final String PRODUCT_ID = "product_id";

        /**
         * 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息
         */
        public static final String SCENE_INFO = "scene_info";
    }

    public static class unifiedResponse {
        public static final String APPID = "appid";
        /**
         * 商户号
         */
        public static final String MCH_ID = "mch_id";
        /**
         * 自定义参数，可以为请求支付的终端设备号等
         */
        public static final String DEVICE_INFO = "device_info";

        /**
         * 微信返回的随机字符串
         */
        public static final String NONCE_STR = "nonce_str";
        public static final String SIGN = "sign";
        public static final String RESULT_CODE = "result_code";
        public static final String RETURN_CODE = "return_code";
        public static final String RETURN_MSG = "return_msg";
        public static final String ERR_CODE = "err_code";
        public static final String ERR_CODE_DES = "err_code_des";
        public static final String TRADE_TYPE = "trade_type";
        public static final String PREPAY_ID = "prepay_id";
    }


    /**
     * 微信APP调起支付接口
     */
    public static class appPay {
        public static final String APPID = "appid";
        public static final String PARTNERID = "partnerid";
        public static final String PREPAYID = "prepayid";
        public static final String PACKAGE_VALUE = "package";
        public static final String NONCESTR = "noncestr";
        public static final String TIMESTAMP = "timestamp";
        public static final String SIGN = "sign";
    }

    /**
     * 支付结果请求通知
     *
     * @author verne
     */
    public static class notifyRequest {
        /**
         * 设备号
         */
        public static final String DEVICE_INFO = "device_info";
        /**
         * 随机字符串
         */
        public static final String NONCE_STR = "nonce_str";
        /**
         * 签名
         */
        public static final String SIGN = "sign";
        /**
         * 业务结果
         */
        public static final String RESULT_CODE = "result_code";
        /**
         * 错误代码
         */
        public static final String ERR_CODE = "err_code";
        /**
         * 错误代码描述
         */
        public static final String ERR_CODE_DES = "err_code_des";
        /**
         * 用户标识
         */
        public static final String OPENID = "openid";
        /**
         * 是否关注公众账号
         */
        public static final String IS_SUBSCRIBE = "is_subscribe";
        /**
         * 交易类型
         */
        public static final String TRADE_TYPE = "trade_type";
        /**
         * 付款银行
         */
        public static final String BANK_TYPE = "bank_type";
        /**
         * 总金额
         */
        public static final String TOTAL_FEE = "total_fee";
        /**
         * 货币种类
         */
        public static final String FEE_TYPE = "fee_type";
        /**
         * 现金支付金额
         */
        public static final String CASH_FEE = "cash_fee";
        /**
         * 现金支付货币类型
         */
        public static final String CASH_FEE_TYPE = "cash_fee_type";
        /**
         * 代金券金额
         */
        public static final String COUPON_FEE = "coupon_fee";
        /**
         * 代金券使用数量
         */
        public static final String COUPON_COUNT = "coupon_count";

        /**
         * 微信支付订单号
         */
        public static final String TRANSACTION_ID = "transaction_id";
        /**
         * 商户订单号
         */
        public static final String OUT_TRADE_NO = "out_trade_no";
        /**
         * 商家数据包
         */
        public static final String ATTACH = "attach";
        /**
         * 支付完成时间
         */
        public static final String TIME_END = "time_end";
    }
}
