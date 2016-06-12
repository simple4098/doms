package com.tomasky.doms.dto.oms;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class ChannelInfo {

    private String userAccount;
    private List<OmsQunarHotel> channelHotelList;

    public ChannelInfo(String userAccount, List<OmsQunarHotel> channelHotelList) {
        this.userAccount = userAccount;
        this.channelHotelList = channelHotelList;
    }

    public ChannelInfo(String userAccount) {
        this.userAccount = userAccount;
    }

    public ChannelInfo() {
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public List<OmsQunarHotel> getChannelHotelList() {
        return channelHotelList;
    }

    public void setChannelHotelList(List<OmsQunarHotel> channelHotelList) {
        this.channelHotelList = channelHotelList;
    }
}
