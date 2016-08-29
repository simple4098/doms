package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.common.CommonApi;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/13
 * @version: v1.0.0
 */
public class QunarBaseBean {
    //参数校验码
    private String hmac;
    //接口版本号 所使用的 api 文档提供的版本号
    private String version = CommonApi.version;
    //PMS 代码
    private String pmsId = CommonApi.pmsId;

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
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
