package com.tomasky.doms.dto;

/**
 * Create by jame
 * Date: 2016/6/13
 * Version: 1.0
 * Description: 阐述
 */
public class TomatoOmsOtaInfo {
    private static final long serialVersionUID = 1L;
    private Integer id;   /**/
    private String name;   /**/
    private String userCode;   /**/
    private String userPassword;   /**/
    private String authority;   /**/
    private Integer apiTypeId;   /**/
    private Integer otaId;   /**/
    private String otaType;
    private Integer pId;
    private Integer otaGroup;
    private String otaHttpUrl;
    private String pushAddr;
    private String pushConfig;
    //新增业务字段
    private String manager;
    private String proxyTypeConfig;
    private String commonProxyStrategy;

    public TomatoOmsOtaInfo() {
    }

    public TomatoOmsOtaInfo(Integer otaId) {
        this.otaId = otaId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getProxyTypeConfig() {
        return proxyTypeConfig;
    }

    public void setProxyTypeConfig(String proxyTypeConfig) {
        this.proxyTypeConfig = proxyTypeConfig;
    }

    public String getCommonProxyStrategy() {
        return commonProxyStrategy;
    }

    public void setCommonProxyStrategy(String commonProxyStrategy) {
        this.commonProxyStrategy = commonProxyStrategy;
    }

    public String getPushConfig() {
        return pushConfig;
    }

    public void setPushConfig(String pushConfig) {
        this.pushConfig = pushConfig;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPushAddr() {
        return pushAddr;
    }

    public void setPushAddr(String pushAddr) {
        this.pushAddr = pushAddr;
    }

    public String getOtaHttpUrl() {
        return otaHttpUrl;
    }

    public void setOtaHttpUrl(String otaHttpUrl) {
        this.otaHttpUrl = otaHttpUrl;
    }

    public Integer getOtaGroup() {
        return otaGroup;
    }

    public void setOtaGroup(Integer otaGroup) {
        this.otaGroup = otaGroup;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getOtaType() {
        return otaType;
    }

    public void setOtaType(String otaType) {
        this.otaType = otaType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getApiTypeId() {
        return apiTypeId;
    }

    public void setApiTypeId(Integer apiTypeId) {
        this.apiTypeId = apiTypeId;
    }

    public Integer getOtaId() {
        return otaId;
    }

    public void setOtaId(Integer otaId) {
        this.otaId = otaId;
    }
}
