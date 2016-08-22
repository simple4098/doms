package com.tomasky.doms.dto.qunar;

/**
 * DESC : 解除酒店匹配
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarDockingRemoveHotel extends QunarBase {
    private String hotelNo;// PMS 酒店代码 String 否
    private String hotelName;// PMS 酒店名称 String 否

    private String channelHotelNo;// 渠道酒店代码 String 是
    private String channelHotelName;// 渠道酒店名称

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }



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
}
