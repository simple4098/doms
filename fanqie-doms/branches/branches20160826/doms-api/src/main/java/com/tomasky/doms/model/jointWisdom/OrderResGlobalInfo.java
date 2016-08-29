package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "ResGlobalInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderResGlobalInfo {
    private List<OrderHotelReservationID> hotelReservationIDList;

    @XmlElement(name = "HotelReservationID", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "HotelReservationIDs")
    public List<OrderHotelReservationID> getHotelReservationIDList() {
        return hotelReservationIDList;
    }

    public void setHotelReservationIDList(List<OrderHotelReservationID> hotelReservationIDList) {
        this.hotelReservationIDList = hotelReservationIDList;
    }
}
