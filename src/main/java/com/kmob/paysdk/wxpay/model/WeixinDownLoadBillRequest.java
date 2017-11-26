package com.kmob.paysdk.wxpay.model;

/**
 * 下载账单实体
 *
 * @author verne
 */
public class WeixinDownLoadBillRequest {
    /**
     * 对账单日期
     */
    private String billDate;
    /**
     * 账单类型
     */
    private String billType="ALL";

    /**
     * 压缩账单
     */
    private String tarType;

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getTarType() {
        return tarType;
    }

    public void setTarType(String tarType) {
        this.tarType = tarType;
    }

}
