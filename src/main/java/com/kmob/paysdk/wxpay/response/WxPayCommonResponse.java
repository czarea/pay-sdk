package com.kmob.paysdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * 微信支付结果仅包含有return 和result等相关信息的的属性类
 * </pre>
 * 
 * @author verne
 */

@XStreamAlias("xml")
public class WxPayCommonResponse extends WxPayBaseResponse {}
