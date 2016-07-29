package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "ResGuest", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderResGuest {
    private OrderProfiles orderProfiles;

    @XmlElement(name = "Profiles", namespace = "http://www.opentravel.org/OTA/2003/05")
    public OrderProfiles getOrderProfiles() {
        return orderProfiles;
    }

    public void setOrderProfiles(OrderProfiles orderProfiles) {
        this.orderProfiles = orderProfiles;
    }
}
