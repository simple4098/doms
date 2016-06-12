package com.tomasky.doms.dto.qunar;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class QunarDockingPhyRoomType extends QunarDockingRemovePhyRoomType{

    //渠道房型名称
    private String channelPhyRoomTypeName;
    //oms房型名称
    private String phyRoomTypeName;

    public String getChannelPhyRoomTypeName() {
        return channelPhyRoomTypeName;
    }

    public void setChannelPhyRoomTypeName(String channelPhyRoomTypeName) {
        this.channelPhyRoomTypeName = channelPhyRoomTypeName;
    }

    public String getPhyRoomTypeName() {
        return phyRoomTypeName;
    }

    public void setPhyRoomTypeName(String phyRoomTypeName) {
        this.phyRoomTypeName = phyRoomTypeName;
    }
}
