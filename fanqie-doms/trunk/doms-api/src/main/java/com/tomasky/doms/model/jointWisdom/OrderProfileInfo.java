package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "ProfileInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderProfileInfo {
    private OrderProfile orderProfile;

    @XmlElement(name = "Profile", namespace = "http://www.opentravel.org/OTA/2003/05")
    public OrderProfile getOrderProfile() {
        return orderProfile;
    }

    public void setOrderProfile(OrderProfile orderProfile) {
        this.orderProfile = orderProfile;
    }
}
