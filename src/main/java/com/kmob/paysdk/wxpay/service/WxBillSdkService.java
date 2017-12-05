package com.kmob.paysdk.wxpay.service;

import com.kmob.paysdk.wxpay.request.WxPayDownloadBillRequest;
import com.kmob.paysdk.wxpay.response.WxPayBillBaseResponse;

/**
 * 微信下载账单
 *
 * @author verne
 */
public interface WxBillSdkService {

    /**
     * 下载成功支付订单
     * 
     * @param request
     * @param gzip 是否压缩
     * @return
     * @throws Exception
     */
    WxPayBillBaseResponse downloadSuccessBill(WxPayDownloadBillRequest request, boolean gzip) throws Exception;

    /**
     * 
     * @param request
     * @param gzip 是否压缩
     * @return
     * @throws Exception
     */
    WxPayBillBaseResponse downloadALLBill(WxPayDownloadBillRequest request, boolean gzip) throws Exception;

    /**
     * 
     * 
     * @param request
     * @param gzip 是否压缩
     * @return
     * @throws Exception
     */
    WxPayBillBaseResponse downloadRefundBill(WxPayDownloadBillRequest request, boolean gzip) throws Exception;
}
