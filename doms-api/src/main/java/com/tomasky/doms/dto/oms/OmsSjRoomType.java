package com.tomasky.doms.dto.oms;

/**
 * DESC : 房型上架架oms参数对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class OmsSjRoomType extends OmsXjRoomType {

    //渠道房型名称
    private String channelPhyRoomTypeName;
    //oms房型mingc
    private String roomTypeName;

    public String getChannelPhyRoomTypeName() {
        return channelPhyRoomTypeName;
    }

    public void setChannelPhyRoomTypeName(String channelPhyRoomTypeName) {
        this.channelPhyRoomTypeName = channelPhyRoomTypeName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
}
