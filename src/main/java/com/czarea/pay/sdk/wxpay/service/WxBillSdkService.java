package com.czarea.pay.sdk.wxpay.service;

import com.czarea.pay.sdk.wxpay.request.WxPayDownloadBillRequest;
import com.czarea.pay.sdk.wxpay.response.WxPayBillBaseResponse;

/**
 * 微信下载账单
 *
 * @author zhouzx
 */
public interface WxBillSdkService {

    /**
     * 下载成功支付订单
     *
     * @param gzip 是否压缩
     */
    WxPayBillBaseResponse downloadSuccessBill(WxPayDownloadBillRequest request, boolean gzip) throws Exception;

    /**
     * @param gzip 是否压缩
     */
    WxPayBillBaseResponse downloadALLBill(WxPayDownloadBillRequest request, boolean gzip) throws Exception;

    /**
     * @param gzip 是否压缩
     */
    WxPayBillBaseResponse downloadRefundBill(WxPayDownloadBillRequest request, boolean gzip) throws Exception;
}
