package com.kmob.paysdk.test.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;  

@Data
@XStreamAlias("example")  
public class Example {  
      
    @XStreamAlias("element")  
    private String element;  
}
