package com.tomasky.doms.dto.qunar.response;

import java.util.List;

/**
 * DESC : 渠道帐号 酒店信息
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class AccountChannelHotel {
    //去哪儿帐号
    private String userAccount;
    //0：正常 3：密码失效 4： QUNAR 删除账 号与渠道酒店的绑定关系
    private String userAccountStatus;
    //酒店集合
    private List<ChannelHotel> channelHotelList;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserAccountStatus() {
        return userAccountStatus;
    }

    public void setUserAccountStatus(String userAccountStatus) {
        this.userAccountStatus = userAccountStatus;
    }

    public List<ChannelHotel> getChannelHotelList() {
        return channelHotelList;
    }

    public void setChannelHotelList(List<ChannelHotel> channelHotelList) {
        this.channelHotelList = channelHotelList;
    }
}
