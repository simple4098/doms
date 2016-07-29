package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "Customer", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderCustomer {
    private OrderPersonName personName;

    @XmlElement(name = "PersonName", namespace = "http://www.opentravel.org/OTA/2003/05")
    public OrderPersonName getPersonName() {
        return personName;
    }

    public void setPersonName(OrderPersonName personName) {
        this.personName = personName;
    }
}
