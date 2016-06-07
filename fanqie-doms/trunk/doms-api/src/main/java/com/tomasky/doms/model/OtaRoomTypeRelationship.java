package com.tomasky.doms.model;

import com.tomasky.doms.enums.OtaCode;
import com.tomasky.doms.model.base.Domain;

import java.math.BigDecimal;

/**
 * DESC : 番茄也渠道客栈房型关联对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class OtaRoomTypeRelationship extends Domain {

    //房型id
    private Integer roomTypeId;
    //房型名称
    private String roomTypeName;
    //客栈id
    private Integer innId;
    //客栈名称
    private String innCode;
    //渠道id
    private String otaId;
    //渠道code
    private OtaCode otaCode;
    //渠道房型酒店code
    private String otaInnCode;
    //渠道房型code
    private String otaRoomTypeCode;
    //佣金比
    private BigDecimal commissionPrice;
    private boolean status;

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public String getInnCode() {
        return innCode;
    }

    public void setInnCode(String innCode) {
        this.innCode = innCode;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public OtaCode getOtaCode() {
        return otaCode;
    }

    public void setOtaCode(OtaCode otaCode) {
        this.otaCode = otaCode;
    }

    public String getOtaInnCode() {
        return otaInnCode;
    }

    public void setOtaInnCode(String otaInnCode) {
        this.otaInnCode = otaInnCode;
    }

    public String getOtaRoomTypeCode() {
        return otaRoomTypeCode;
    }

    public void setOtaRoomTypeCode(String otaRoomTypeCode) {
        this.otaRoomTypeCode = otaRoomTypeCode;
    }

    public BigDecimal getCommissionPrice() {
        return commissionPrice;
    }

    public void setCommissionPrice(BigDecimal commissionPrice) {
        this.commissionPrice = commissionPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
