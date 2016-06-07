package com.tomasky.doms.dto.qunar;

/**
 * DESC :酒店关闭渠道直连调用该服务
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarRemoveAccount extends QunarBase{

    //pms 酒店代码
    private String hotelNo;
    //渠道账户用户名
    private String userAccount;

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
