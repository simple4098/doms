package com.tomasky.doms.dto.qunar.response;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarHotelInfo {
    //pms 酒店code
    private String hotelNo;
    //渠道code
    private String channelCode;
    private List<AccountChannelHotel> accountChannelHotelList;

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

    public List<AccountChannelHotel> getAccountChannelHotelList() {
        return accountChannelHotelList;
    }

    public void setAccountChannelHotelList(List<AccountChannelHotel> accountChannelHotelList) {
        this.accountChannelHotelList = accountChannelHotelList;
    }
}
