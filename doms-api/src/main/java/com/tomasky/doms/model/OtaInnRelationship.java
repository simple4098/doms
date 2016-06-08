package com.tomasky.doms.model;


import com.tomasky.doms.enums.OtaCode;
import com.tomasky.doms.model.base.Domain;

/**
 * DESC : 番茄客栈与渠道房型关联对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */public class OtaInnRelationship extends Domain{

    //otaInn主键
    private String otaInnPk;
    //渠道id
    private String otaId;
    //true 开通成功； false 开通失败
    private Boolean status;
    //渠道酒店code
    private String otaInnCode;
    private String otaInnName;

    public String getOtaInnPk() {
        return otaInnPk;
    }

    public void setOtaInnPk(String otaInnPk) {
        this.otaInnPk = otaInnPk;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getOtaInnCode() {
        return otaInnCode;
    }

    public void setOtaInnCode(String otaInnCode) {
        this.otaInnCode = otaInnCode;
    }

    public String getOtaInnName() {
        return otaInnName;
    }

    public void setOtaInnName(String otaInnName) {
        this.otaInnName = otaInnName;
    }
}
