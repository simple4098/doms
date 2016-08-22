package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "Profile", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderProfile {
    private OrderCustomer customer;

    @XmlElement(name = "Customer", namespace = "http://www.opentravel.org/OTA/2003/05")
    public OrderCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(OrderCustomer customer) {
        this.customer = customer;
    }
}
