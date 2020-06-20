package com.czarea.pay.sdk.test.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@Data
@XStreamAlias("demo")
public class Demo {

    @XStreamAlias("element")
    private String element;

}
