package com.tomasky.doms.dto.qunar.response;

/**
 * DESC : 去哪儿酒店对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class ChannelHotel {

    private String channelHotelNo;// "taiyuan52735",
    private String channelHotelName;//麒麟酒店",
    private String dockingStatus;// "1", //1：已对接， 2：未对接
    private String otherRelationHotelNo;// "beijing0000",
    private String otherRelationHotelName;// "北京去呼呼酒店"

    public String getChannelHotelNo() {
        return channelHotelNo;
    }

    public void setChannelHotelNo(String channelHotelNo) {
        this.channelHotelNo = channelHotelNo;
    }

    public String getChannelHotelName() {
        return channelHotelName;
    }

    public void setChannelHotelName(String channelHotelName) {
        this.channelHotelName = channelHotelName;
    }

    public String getDockingStatus() {
        return dockingStatus;
    }

    public void setDockingStatus(String dockingStatus) {
        this.dockingStatus = dockingStatus;
    }

    public String getOtherRelationHotelNo() {
        return otherRelationHotelNo;
    }

    public void setOtherRelationHotelNo(String otherRelationHotelNo) {
        this.otherRelationHotelNo = otherRelationHotelNo;
    }

    public String getOtherRelationHotelName() {
        return otherRelationHotelName;
    }

    public void setOtherRelationHotelName(String otherRelationHotelName) {
        this.otherRelationHotelName = otherRelationHotelName;
    }
}
