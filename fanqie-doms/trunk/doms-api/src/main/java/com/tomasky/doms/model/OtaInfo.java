package com.tomasky.doms.model;


import com.tomasky.doms.enums.OtaCode;
import com.tomasky.doms.model.base.Domain;

/**
 * DESC : 第三方系统中开通的渠道
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInfo extends Domain {

    //渠道名称
    private String otaInfo;
    private String companyId;
    private OtaCode otaCode;
    //排序
    private int sort;
    private String userAccount;
    private String userPassword;
    private String otaId;

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public String getOtaInfo() {
        return otaInfo;
    }

    public void setOtaInfo(String otaInfo) {
        this.otaInfo = otaInfo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public OtaCode getOtaCode() {
        return otaCode;
    }

    public void setOtaCode(OtaCode otaCode) {
        this.otaCode = otaCode;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
