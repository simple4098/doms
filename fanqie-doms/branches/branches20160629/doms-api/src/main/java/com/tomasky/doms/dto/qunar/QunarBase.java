package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.DomsConstants;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/6
 * @version: v1.0.0
 */
public class QunarBase extends QunarBaseBean {
    private String channelCode = DomsConstants.channelCode;
    //IP 地址 String 否 用户的真实 IP， Qunar 验证账号需要
    private String userIp = CommonApi.userIp;
    // 操作人 ID String 否 pms 操作人 ID
    private String operatorGuid = CommonApi.operatorGuid;
    private String operatorName = CommonApi.operatorName;

    public QunarBase() {
    }

    public QunarBase(String userIp, String operatorGuid, String operatorName) {
        this.userIp = userIp;
        this.operatorGuid = operatorGuid;
        this.operatorName = operatorName;
    }


    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }


    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
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


}
