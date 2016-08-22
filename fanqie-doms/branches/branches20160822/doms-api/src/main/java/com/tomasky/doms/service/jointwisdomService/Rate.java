package com.tomasky.doms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 * 价格
 */
@XmlRootElement(name = "Rate", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Rate {
    private String effectiveDate;//价格开始时间
    private String expireDate;//价格结束时间
    private Base base;//费用类型
    private String rateTimeUnit;
    private String unitMultiplier;


    @XmlAttribute(name = "RateTimeUnit")
    public String getRateTimeUnit() {
        return rateTimeUnit;
    }

    public void setRateTimeUnit(String rateTimeUnit) {
        this.rateTimeUnit = rateTimeUnit;
    }

    @XmlAttribute(name = "UnitMultiplier")
    public String getUnitMultiplier() {
        return unitMultiplier;
    }

    public void setUnitMultiplier(String unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }

    @XmlElement(name = "Base")
    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    @XmlAttribute(name = "EffectiveDate")
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @XmlAttribute(name = "ExpireDate")
    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
