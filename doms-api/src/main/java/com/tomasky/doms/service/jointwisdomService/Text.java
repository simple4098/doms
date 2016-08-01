package com.tomasky.doms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by wangdayin on 2016/1/11.
 * 房型描述
 */
@XmlRootElement(name = "Text", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Text {
    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
