package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/28.
 */
@XmlRootElement(name = "HotelReservationID", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderHotelReservationID {
    private String resIdType;
    private String resIdValue;

    @XmlAttribute(namespace = "ResID_Type")
    public String getResIdType() {
        return resIdType;
    }

    public void setResIdType(String resIdType) {
        this.resIdType = resIdType;
    }

    @XmlAttribute(name = "ResID_Value")
    public String getResIdValue() {
        return resIdValue;
    }

    public void setResIdValue(String resIdValue) {
        this.resIdValue = resIdValue;
    }
}
