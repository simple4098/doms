package com.tomasky.doms.dto.oms;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class ChannelInfoData {

    private List<ChannelInfo> channelInfo;
    private Integer innId;
    private String innName;
    private String accountId;
    private String operatorGuid;
    private String operatorName;

    public ChannelInfoData(String accountId, String innName, String operatorGuid, String operatorName) {
        this.accountId = accountId;
        this.innName = innName;
        this.operatorGuid = operatorGuid;
        this.operatorName = operatorName;
    }

    public ChannelInfoData() {
    }

    public List<ChannelInfo> getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(List<ChannelInfo> channelInfo) {
        this.channelInfo = channelInfo;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
