package com.czarea.pay.sdk.test.model;

import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

@Data
@XStreamAlias("root")
public class Case1 {
    @XStreamAlias("date")
    @XStreamAsAttribute
    private Date date;

    @XStreamImplicit
    private List<Demo> demos;

    public Date getDate() {
        return date;
    }
}
