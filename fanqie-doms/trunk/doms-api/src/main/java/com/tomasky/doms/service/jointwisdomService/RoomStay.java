package com.tomasky.doms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/11.
 */
@XmlRootElement(name = "RoomStay", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RoomStay {
    //    private List<RoomType> roomTypes;//房型信息
    private List<RoomRate> roomRates;
    private List<GuestCount> guestCounts;
    private TimeSpan timeSpan;
    private BasicPropertyInfo basicPropertyInfo;
    private Guarantee guarantee;
    private Total total;
    private String resGuestRPHs;
    private List<SpecialRequest> specialRequests;
    private DepositPayments depositPayments;
    private String marketCode;
    private String sourceOfBusiness;
    private String promotionCode;


    @XmlAttribute(name = "PromotionCode")
    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    @XmlAttribute(name = "SourceOfBusiness")
    public String getSourceOfBusiness() {
        return sourceOfBusiness;
    }

    public void setSourceOfBusiness(String sourceOfBusiness) {
        this.sourceOfBusiness = sourceOfBusiness;
    }

    @XmlAttribute(name = "MarketCode")
    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    @XmlElement(name = "DepositPayments")
    public DepositPayments getDepositPayments() {
        return depositPayments;
    }

    public void setDepositPayments(DepositPayments depositPayments) {
        this.depositPayments = depositPayments;
    }

    @XmlElement(name = "SpecialRequest", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "SpecialRequests")
    public List<SpecialRequest> getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(List<SpecialRequest> specialRequests) {
        this.specialRequests = specialRequests;
    }

    @XmlElement(name = "ResGuestRPHs", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getResGuestRPHs() {
        return resGuestRPHs;
    }

    public void setResGuestRPHs(String resGuestRPHs) {
        this.resGuestRPHs = resGuestRPHs;
    }

    @XmlElement(name = "Total", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    @XmlElement(name = "Guarantee", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Guarantee getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    @XmlElement(name = "BasicPropertyInfo")
    public BasicPropertyInfo getBasicPropertyInfo() {
        return basicPropertyInfo;
    }

    public void setBasicPropertyInfo(BasicPropertyInfo basicPropertyInfo) {
        this.basicPropertyInfo = basicPropertyInfo;
    }

    @XmlElement(name = "TimeSpan")
    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }

    @XmlElement(name = "GuestCount")
    @XmlElementWrapper(name = "GuestCounts")
    public List<GuestCount> getGuestCounts() {
        return guestCounts;
    }

    public void setGuestCounts(List<GuestCount> guestCounts) {
        this.guestCounts = guestCounts;
    }

    @XmlElement(name = "RoomRate")
    @XmlElementWrapper(name = "RoomRates")
    public List<RoomRate> getRoomRates() {
        return roomRates;
    }

    public void setRoomRates(List<RoomRate> roomRates) {
        this.roomRates = roomRates;
    }


   /* @XmlElement(name = "RoomType")
    @XmlElementWrapper(name = "RoomTypes")
    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }*/
}
