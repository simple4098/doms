package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.Constants;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/6
 * @version: v1.0.0
 */
public class QunarBase {
    //参数校验码
    private String  hmac;
    //接口版本号 所使用的 api 文档提供的版本号
    private String version = CommonApi.version;
    //PMS 代码
    private String pmsId= CommonApi.pmsId;
    private String channelCode=Constants.channelCode;
    //IP 地址 String 否 用户的真实 IP， Qunar 验证账号需要
    private String userIp ;
    // 操作人 ID String 否 pms 操作人 ID
    private String operatorGuid;
    private String operatorName;

    public QunarBase() {
    }

    public QunarBase( String userIp, String operatorGuid, String operatorName) {
        this.userIp = userIp;
        this.operatorGuid = operatorGuid;
        this.operatorName = operatorName;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPmsId() {
        return pmsId;
    }

    public void setPmsId(String pmsId) {
        this.pmsId = pmsId;
    }
}
