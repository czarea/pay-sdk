package com.czarea.pay.sdk.wxpay.config;

import com.czarea.pay.sdk.wxpay.transport.IWxPayDomain;
import com.czarea.pay.sdk.wxpay.transport.WxPayDomainSimpleImpl;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信支付配置
 *
 * @author zhouzx
 */
@ConfigurationProperties(prefix = "wxpay")
public class WxPayConfig {

    /**
     * appId
     */
    private String appId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户密钥
     */
    private String key;
    /**
     * 证书路径
     */
    private String certPath = "E://微信开发//通证书//apiclient_cert.p12";

    /**
     * 账单下载Gzip文件保存路径
     */
    private String billPath = "E://微信开发//";

    /**
     * 超时时间
     */
    private long readTimeoutMs = 10000;
    /**
     * 连接超时时间
     */
    private long connectTimeoutMs = 2000;
    /**
     * 支付通知接收地址
     */
    private String notifyUrl;

    /**
     * 是否沙箱环境
     */
    private boolean useSandbox;
    /**
     * 是否上报请求信息
     */
    private boolean shouldAutoReport = false;
    /**
     * 上报最大队列
     */
    private int reportQueueMaxSize = 10000;

    /**
     * 主域名
     */
    private String primaryDomain = "api.mch.weixin.qq.com";
    /**
     * 候补域名
     */
    private String alternateDomain = "api2.mch.weixin.qq.com";
    /**
     * 报表线程数大小
     */
    private int reportWorkerNum = 1;
    /**
     * 报表批量大小
     */
    private int reportBatchSize = 2;

    /**
     * 刷卡支付查询间隔
     */
    private int micropayInterval = 10;

    /**
     * 刷卡支付查询总次数
     */
    private int micropayTimes = 3;

    public boolean isUseSandbox() {
        return useSandbox;
    }

    public void setUseSandbox(boolean useSandbox) {
        this.useSandbox = useSandbox;
    }

    public boolean shouldAutoReport() {
        return true;
    }

    public int getReportQueueMaxSize() {
        return reportQueueMaxSize;
    }

    private byte[] certData;

    public String getAppID() {
        return appId;
    }

    public String getMchID() {
        return mchId;
    }

    public String getKey() {
        return key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public IWxPayDomain getWXPayDomain() {
        return WxPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return primaryDomain;
    }

    public String getAlternateDomain() {
        return alternateDomain;
    }

    public int getReportWorkerNum() {
        return reportWorkerNum;
    }

    public int getReportBatchSize() {
        return reportBatchSize;
    }

    public boolean isShouldAutoReport() {
        return shouldAutoReport;
    }

    public void setShouldAutoReport(boolean shouldAutoReport) {
        this.shouldAutoReport = shouldAutoReport;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        File file = new File(certPath);
        InputStream certStream;
        try {
            certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
        } catch (IOException e) {
        }
        this.certPath = certPath;
    }

    public String getBillPath() {
        return billPath;
    }

    public void setBillPath(String billPath) {
        this.billPath = billPath;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public long getReadTimeoutMs() {
        return readTimeoutMs;
    }

    public void setReadTimeoutMs(long readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }

    public long getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(long connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public byte[] getCertData() {
        return certData;
    }

    public void setReportQueueMaxSize(int reportQueueMaxSize) {
        this.reportQueueMaxSize = reportQueueMaxSize;
    }

    public void setPrimaryDomain(String primaryDomain) {
        this.primaryDomain = primaryDomain;
    }

    public void setAlternateDomain(String alternateDomain) {
        this.alternateDomain = alternateDomain;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public void setReportWorkerNum(int reportWorkerNum) {
        this.reportWorkerNum = reportWorkerNum;
    }

    public void setReportBatchSize(int reportBatchSize) {
        this.reportBatchSize = reportBatchSize;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMicropayInterval() {
        return micropayInterval;
    }

    public void setMicropayInterval(int micropayInterval) {
        this.micropayInterval = micropayInterval;
    }

    public int getMicropayTimes() {
        return micropayTimes;
    }

    public void setMicropayTimes(int micropayTimes) {
        this.micropayTimes = micropayTimes;
    }

}
