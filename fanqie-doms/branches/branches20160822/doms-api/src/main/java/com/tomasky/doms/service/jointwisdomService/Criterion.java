package com.tomasky.doms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "Criterion", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Criterion {
    private HotelRef hotelRef;
    private StayDateRange stayDateRange;
    private RoomStayCandidates roomStayCandidates;
    private List<RatePlanCandidate> ratePlanCandidates;

    @XmlElement(name = "RatePlanCandidate", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "RatePlanCandidates")
    public List<RatePlanCandidate> getRatePlanCandidates() {
        return ratePlanCandidates;
    }

    public void setRatePlanCandidates(List<RatePlanCandidate> ratePlanCandidates) {
        this.ratePlanCandidates = ratePlanCandidates;
    }

    @XmlElement(name = "HotelRef", namespace = "http://www.opentravel.org/OTA/2003/05")
    public HotelRef getHotelRef() {
        return hotelRef;
    }

    public void setHotelRef(HotelRef hotelRef) {
        this.hotelRef = hotelRef;
    }

    @XmlElement(name = "StayDateRange", namespace = "http://www.opentravel.org/OTA/2003/05")
    public StayDateRange getStayDateRange() {
        return stayDateRange;
    }

    public void setStayDateRange(StayDateRange stayDateRange) {
        this.stayDateRange = stayDateRange;
    }

    @XmlElement(name = "RoomStayCandidates", namespace = "http://www.opentravel.org/OTA/2003/05")
    public RoomStayCandidates getRoomStayCandidates() {
        return roomStayCandidates;
    }

    public void setRoomStayCandidates(RoomStayCandidates roomStayCandidates) {
        this.roomStayCandidates = roomStayCandidates;
    }
}
