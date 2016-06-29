package com.tomasky.doms.dto.oms;

/**
 * DESC : 房型下架oms参数对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class OmsXjRoomType extends OmsHotel {

    //渠道房型code
    private String channelPhyRoomTypeCode;
    //oms房型id
    private String roomTypeId;
    //渠道价格计划code
    private String channelRatePlanCode;

    public String getChannelPhyRoomTypeCode() {
        return channelPhyRoomTypeCode;
    }

    public void setChannelPhyRoomTypeCode(String channelPhyRoomTypeCode) {
        this.channelPhyRoomTypeCode = channelPhyRoomTypeCode;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getChannelRatePlanCode() {
        return channelRatePlanCode;
    }

    public void setChannelRatePlanCode(String channelRatePlanCode) {
        this.channelRatePlanCode = channelRatePlanCode;
    }
}
