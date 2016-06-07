package com.tomasky.doms.model;


import com.tomasky.doms.enums.OtaCode;
import com.tomasky.doms.model.base.Domain;

/**
 * DESC : 番茄客栈与渠道房型关联对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */public class OtaInn extends Domain{
    //客栈id
    private Integer innId;
    //客栈名称
    private String innName;
    //渠道id
    private String otaId;
    //true 匹配成功
    private boolean status;
    //true 开通成功； false 开通失败
    private boolean open;
    //渠道酒店code
    private String otaInnCode;
    //客栈code
    private String innCode;
    //渠道code
    private OtaCode otaCode;

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOtaInnCode() {
        return otaInnCode;
    }

    public void setOtaInnCode(String otaInnCode) {
        this.otaInnCode = otaInnCode;
    }

    public String getInnCode() {
        return innCode;
    }

    public void setInnCode(String innCode) {
        this.innCode = innCode;
    }

    public OtaCode getOtaCode() {
        return otaCode;
    }

    public void setOtaCode(OtaCode otaCode) {
        this.otaCode = otaCode;
    }
}
