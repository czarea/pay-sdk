package com.czarea.pay.sdk.wxpay.transport;

import com.czarea.pay.sdk.wxpay.config.WxPayConfig;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxPayTransport {

    private static final Logger logger = LoggerFactory.getLogger(WxPayTransport.class);

    private WxPayConfig config;

    public WxPayTransport(WxPayConfig config) throws Exception {

        this.config = config;
    }

    /**
     * 请求，只请求一次，不做重试
     *
     * @param useCert 是否使用证书，针对退款、撤销等操作
     */
    private String requestOnce(final String domain, String urlSuffix, String uuid, String data,
        int connectTimeoutMs, int readTimeoutMs, boolean useCert) throws Exception {
        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            char[] password = config.getMchID().toCharArray();
            InputStream certStream = config.getCertStream();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContext, new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());

            connManager =
                new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http",
                            PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslConnectionSocketFactory).build(),
                    null, null, null);
        } else {
            connManager =
                new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http",
                            PlainConnectionSocketFactory.getSocketFactory())
                        .register("https",
                            SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                    null, null, null);
        }

        HttpClient httpClient =
            HttpClientBuilder.create().setConnectionManager(connManager).build();

        String url = "https://" + domain + urlSuffix;
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs)
            .setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", "wxpay sdk java v-0.0.1" + config.getMchID());
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);

        Header[] contentTypeHeaders = httpResponse.getHeaders("Content-Type");
        boolean gzipContentType = false;

        for (Header head : contentTypeHeaders) {
            if (head.getValue().contains("application/x-gzip")) {
                gzipContentType = true;
                break;
            }
        }

        HttpEntity httpEntity = httpResponse.getEntity();
        if (!gzipContentType) {
            String content = EntityUtils.toString(httpEntity, "UTF-8");
            return content;
        } else {
            //TODO 有可能会重复
            SimpleDateFormat allTime = new SimpleDateFormat("yyyyMMddHHmmss");

            String fileName = config.getBillPath() + allTime.format(new Date()) + WxPayUtil.generateNonceStr() + ".gzip";
            Files.copy(httpEntity.getContent(), Paths.get(fileName),
                StandardCopyOption.REPLACE_EXISTING);
            return "save file succes to " + fileName;
        }


    }


    private String request(String urlSuffix, String uuid, String data, int connectTimeoutMs,
        int readTimeoutMs, boolean useCert, boolean autoReport) throws Exception {
        Exception exception = null;
        long elapsedTimeMillis = 0;
        long startTimestampMs = WxPayUtil.getCurrentTimestampMs();
        boolean firstHasDnsErr = false;
        boolean firstHasConnectTimeout = false;
        boolean firstHasReadTimeout = false;
        IWxPayDomain.DomainInfo domainInfo = config.getWXPayDomain().getDomain(config);
        if (domainInfo == null) {
            throw new Exception("WXPayConfig.getWXPayDomain().getDomain() is empty or null");
        }
        try {
            long start = System.currentTimeMillis();
            String result = requestOnce(domainInfo.domain, urlSuffix, uuid, data, connectTimeoutMs,
                readTimeoutMs, useCert);
            elapsedTimeMillis = WxPayUtil.getCurrentTimestampMs() - startTimestampMs;
            config.getWXPayDomain().report(domainInfo.domain, elapsedTimeMillis, null);
            WxPayReport.getInstance(config).report(uuid, elapsedTimeMillis, domainInfo.domain,
                domainInfo.primaryDomain, connectTimeoutMs, readTimeoutMs, firstHasDnsErr,
                firstHasConnectTimeout, firstHasReadTimeout);
            long end = System.currentTimeMillis();
            logger.debug("\n【请求地址】：{}\n【请求数据】：\n{}\n【响应数据】：\n{}\n【耗时】\n{}", urlSuffix, data,
                result, (end - start));
            return result;
        } catch (UnknownHostException ex) { // dns 解析错误，或域名不存在
            exception = ex;
            firstHasDnsErr = true;
            elapsedTimeMillis = WxPayUtil.getCurrentTimestampMs() - startTimestampMs;
            WxPayUtil.getLogger().warn("UnknownHostException for domainInfo {}", domainInfo);
            WxPayReport.getInstance(config).report(uuid, elapsedTimeMillis, domainInfo.domain,
                domainInfo.primaryDomain, connectTimeoutMs, readTimeoutMs, firstHasDnsErr,
                firstHasConnectTimeout, firstHasReadTimeout);
        } catch (ConnectTimeoutException ex) {
            exception = ex;
            firstHasConnectTimeout = true;
            elapsedTimeMillis = WxPayUtil.getCurrentTimestampMs() - startTimestampMs;
            WxPayUtil.getLogger().warn("connect timeout happened for domainInfo {}",
                domainInfo);
            WxPayReport.getInstance(config).report(uuid, elapsedTimeMillis, domainInfo.domain,
                domainInfo.primaryDomain, connectTimeoutMs, readTimeoutMs, firstHasDnsErr,
                firstHasConnectTimeout, firstHasReadTimeout);
        } catch (SocketTimeoutException ex) {
            exception = ex;
            firstHasReadTimeout = true;
            elapsedTimeMillis = WxPayUtil.getCurrentTimestampMs() - startTimestampMs;
            WxPayUtil.getLogger().warn("timeout happened for domainInfo {}", domainInfo);
            WxPayReport.getInstance(config).report(uuid, elapsedTimeMillis, domainInfo.domain,
                domainInfo.primaryDomain, connectTimeoutMs, readTimeoutMs, firstHasDnsErr,
                firstHasConnectTimeout, firstHasReadTimeout);
        } catch (Exception ex) {
            exception = ex;
            elapsedTimeMillis = WxPayUtil.getCurrentTimestampMs() - startTimestampMs;
            WxPayReport.getInstance(config).report(uuid, elapsedTimeMillis, domainInfo.domain,
                domainInfo.primaryDomain, connectTimeoutMs, readTimeoutMs, firstHasDnsErr,
                firstHasConnectTimeout, firstHasReadTimeout);
        }
        config.getWXPayDomain().report(domainInfo.domain, elapsedTimeMillis, exception);
        throw exception;
    }


    /**
     * 可重试的，非双向认证的请求
     */
    public String requestWithoutCert(String urlSuffix, String uuid, String data, boolean autoReport)
        throws Exception {
        return this.request(urlSuffix, uuid, data, config.getHttpConnectTimeoutMs(),
            config.getHttpReadTimeoutMs(), false, autoReport);
        // return requestWithoutCert(urlSuffix, uuid, data, config.getHttpConnectTimeoutMs(),
        // config.getHttpReadTimeoutMs(), autoReport);
    }

    /**
     * 可重试的，非双向认证的请求
     */
    public String requestWithoutCert(String urlSuffix, String uuid, String data,
        int connectTimeoutMs, int readTimeoutMs, boolean autoReport) throws Exception {
        return this.request(urlSuffix, uuid, data, connectTimeoutMs, readTimeoutMs, false,
            autoReport);

        /*
         * String result; Exception exception; boolean shouldRetry = false;
         *
         * boolean useCert = false; try { result = requestOnce(domain, urlSuffix, uuid, data,
         * connectTimeoutMs, readTimeoutMs, useCert); return result; } catch (UnknownHostException
         * ex) { // dns 解析错误，或域名不存在 exception = ex;
         * WXPayUtil.getLogger().warn("UnknownHostException for domain {}, try to use {}", domain,
         * this.primaryDomain); shouldRetry = true; } catch (ConnectTimeoutException ex) { exception
         * = ex; WXPayUtil.getLogger().warn("connect timeout happened for domain {}, try to use {}",
         * domain, this.primaryDomain); shouldRetry = true; } catch (SocketTimeoutException ex) {
         * exception = ex; shouldRetry = false; } catch (Exception ex) { exception = ex; shouldRetry
         * = false; }
         *
         * if (shouldRetry) { result = requestOnce(this.primaryDomain, urlSuffix, uuid, data,
         * connectTimeoutMs, readTimeoutMs, useCert); return result; } else { throw exception; }
         */
    }

    /**
     * 可重试的，双向认证的请求
     */
    public String requestWithCert(String urlSuffix, String uuid, String data, boolean autoReport)
        throws Exception {
        return this.request(urlSuffix, uuid, data, config.getHttpConnectTimeoutMs(),
            config.getHttpReadTimeoutMs(), true, autoReport);
        // return requestWithCert(urlSuffix, uuid, data, config.getHttpConnectTimeoutMs(),
        // config.getHttpReadTimeoutMs(), autoReport);
    }

    /**
     * 可重试的，双向认证的请求
     */
    public String requestWithCert(String urlSuffix, String uuid, String data, int connectTimeoutMs,
        int readTimeoutMs, boolean autoReport) throws Exception {
        return this.request(urlSuffix, uuid, data, connectTimeoutMs, readTimeoutMs, true,
            autoReport);

        /*
         * String result; Exception exception; boolean shouldRetry = false;
         *
         * boolean useCert = true; try { result = requestOnce(domain, urlSuffix, uuid, data,
         * connectTimeoutMs, readTimeoutMs, useCert); return result; } catch
         * (ConnectTimeoutException ex) { exception = ex; WXPayUtil.getLogger().warn(String.
         * format("connect timeout happened for domain {}, try to use {}", domain,
         * this.primaryDomain)); shouldRetry = true; } catch (SocketTimeoutException ex) { exception
         * = ex; shouldRetry = false; } catch (Exception ex) { exception = ex; shouldRetry = false;
         * }
         *
         * if (shouldRetry && this.primaryDomain != null) { result = requestOnce(this.primaryDomain,
         * urlSuffix, uuid, data, connectTimeoutMs, readTimeoutMs, useCert, autoReport); return
         * result; } else { throw exception; }
         */
    }
}
