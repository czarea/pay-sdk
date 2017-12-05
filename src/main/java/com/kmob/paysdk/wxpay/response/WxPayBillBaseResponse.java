package com.kmob.paysdk.wxpay.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易时间:2017-04-06 01:00:02 公众账号ID: 商户号: 子商户号:0 设备号:WEB 微信订单号: 商户订单号:2017040519091071873216 用户标识:
 * 交易类型:NATIVE 交易状态:REFUND 付款银行:CFT 货币种类:CNY 总金额:0.00 企业红包金额:0.00 微信退款单号: 商户退款单号:20170406010000933
 * 退款金额:0.01 企业红包退款金额:0.00 退款类型:ORIGINAL 退款状态:SUCCESS 商品名称: 商户数据包: 手续费:0.00000 费率 :0.60%
 */
@Data
@NoArgsConstructor
public class WxPayBillBaseResponse implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 4801752393979723633L;

    private String fileName;
    private List<WxAllBillResponse> allList;
    private List<WxSuccessBillResponse> sussceeList;
    private List<WxRefundBillResponse> refundList;

}
