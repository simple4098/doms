package com.tomasky.doms.dto.qunar;

/**
 * DESC : 匹配渠道酒店
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarDockingHotel extends QunarDockingRemoveHotel{

    private String userAccount;// 渠道账号用户名

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
