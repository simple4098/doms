package com.tomasky.doms.dto.oms;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class OmsQunarHotel {

    private String channelHotelName;
    private String channelHotelNo;

    public OmsQunarHotel(String channelHotelName, String channelHotelNo) {
        this.channelHotelName = channelHotelName;
        this.channelHotelNo = channelHotelNo;
    }

    public OmsQunarHotel() {
    }

    public String getChannelHotelName() {
        return channelHotelName;
    }

    public void setChannelHotelName(String channelHotelName) {
        this.channelHotelName = channelHotelName;
    }

    public String getChannelHotelNo() {
        return channelHotelNo;
    }

    public void setChannelHotelNo(String channelHotelNo) {
        this.channelHotelNo = channelHotelNo;
    }
}
