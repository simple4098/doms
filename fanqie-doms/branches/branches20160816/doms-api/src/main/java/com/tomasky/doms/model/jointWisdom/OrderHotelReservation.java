package com.tomasky.doms.model.jointWisdom;

import com.tomasky.doms.enums.ResStatus;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/7/25.
 */
@XmlRootElement(name = "HotelReservation", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderHotelReservation {
    private ResStatus resStatus;
    private List<OrderRoomStay> roomStayList;
    private List<OrderResGuest> resGuestList;
    private OrderResGlobalInfo resGlobalInfo;

    @XmlElement(name = "ResGlobalInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
    public OrderResGlobalInfo getResGlobalInfo() {
        return resGlobalInfo;
    }

    public void setResGlobalInfo(OrderResGlobalInfo resGlobalInfo) {
        this.resGlobalInfo = resGlobalInfo;
    }


    @XmlElement(name = "ResGuest", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "ResGuests")
    public List<OrderResGuest> getResGuestList() {
        return resGuestList;
    }

    public void setResGuestList(List<OrderResGuest> resGuestList) {
        this.resGuestList = resGuestList;
    }

    @XmlAttribute(name = "ResStatus")
    public ResStatus getResStatus() {
        return resStatus;
    }

    public void setResStatus(ResStatus resStatus) {
        this.resStatus = resStatus;
    }

    @XmlElement(name = "RoomStay", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "RoomStays")
    public List<OrderRoomStay> getRoomStayList() {
        return roomStayList;
    }

    public void setRoomStayList(List<OrderRoomStay> roomStayList) {
        this.roomStayList = roomStayList;
    }
}
