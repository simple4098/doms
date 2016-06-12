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
    private Integer innId;
    //手机号码
    private String mobile;
    //渠道id
    private String otaId;
    //手机验证码
    private String phoneCode;
    private String operatorGuid;
    private String operatorName;
    private String innName;
    private String userIp;
    private String data;
    //渠道酒店code
    private String channelHotelNo;

    public OmsPram(Integer innId, String mobile, String otaId, String phoneCode,String innName,String operatorGuid,String operatorName) {
        this.innId = innId;
        this.mobile = mobile;
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

    public OmsPram(String data) {
        this.data = data;
    }

    public OmsPram() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
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




}
