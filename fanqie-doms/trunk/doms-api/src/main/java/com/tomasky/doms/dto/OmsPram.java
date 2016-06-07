package com.tomasky.doms.dto;

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
    private String innCode;

    public OmsPram(Integer innId, String mobile, String otaId, String phoneCode, String operatorGuid, String operatorName) {
        this.innId = innId;
        this.mobile = mobile;
        this.otaId = otaId;
        this.phoneCode = phoneCode;
        this.operatorGuid = operatorGuid;
        this.operatorName = operatorName;
    }

    public OmsPram() {
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

    public String getInnCode() {
        innCode = otaId+"_"+innId;
        return innCode;
    }


}
