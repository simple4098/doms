package com.tomasky.doms.model.jointWisdom;

import com.tomasky.doms.service.jointwisdomService.BasicPropertyInfo;
import com.tomasky.doms.service.jointwisdomService.TimeSpan;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/7/26.
 */
@XmlRootElement(name = "RoomStay", namespace = "http://www.opentravel.org/OTA/2003/05")
public class OrderRoomStay {
    private TimeSpan timeSpan;
    private BasicPropertyInfo basicPropertyInfo;
    private List<OrderRoomType> roomTypeList;


    @XmlElement(name = "RoomType", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "RoomTypes")
    public List<OrderRoomType> getRoomTypeList() {
        return roomTypeList;
    }

    public void setRoomTypeList(List<OrderRoomType> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }

    @XmlElement(name = "TimeSpan", namespace = "http://www.opentravel.org/OTA/2003/05")
    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }

    @XmlElement(name = "BasicPropertyInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
    public BasicPropertyInfo getBasicPropertyInfo() {
        return basicPropertyInfo;
    }

    public void setBasicPropertyInfo(BasicPropertyInfo basicPropertyInfo) {
        this.basicPropertyInfo = basicPropertyInfo;
    }
}
