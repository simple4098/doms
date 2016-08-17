package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "Profiles", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderProfiles {
    private List<OrderProfileInfo> orderProfileInfos;

    @XmlElement(name = "ProfileInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
    public List<OrderProfileInfo> getOrderProfileInfos() {
        return orderProfileInfos;
    }

    public void setOrderProfileInfos(List<OrderProfileInfo> orderProfileInfos) {
        this.orderProfileInfos = orderProfileInfos;
    }
}
