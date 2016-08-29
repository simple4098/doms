package com.tomasky.doms.dto.qunar.response;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class QunarRoomTypeHotelInfo {

    //渠道酒店code
    private String channelHotelNo;
    //渠道酒店名称
    private String channelHotelName;
    //房型列表
    private List<QunarRoomTypeInfo> channelPhyRoomTypeList;

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

    public List<QunarRoomTypeInfo> getChannelPhyRoomTypeList() {
        return channelPhyRoomTypeList;
    }

    public void setChannelPhyRoomTypeList(List<QunarRoomTypeInfo> channelPhyRoomTypeList) {
        this.channelPhyRoomTypeList = channelPhyRoomTypeList;
    }
}

