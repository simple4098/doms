package com.tomasky.doms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/8/3.
 */
@XmlRootElement(name = "RatePlanCandidate", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RatePlanCandidate {
    private String ratePlanCode;

    @XmlElement(name = "RatePlanCode")
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }
}
