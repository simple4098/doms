package com.tomasky.doms.dto.qunar.response;

import java.util.List;

/**
 * DESC : 查询去哪儿酒店房型列表
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarProductionData {
    //pms 酒店code
    private String hotelNo;
    //渠道code
    private String channelCode;
    //酒店房型列表
    private List<QunarProductHotelInfo> channelHotelList;

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public List<QunarProductHotelInfo> getChannelHotelList() {
        return channelHotelList;
    }

    public void setChannelHotelList(List<QunarProductHotelInfo> channelHotelList) {
        this.channelHotelList = channelHotelList;
    }
}
