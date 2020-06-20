package com.czarea.pay.sdk.wxpay.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 关闭订单结果对象类
 * </pre>
 *
 * @author zhouzx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayOrderCloseResponse extends WxPayBaseResponse {

    /**
     * 业务结果描述
     */
    @XStreamAlias("result_msg")
    private String resultMsg;
}
