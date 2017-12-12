package com.kmob.paysdk.wxpay.transport;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.kmob.paysdk.wxpay.config.WxPayConfig;
import com.kmob.paysdk.wxpay.constant.WxPayConstants;
import com.kmob.paysdk.wxpay.constant.WxPayConstants.SignType;

/**
 * 微信支付类
 *
 * @author verne
 */
public class WxPay {

    private WxPayConfig config;
    private SignType signType;
    private boolean autoReport;
    private boolean useSandbox = false;
    private String notifyUrl;
    private WxPayTransport wxPayRequest;

    public WxPay(final WxPayConfig config) throws Exception {
        this(config, null, false);
        useSandbox = config.isUseSandbox();
    }

    public WxPay(final WxPayConfig config, final String notifyUrl, final boolean autoReport)
            throws Exception {
        this.config = config;
        // checkWXPayConfig();
        this.notifyUrl = notifyUrl;
        this.autoReport = false;
        if (config.isUseSandbox()) {
            this.signType = SignType.MD5; // 沙箱环境
        } else {
            this.signType = SignType.HMACSHA256;
        }
        this.wxPayRequest = new WxPayTransport(config);
    }

    /**
     * 向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br>
     * 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
     *
     * @param reqData
     * @return
     * @throws Exception
     */
    public Map<String, String> fillRequestData(Map<String, String> reqData) throws Exception {
        reqData.put("appid", config.getAppID());
        reqData.put("mch_id", config.getMchID());
//        reqData.put("nonce_str", WeixinPayUtil.generateUUID());
        if (SignType.MD5.equals(this.signType)) {
            reqData.put("sign_type", WxPayConstants.MD5);
        } else if (SignType.HMACSHA256.equals(this.signType)) {
            reqData.put("sign_type", WxPayConstants.HMACSHA256);
        }
        System.out.println(reqData);
        reqData.put("sign",
                WxPayUtil.generateSignature(reqData, config.getKey(), this.signType));
        return reqData;
    }

    /**
     * 判断xml数据的sign是否有效，必须包含sign字段，否则返回false。
     *
     * @param reqData 向wxpay post的请求数据
     * @return 签名是否有效
     * @throws Exception
     */
    public boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
        // 返回数据的签名方式和请求中给定的签名方式是一致的
        return WxPayUtil.isSignatureValid(reqData, this.config.getKey(), this.signType);
    }

    /**
     * 判断支付结果通知中的sign是否有效
     *
     * @param reqData 向wxpay post的请求数据
     * @return 签名是否有效
     * @throws Exception
     */
    public boolean isPayResultNotifySignatureValid(Map<String, String> reqData) throws Exception {
        String signTypeInData = reqData.get(WxPayConstants.FIELD_SIGN_TYPE);
        SignType signType;
        if (signTypeInData == null) {
            signType = SignType.MD5;
        } else {
            signTypeInData = signTypeInData.trim();
            if (signTypeInData.length() == 0) {
                signType = SignType.MD5;
            } else if (WxPayConstants.MD5.equals(signTypeInData)) {
                signType = SignType.MD5;
            } else if (WxPayConstants.HMACSHA256.equals(signTypeInData)) {
                signType = SignType.HMACSHA256;
            } else {
                throw new Exception(String.format("Unsupported sign_type: %s", signTypeInData));
            }
        }
        return WxPayUtil.isSignatureValid(reqData, this.config.getKey(), signType);
    }


    /**
     * 不需要证书的请求
     * 
     * @param urlSuffix String
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 超时时间，单位是毫秒
     * @param readTimeoutMs 超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public String requestWithoutCert(String urlSuffix, Map<String, String> reqData,
            int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String msgUUID = reqData.get("nonce_str");
        String reqBody = WxPayUtil.mapToXml(reqData);

        String resp = this.wxPayRequest.requestWithoutCert(urlSuffix, msgUUID, reqBody,
                connectTimeoutMs, readTimeoutMs, autoReport);
        return resp;
    }


    /**
     * 需要证书的请求
     * 
     * @param urlSuffix String
     * @param reqData 向wxpay post的请求数据 Map
     * @param connectTimeoutMs 超时时间，单位是毫秒
     * @param readTimeoutMs 超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public String requestWithCert(String urlSuffix, Map<String, String> reqData,
            int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String msgUUID = reqData.get("nonce_str");
        String reqBody = WxPayUtil.mapToXml(reqData);

        String resp = this.wxPayRequest.requestWithCert(urlSuffix, msgUUID, reqBody,
                connectTimeoutMs, readTimeoutMs, this.autoReport);
        return resp;
    }

    /**
     * 处理 HTTPS API返回数据，转换成Map对象。return_code为SUCCESS时，验证签名。
     * 
     * @param xmlStr API返回的XML格式数据
     * @return Map类型数据
     * @throws Exception
     */
    public Map<String, String> processResponseXml(String xmlStr) throws Exception {
        String RETURN_CODE = "return_code";
        String return_code;
        Map<String, String> respData = WxPayUtil.xmlToMap(xmlStr);
        if (respData.containsKey(RETURN_CODE)) {
            return_code = respData.get(RETURN_CODE);
        } else {
            throw new Exception(String.format("No `return_code` in XML: %s", xmlStr));
        }

        if (return_code.equals(WxPayConstants.FAIL)) {
            return respData;
        } else if (return_code.equals(WxPayConstants.SUCCESS)) {
            if (this.isResponseSignatureValid(respData)) {
                return respData;
            } else {
                throw new Exception(String.format("Invalid sign value in XML: %s", xmlStr));
            }
        } else {
            throw new Exception(String.format("return_code value %s is invalid in XML: %s",
                    return_code, xmlStr));
        }
    }

    /**
     * 作用：提交刷卡支付<br>
     * 场景：刷卡支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> microPay(Map<String, String> reqData) throws Exception {
        return this.microPay(reqData, this.config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：提交刷卡支付<br>
     * 场景：刷卡支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> microPay(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_MICROPAY_URL_SUFFIX;
        } else {
            url = WxPayConstants.MICROPAY_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 提交刷卡支付，针对软POS，尽可能做成功 内置重试机制，最多60s
     * 
     * @param reqData
     * @return
     * @throws Exception
     */
    public Map<String, String> microPayWithPos(Map<String, String> reqData) throws Exception {
        return this.microPayWithPos(reqData, this.config.getHttpConnectTimeoutMs());
    }

    /**
     * 提交刷卡支付，针对软POS，尽可能做成功 内置重试机制，最多60s
     * 
     * @param reqData
     * @param connectTimeoutMs
     * @return
     * @throws Exception
     */
    public Map<String, String> microPayWithPos(Map<String, String> reqData, int connectTimeoutMs)
            throws Exception {
        int remainingTimeMs = 60 * 1000;
        long startTimestampMs = 0;
        Map<String, String> lastResult = null;
        Exception lastException = null;

        while (true) {
            startTimestampMs = WxPayUtil.getCurrentTimestampMs();
            int readTimeoutMs = remainingTimeMs - connectTimeoutMs;
            if (readTimeoutMs > 1000) {
                try {
                    lastResult = this.microPay(reqData, connectTimeoutMs, readTimeoutMs);
                    String returnCode = lastResult.get("return_code");
                    if (returnCode.equals("SUCCESS")) {
                        String resultCode = lastResult.get("result_code");
                        String errCode = lastResult.get("err_code");
                        if (resultCode.equals("SUCCESS")) {
                            break;
                        } else {
                            // 看错误码，若支付结果未知，则重试提交刷卡支付
                            if (errCode.equals("SYSTEMERROR") || errCode.equals("BANKERROR")
                                    || errCode.equals("USERPAYING")) {
                                remainingTimeMs = remainingTimeMs
                                        - (int) (WxPayUtil.getCurrentTimestampMs()
                                                - startTimestampMs);
                                if (remainingTimeMs <= 100) {
                                    break;
                                } else {
                                    WxPayUtil.getLogger()
                                            .info("microPayWithPos: try micropay again");
                                    if (remainingTimeMs > 5 * 1000) {
                                        Thread.sleep(5 * 1000);
                                    } else {
                                        Thread.sleep(1 * 1000);
                                    }
                                    continue;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                } catch (Exception ex) {
                    lastResult = null;
                    lastException = ex;
                }
            } else {
                break;
            }
        }

        if (lastResult == null) {
            throw lastException;
        } else {
            return lastResult;
        }
    }



    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData) throws Exception {
        checkData(reqData);
        return this.unifiedOrder(reqData, config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }

    private Map<String, String> checkData(Map<String, String> reqData) {
        if (StringUtils.isEmpty(reqData.get("body"))) {
            Map<String, String> error = new HashMap<String, String>();
            error.put("msg", "body is null!");
            return error;
        }
        return null;
    }


    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_UNIFIEDORDER_URL_SUFFIX;
        } else {
            url = WxPayConstants.UNIFIEDORDER_URL_SUFFIX;
        }
        if (this.notifyUrl != null) {
            reqData.put("notify_url", this.notifyUrl);
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：查询订单<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public String orderQuery(Map<String, String> reqData) throws Exception {
        return this.orderQuery(reqData, config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：查询订单<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据 int
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public String orderQuery(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs)
            throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_ORDERQUERY_URL_SUFFIX;
        } else {
            url = WxPayConstants.ORDERQUERY_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return respXml;
    }


    /**
     * 作用：撤销订单<br>
     * 场景：刷卡支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> reverse(Map<String, String> reqData) throws Exception {
        return this.reverse(reqData, config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：撤销订单<br>
     * 场景：刷卡支付<br>
     * 其他：需要证书
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> reverse(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_REVERSE_URL_SUFFIX;
        } else {
            url = WxPayConstants.REVERSE_URL_SUFFIX;
        }
        String respXml = this.requestWithCert(url, this.fillRequestData(reqData), connectTimeoutMs,
                readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：关闭订单<br>
     * 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> closeOrder(Map<String, String> reqData) throws Exception {
        return this.closeOrder(reqData, config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：关闭订单<br>
     * 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> closeOrder(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_CLOSEORDER_URL_SUFFIX;
        } else {
            url = WxPayConstants.CLOSEORDER_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> refund(Map<String, String> reqData) throws Exception {
        return this.refund(reqData, this.config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付<br>
     * 其他：需要证书
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> refund(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_REFUND_URL_SUFFIX;
        } else {
            url = WxPayConstants.REFUND_URL_SUFFIX;
        }
        String respXml = this.requestWithCert(url, this.fillRequestData(reqData), connectTimeoutMs,
                readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：退款查询<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> refundQuery(Map<String, String> reqData) throws Exception {
        return this.refundQuery(reqData, this.config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：退款查询<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> refundQuery(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_REFUNDQUERY_URL_SUFFIX;
        } else {
            url = WxPayConstants.REFUNDQUERY_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：对账单下载（成功时返回对账单数据，失败时返回XML格式数据）<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> downloadBill(Map<String, String> reqData) throws Exception {
        return this.downloadBill(reqData, this.config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：对账单下载<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付<br>
     * 其他：无论是否成功都返回Map。若成功，返回的Map中含有return_code、return_msg、data， 其中return_code为`SUCCESS`，data为对账单数据。
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return 经过封装的API返回数据
     * @throws Exception
     */
    public Map<String, String> downloadBill(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_DOWNLOADBILL_URL_SUFFIX;
        } else {
            url = WxPayConstants.DOWNLOADBILL_URL_SUFFIX;
        }
        String respStr = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs).trim();
        Map<String, String> ret;
        // 出现错误，返回XML数据
        if (respStr.indexOf("<") == 0) {
            ret = WxPayUtil.xmlToMap(respStr);
        } else {
            // 正常返回csv数据
            ret = new HashMap<String, String>();
            ret.put("return_code", WxPayConstants.SUCCESS);
            ret.put("return_msg", "ok");
            ret.put("data", respStr);
        }
        return ret;
    }


    /**
     * 作用：交易保障<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> report(Map<String, String> reqData) throws Exception {
        return this.report(reqData, this.config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：交易保障<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> report(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_REPORT_URL_SUFFIX;
        } else {
            url = WxPayConstants.REPORT_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return WxPayUtil.xmlToMap(respXml);
    }


    /**
     * 作用：转换短链接<br>
     * 场景：刷卡支付、扫码支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> shortUrl(Map<String, String> reqData) throws Exception {
        return this.shortUrl(reqData, this.config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：转换短链接<br>
     * 场景：刷卡支付、扫码支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> shortUrl(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_SHORTURL_URL_SUFFIX;
        } else {
            url = WxPayConstants.SHORTURL_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：授权码查询OPENID接口<br>
     * 场景：刷卡支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> authCodeToOpenid(Map<String, String> reqData) throws Exception {
        return this.authCodeToOpenid(reqData, this.config.getHttpConnectTimeoutMs(),
                this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：授权码查询OPENID接口<br>
     * 场景：刷卡支付
     * 
     * @param reqData 向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs 读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> authCodeToOpenid(Map<String, String> reqData, int connectTimeoutMs,
            int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayConstants.SANDBOX_AUTHCODETOOPENID_URL_SUFFIX;
        } else {
            url = WxPayConstants.AUTHCODETOOPENID_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
                connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public WxPayConfig getConfig() {
        return config;
    }

    public void setConfig(WxPayConfig config) {
        this.config = config;
    }

    public SignType getSignType() {
        return signType;
    }

    public void setSignType(SignType signType) {
        this.signType = signType;
    }

    public boolean isAutoReport() {
        return autoReport;
    }

    public void setAutoReport(boolean autoReport) {
        this.autoReport = autoReport;
    }

    public boolean isUseSandbox() {
        return useSandbox;
    }

    public void setUseSandbox(boolean useSandbox) {
        this.useSandbox = useSandbox;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public WxPayTransport getWxPayRequest() {
        return wxPayRequest;
    }

    public void setWxPayRequest(WxPayTransport wxPayRequest) {
        this.wxPayRequest = wxPayRequest;
    }

}
