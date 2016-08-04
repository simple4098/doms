package com.tomasky.doms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "GuaranteesAccepted", namespace = "http://www.opentravel.org/OTA/2003/05")
public class GuaranteesAccepted {
    private GuaranteeAccepted guaranteeAccepted;

    @XmlElement(name = "GuaranteeAccepted", namespace = "http://www.opentravel.org/OTA/2003/05")
    public GuaranteeAccepted getGuaranteeAccepted() {
        return guaranteeAccepted;
    }

    public void setGuaranteeAccepted(GuaranteeAccepted guaranteeAccepted) {
        this.guaranteeAccepted = guaranteeAccepted;
    }
}
