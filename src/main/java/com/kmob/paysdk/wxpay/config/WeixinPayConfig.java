package com.kmob.paysdk.wxpay.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.kmob.paysdk.wxpay.transport.IWeixinPayDomain;
import com.kmob.paysdk.wxpay.transport.WeixinPayDomainSimpleImpl;

/**
 * 微信支付配置
 *
 * @author verne
 */
@Component
@ConfigurationProperties(prefix = "wxpay")
public class WeixinPayConfig {
    private boolean shouldAutoReport =true;
    private int reportQueueMaxSize = 10000;
    private String certPath;
    private String appId;
    private String mchId;
    private long readTimeoutMs = 10000;
    private long connectTimeoutMs =2000;
    private String primaryDomain = "api.mch.weixin.qq.com"; 
    private String alternateDomain ="api2.mch.weixin.qq.com";
    private int reportWorkerNum =1;
    private int reportBatchSize =2;
    
    public boolean shouldAutoReport() {
        return true;
    }

    public int getReportQueueMaxSize() {
        return reportQueueMaxSize;
    }

    private byte[] certData;

    private WeixinPayConfig() throws Exception {
        // String certPath = "D://CERT/common/apiclient_cert.p12";
        // File file = new File(certPath);
        // InputStream certStream = new FileInputStream(file);
        // this.certData = new byte[(int) file.length()];
        // certStream.read(this.certData);
        // certStream.close();
    }

    public String getAppID() {
        return "wx7a38a3b777c31606";
    }

    public String getMchID() {
        return "1493001312";
    }

    public String getKey() {
        return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCA";
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

    public IWeixinPayDomain getWXPayDomain() {
        return WeixinPayDomainSimpleImpl.instance();
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
        this.certPath = certPath;
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

    public void setCertData(byte[] certData) {
        this.certData = certData;
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

    public void setReportWorkerNum(int reportWorkerNum) {
        this.reportWorkerNum = reportWorkerNum;
    }

    public void setReportBatchSize(int reportBatchSize) {
        this.reportBatchSize = reportBatchSize;
    }
}
