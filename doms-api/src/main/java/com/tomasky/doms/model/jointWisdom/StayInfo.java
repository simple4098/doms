package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/25.
 */
@XmlRootElement(name = "StayInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
public class StayInfo {
    private OrderHotelReservation hotelReservation;

    @XmlElement(name = "HotelReservation", namespace = "http://www.opentravel.org/OTA/2003/05")
    public OrderHotelReservation getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(OrderHotelReservation hotelReservation) {
        this.hotelReservation = hotelReservation;
    }
}
