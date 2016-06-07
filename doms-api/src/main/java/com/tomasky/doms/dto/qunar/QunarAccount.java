package com.tomasky.doms.dto.qunar;

/**
 * DESC : 去哪儿开通渠道
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarAccount extends QunarBase {
    //去哪儿网渠道请使用注册用户绑定的手机号
    private String userAccount;
    //手机动态码
    private String verificationCode;

    public QunarAccount() {
    }

    public QunarAccount(String userIp, String operatorGuid, String operatorName, String userAccount, String verificationCode) {
        super(userIp, operatorGuid, operatorName);
        this.userAccount = userAccount;
        this.verificationCode = verificationCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
