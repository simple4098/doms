package com.tomasky.doms.model.jointWisdom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/7/28.
 */
@XmlRootElement(name = "RoomType", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderRoomType {
    private String roomTypeCode;
    private String RoomId;

    @XmlAttribute(name = "RoomTypeCode")
    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    @XmlAttribute(name = "RoomID")
    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }
}
