package com.kmob.paysdk.wxpay;

/**
 * 微信代金卷
 *
 * @author verne
 */
public class WeixinCoupon {
    private String id;
    private String fee;
    private String type;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFee() {
        return fee;
    }
    public void setFee(String fee) {
        this.fee = fee;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "WeixinCoupon [id=" + id + ", fee=" + fee + ", type=" + type + "]";
    }
    
}
