package com.czarea.pay.sdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhouzx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPaySandboxSignKeyResponse extends WxPayBaseResponse {

    /**
     * <pre>
     * 沙箱密钥
     * sandbox_signkey
     * 否
     * 013467007045764
     * String(32)
     * 返回的沙箱密钥
     * </pre>
     */
    @XStreamAlias("sandbox_signkey")
    private String sandboxSignKey;

}
