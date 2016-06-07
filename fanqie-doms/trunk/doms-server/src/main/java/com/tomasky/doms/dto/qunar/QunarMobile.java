package com.tomasky.doms.dto.qunar;

/**
 * DESC : 去哪儿渠道 发送动态验证码
 * @author : 番茄木-ZLin
 * @data : 2016/6/6
 * @version: v1.0.0
 */
public class QunarMobile extends QunarBase {

    private String mobile;// 手机号码 String 否 绑定渠道账号的手机号码


    public QunarMobile(String mobile) {
        this.mobile = mobile;
    }

    public QunarMobile(String userIp, String operatorGuid, String operatorName, String mobile) {
        super(userIp, operatorGuid, operatorName);
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
