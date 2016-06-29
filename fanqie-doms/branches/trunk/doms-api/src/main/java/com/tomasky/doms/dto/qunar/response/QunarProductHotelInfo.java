package com.tomasky.doms.dto.qunar.response;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class QunarProductHotelInfo {

    //渠道酒店code
    private String channelHotelNo;
    //渠道酒店名称
    private String channelHotelName;
    //房型列表
    private List<QunarProductionInfo> channelProductionList;

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

    public List<QunarProductionInfo> getChannelProductionList() {
        return channelProductionList;
    }

    public void setChannelProductionList(List<QunarProductionInfo> channelProductionList) {
        this.channelProductionList = channelProductionList;
    }
}

