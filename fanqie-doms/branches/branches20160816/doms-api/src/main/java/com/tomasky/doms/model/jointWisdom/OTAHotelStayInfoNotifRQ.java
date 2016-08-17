package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/7/25.
 * 订单审核请求参数
 */
@XmlRootElement(name = "OTA_HotelStayInfoNotifRQ", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OTAHotelStayInfoNotifRQ {
    private String version;
    private String xmlns;
    private List<StayInfo> stayInfos;

    @XmlAttribute(name = "Version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlAttribute(name = "xmlns")
    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    @XmlElement(name = "StayInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "StayInfos")
    public List<StayInfo> getStayInfos() {
        return stayInfos;
    }

    public void setStayInfos(List<StayInfo> stayInfos) {
        this.stayInfos = stayInfos;
    }
}
