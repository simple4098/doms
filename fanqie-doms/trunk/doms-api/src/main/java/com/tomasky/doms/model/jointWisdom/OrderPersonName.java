package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "PersonName", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderPersonName {
    private String givenName;//名
    private String surname;//姓

    @XmlElement(name = "GivenName", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @XmlElement(name = "Surname", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
