package com.czarea.pay.sdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 刷卡撤单响应
 *
 * @author zhouzx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayReverseResponse extends WxPayBaseResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5530805673080592830L;
    @XStreamAlias("recall")
    private String recall;
}
