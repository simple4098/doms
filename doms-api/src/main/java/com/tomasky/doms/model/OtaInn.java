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
    //客栈渠道accountId
    private String accountId;
    //渠道id
    private String otaId;
    //true 开通成功； false 开通失败
    private Boolean openStatus;
    //客栈code
    private String innCode;
    //渠道code
    private OtaCode otaCode;

    public OtaInn() {
    }


    public OtaInn(Integer innId, String innName, String accountId, String otaId, Boolean openStatus, OtaCode otaCode) {
        this.innId = innId;
        this.innName = innName;
        this.accountId = accountId;
        this.otaId = otaId;
        this.openStatus = openStatus;

        this.otaCode = otaCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Boolean getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Boolean openStatus) {
        this.openStatus = openStatus;
    }

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
