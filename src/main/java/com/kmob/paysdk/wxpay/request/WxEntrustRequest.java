package com.kmob.paysdk.wxpay.request;

import com.kmob.paysdk.annotation.Required;
import com.kmob.paysdk.exception.WxPayException;

public class WxEntrustRequest extends WxPayBaseRequest{
    @Required
    private String planId;
    @Required
    private String contractCode;
    @Required
    private int requestSerial;
    @Required
    private String contractDisplayAccount;
    @Required
    private String notifyUrl;
    @Required
    private String version;
    @Required
    private String timestamp;
    
    private String clientip;
    private String deviceid;
    private String mobile;
    private String email;
    private String qq;
    private String openid;
    private String creid;
    private String outerid;


    public String getPlanId() {
        return planId;
    }


    public void setPlanId(String planId) {
        this.planId = planId;
    }


    public String getContractCode() {
        return contractCode;
    }


    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }


    public int getRequestSerial() {
        return requestSerial;
    }


    public void setRequestSerial(int requestSerial) {
        this.requestSerial = requestSerial;
    }


    public String getContractDisplayAccount() {
        return contractDisplayAccount;
    }


    public void setContractDisplayAccount(String contractDisplayAccount) {
        this.contractDisplayAccount = contractDisplayAccount;
    }


    public String getNotifyUrl() {
        return notifyUrl;
    }


    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }


    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }


    public String getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getClientip() {
        return clientip;
    }


    public void setClientip(String clientip) {
        this.clientip = clientip;
    }


    public String getDeviceid() {
        return deviceid;
    }


    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getQq() {
        return qq;
    }


    public void setQq(String qq) {
        this.qq = qq;
    }


    public String getOpenid() {
        return openid;
    }


    public void setOpenid(String openid) {
        this.openid = openid;
    }


    public String getCreid() {
        return creid;
    }


    public void setCreid(String creid) {
        this.creid = creid;
    }


    public String getOuterid() {
        return outerid;
    }


    public void setOuterid(String outerid) {
        this.outerid = outerid;
    }


    @Override
    protected void checkConstraints() throws WxPayException {
        // TODO Auto-generated method stub
        
    }

}
