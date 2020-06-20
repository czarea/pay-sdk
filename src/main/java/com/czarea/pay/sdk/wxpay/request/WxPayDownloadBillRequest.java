package com.czarea.pay.sdk.wxpay.request;

import com.czarea.pay.sdk.annotation.Required;
import com.czarea.pay.sdk.exception.WxPayException;
import com.czarea.pay.sdk.wxpay.constant.WxPayInteractiveConstants.BillType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *   微信支付下载对账单请求参数类
 * </pre>
 *
 * @author zhouzx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayDownloadBillRequest extends WxPayBaseRequest {

    private static final String[] BILL_TYPES = new String[]{BillType.ALL, BillType.SUCCESS,
        BillType.REFUND, BillType.RECHARGE_REFUND};
    private static final String TAR_TYPE_GZIP = "GZIP";

    /**
     * <pre>
     * 设备号
     * device_info
     * 否
     * String(32)
     * 13467007045764
     * 终端设备号
     * </pre>
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * <pre>
     * 账单类型
     * bill_type
     * 是
     * ALL
     * String(8)
     * --ALL，返回当日所有订单信息，默认值
     * --SUCCESS，返回当日成功支付的订单
     * --REFUND，返回当日退款订单
     * </pre>
     */
    @Required
    @XStreamAlias("bill_type")
    private String billType;

    /**
     * <pre>
     * 对账单日期
     * bill_date
     * 是
     * String(8)
     * 20140603
     * 下载对账单的日期，格式：20140603
     * </pre>
     */
    @Required
    @XStreamAlias("bill_date")
    private String billDate;

    /**
     * <pre>
     * 压缩账单
     * tar_type
     * 否
     * String(8)
     * GZIP
     * 非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * </pre>
     */
    @XStreamAlias("tar_type")
    private String tarType;

    @Override
    protected void checkConstraints() throws WxPayException {
        if (StringUtils.isNotBlank(this.getTarType()) && !TAR_TYPE_GZIP.equals(this.getTarType())) {
            throw new WxPayException("tar_type值如果存在，只能为GZIP");
        }

        if (!ArrayUtils.contains(BILL_TYPES, this.getBillType())) {
            throw new WxPayException(String.format("bill_type目前必须为%s其中之一,实际值：%s",
                Arrays.toString(BILL_TYPES), this.getBillType()));
        }
    }
}
