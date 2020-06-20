package com.czarea.pay.sdk.dto;

import java.io.Serializable;

/**
 * 返回实体类
 *
 * @author zhouzx
 */
public class ResultInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 结果码
     */
    protected int code;
    /**
     * 结果详情
     */
    protected String msg;

    /**
     * 结果数据，如果返回错误一般不会返回data，直接为null
     */
    protected Object data;

    public ResultInfo() {

    }

    public ResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultInfo [code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }

}
