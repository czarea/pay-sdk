package com.kmob.paysdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 微信成功支付账单
 *
 * @author verne
 */
@Data
@XStreamAlias("xml")
public class WxSuccessBillResponse {
    private String tradeDate;
    private String appId;
    private String mchId;
    private String subMchId;
    private String deviceInfo;
    private String transactionId;
    private String tradeNo;
    private String userFlag;
    private String tradeType;
    private String tradeStatus;
    private String bankType;
    private String feeType;
    private String totalFee;
    private String couponDiscount;
    
    private String productId;
    private String attach;
    private String poundage;
    private String rate;
    
    @Override
    public String toString() {
        return "WeixinSuccessBillResponse [tradeDate=" + tradeDate + ", appId=" + appId + ", mchId="
                + mchId + ", subMchId=" + subMchId + ", deviceInfo=" + deviceInfo
                + ", transactionId=" + transactionId + ", tradeNo=" + tradeNo + ", userFlag="
                + userFlag + ", tradeType=" + tradeType + ", tradeStatus=" + tradeStatus
                + ", bankType=" + bankType + ", feeType=" + feeType + ", totalFee=" + totalFee
                + ", couponDiscount=" + couponDiscount + ", productId=" + productId + ", attach="
                + attach + ", poundage=" + poundage + ", rate=" + rate + "]";
    }
    
}
