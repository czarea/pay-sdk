package com.kmob.paysdk.wxpay.response;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 刷卡撤单响应
 *
 * @author verne
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayReverseResponse extends WxPayBaseResponse implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 5530805673080592830L;
    @XStreamAlias("recall")
    private String recall;
}
