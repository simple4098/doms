package com.tomasky.doms.dto;

import com.tomasky.doms.dto.oms.ChannelInfo;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class OmsPram {
    //客栈id
    //private Integer innId;
    private String accountId;
    //手机号码
    private String mobile;
    private String userAccount;
    //渠道id
    private String otaId;
    //手机验证码
    private String phoneCode;
    private String operatorGuid;
    private String operatorName;
    private String innName;
    private String userIp;
    private String param;
    //渠道酒店code
    private String channelHotelNo;

    public OmsPram() {
    }

    public OmsPram(String accountId, String mobile, String otaId, String phoneCode,String innName,String operatorGuid,String operatorName) {
        this.accountId = accountId;
        this.mobile = mobile;
        this.userAccount = mobile;
        this.otaId = otaId;
        this.phoneCode = phoneCode;
        this.innName = innName;
        this.operatorGuid=operatorGuid;
        this.operatorName=operatorName;
    }

    public String getChannelHotelNo() {
        return channelHotelNo;
    }

    public void setChannelHotelNo(String channelHotelNo) {
        this.channelHotelNo = channelHotelNo;
    }

    public OmsPram(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    public String getOperatorGuid() {
        return operatorGuid;
    }

    public void setOperatorGuid(String operatorGuid) {
        this.operatorGuid = operatorGuid;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }



    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
