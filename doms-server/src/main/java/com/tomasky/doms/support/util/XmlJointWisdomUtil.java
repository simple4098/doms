package com.tomasky.doms.support.util;


import com.fanqie.util.DateUtil;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.enums.*;
import com.tomasky.doms.model.*;
import com.tomasky.doms.service.jointwisdomService.RatePlan;
import com.tomasky.doms.service.jointwisdomService.RatePlanDescription;
import com.tomasky.doms.service.jointwisdomService.Text;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/11.
 * 众荟xml工具类
 */
public class XmlJointWisdomUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlJointWisdomUtil.class);
    private static String requestType;

    /**
     * 解析xml信息
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Element dealXmlStr(String xmlStr) throws Exception {
        Document document = DocumentHelper.parseText(xmlStr);
        //得到跟节点信息
        Element element = document.getRootElement();
//        Element rootElement = element.element("Body");
        return element;
    }

    /**
     * 得到xml信息的根节点名称
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static String getRootElementString(String xmlStr) throws Exception {
        Document document = DocumentHelper.parseText(xmlStr);
        //得到跟节点信息
        Element element = document.getRootElement();
        return element.getName();
    }

    /**
     * 得到众荟的试订单请求对象
     *
     * @param xml
     * @return
     */
    public static Order getJointWisdomAvailOrder(String xml) throws Exception {
        Order order = new Order();
        //解析xml
        Element element = dealXmlStr(xml);
//        Element element = dealXmlStr.element("OTA_HotelAvailRQ");//根节点
        Element param = element.element("AvailRequestSegments").element("AvailRequestSegment").element("HotelSearchCriteria").element("Criterion");
        //酒店的code
        order.setInnCode(param.element("HotelRef").attributeValue("HotelCode"));
        order.setLiveTime(DateUtil.parse(param.element("StayDateRange").attributeValue("Start"), "yyyy-MM-dd"));
        order.setLeaveTime(DateUtil.parse(param.element("StayDateRange").attributeValue("End"), "yyyy-MM-dd"));
        order.setRoomTypeCode(param.element("RoomStayCandidates").element("RoomStayCandidate").attributeValue("RoomTypeCode"));
        order.setRoomTypeId(order.getRoomTypeCode());
        order.setHomeAmount(Integer.parseInt(param.element("RoomStayCandidates").element("RoomStayCandidate").attributeValue("Quantity")));
        order.setChannelSource(ChannelSource.CTRIP_CONN);
        if (StringUtils.isNotEmpty(order.getInnCode()) && order.getInnCode().contains("_")) {
            order.setInnId(Integer.parseInt(order.getInnCode().split("_")[1]));
        } else {
            throw new RuntimeException("解析hotleCode出错，hotelCode=>" + order.getInnCode());
        }
        order.setOtaId(DomsConstants.XCOtaId);
        return order;
    }

    public static void main(String[] args) {
        String xml = "<SpecialRequests>\n" +
                "\t\t\t\t\t\t\t\t<SpecialRequest>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>Please provide extra robe.</Text>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>must be nonsmoking room</Text>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>try to arrange nonsmoking room</Text>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>extra breakfast request</Text>\n" +
                "\t\t\t\t\t\t\t\t</SpecialRequest>\n" +
                "\t\t\t\t\t\t\t</SpecialRequests>";
        try {
            Element element = dealXmlStr(xml);
            List<Element> elements = element.element("SpecialRequest").elements("Text");
            if (ArrayUtils.isNotEmpty(elements.toArray())) {
                for (Element e : elements) {
                    System.out.println(e.elementText("Text"));
                }
            }
//            Order jointWisdomAvailOrder = getJointWisdomAvailOrder(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到订单请求的类型
     *
     * @param xml
     * @return
     */
    public static OrderRequestType getOrderRequestType(String xml) throws Exception {
        Element element = dealXmlStr(xml);
        String status = element.attributeValue("ResStatus");
        return Enum.valueOf(OrderRequestType.class, status);
    }

    /**
     * 得到众荟的订单对象
     *
     * @param xml
     * @return
     */
    public static Order getAddOrder(String xml) throws Exception {
        Order order = new Order();
        order.setId(order.getUuid());
        order.setChannelSource(ChannelSource.CTRIP_CONN);
        Element element = dealXmlStr(xml);
        Element param = element.element("HotelReservations").element("HotelReservation");
        //房型信息
        Element roomTypeParam = param.element("RoomStays").element("RoomStay");
        Element roomRate = roomTypeParam.element("RoomRates").element("RoomRate");
        order.setRoomTypeCode(roomRate.attributeValue("RoomTypeCode"));
        order.setOTARoomTypeId(order.getRoomTypeCode());
        order.setRoomTypeId(order.getRoomTypeCode());
        order.setHomeAmount(Integer.parseInt(roomRate.attributeValue("NumberOfUnits")));
        //真正的渠道来源:携程
        order.setParentCode(element.element("POS").element("Source").element("RequestorID").attributeValue("ID"));
        //价格code
        order.setOTARateCode(roomRate.attributeValue("RatePlanCode"));
        String paymentTypeString = roomRate.attributeValue("RatePlanCategory");
        if (StringUtils.isNotEmpty(paymentTypeString) && paymentTypeString.equals("501")) {
            //预付
            order.setPaymentType(PaymentType.PP);
        } else {
            //现付
            order.setPaymentType(PaymentType.FG);
            //现付才会传担保的数据
            String guaranteeType = roomTypeParam.element("Guarantee").attributeValue("GuaranteeType");
            if("None".equals(guaranteeType)){
                order.setWeatherGuarantee(false);
            }
            if("GuaranteeRequired".equals(guaranteeType)) {
                order.setWeatherGuarantee(true);
            }
        }
        //得到每日入住信息
        order.setDailyInfoses(getOrderDailyInfos(roomRate.element("Rates").elements("Rate"), order));
        order.setPerson(Integer.valueOf(roomTypeParam.element("GuestCounts").element("GuestCount").attributeValue("Count")));
        order.setLiveTime(DateUtil.parse(roomTypeParam.element("TimeSpan").attributeValue("Start"), "yyyy-MM-dd"));
        order.setLeaveTime(DateUtil.parse(roomTypeParam.element("TimeSpan").attributeValue("End"), "yyyy-MM-dd"));
        order.setTotalPrice(BigDecimal.valueOf(Double.valueOf(roomTypeParam.element("Total").attributeValue("AmountAfterTax"))));
        order.setCurrency(CurrencyCode.CNY);
        order.setInnCode(roomTypeParam.element("BasicPropertyInfo").attributeValue("HotelCode"));

        //// TODO: 2016/9/1  注释此段代码  担保非担保问题
        /*if (StringUtils.isNotEmpty(guaranteeType)) {
            order.setWeatherGuarantee(true);
        } else {
            order.setWeatherGuarantee(false);
        }*/
        order.setOTAHotelId(order.getInnCode());
        if (StringUtils.isNotEmpty(order.getInnCode()) && order.getInnCode().contains("_")) {
            order.setInnId(Integer.parseInt(order.getInnCode().split("_")[1]));
        } else {
            throw new RuntimeException("解析hotleCode出错，hotelCode=>" + order.getInnCode());
        }
        order.setComment(getComment(roomTypeParam.element("SpecialRequests").element("SpecialRequest").elements("Text")));
        //入住人信息
        Element guestParam = param.element("ResGuests").element("ResGuest");
        //最晚到店时间
        order.setLastestArriveTime(DateUtil.parse(DomsUtil.getDateString(guestParam.element("ArrivalTransport").element("TransportInfo").attributeValue("Time")), "yyyy-MM-dd HH:mm:ss"));
        //入住人信息
        order.setOrderGuestses(getOrderGuest(guestParam.element("Profiles").element("ProfileInfo").element("Profile").element("Customer").elements("PersonName"), order));
        //联系人信息
        order.setGuestName(getGuestInfo(order.getOrderGuestses()));
        //渠道订单号
        order.setChannelOrderCode(getChannelOrderCode(param.element("ResGlobalInfo").element("HotelReservationIDs").elements("HotelReservationID")));
        order.setOmsOrderCode(getOmsOrderCode(param.element("ResGlobalInfo").element("HotelReservationIDs").elements("HotelReservationID")));
        //设置预付金额
        order.setPrepayPrice(order.getTotalPrice());
        //设置已付金额
        order.setPayment(order.getTotalPrice());
        return order;
    }

    /**
     * 得到pms订单号
     *
     * @param elements
     * @return
     */
    private static String getOmsOrderCode(List<Element> elements) {
        if (CollectionUtils.isNotEmpty(elements)) {
            for (Element element : elements) {
                if (element.attributeValue("ResID_Type").equals("10")) {
                    return element.attributeValue("ResID_Value");
                }
            }
        }
        return null;
    }

    /**
     * 得到渠道订单号，携程
     *
     * @param elements
     * @return
     */
    private static String getChannelOrderCode(List<Element> elements) {
        if (CollectionUtils.isNotEmpty(elements)) {
            for (Element element : elements) {
                if (element.attributeValue("ResID_Type").equals("24")) {
                    return element.attributeValue("ResID_Value");
                }
            }
        } else {
            throw new RuntimeException("解析众荟订单信息出错，获取渠道订单号");
        }
        return null;
    }

    /**
     * 得到订单联系人信息（众荟用入住人，以逗号分隔多个入住人）
     *
     * @param orderGuestses
     * @return
     */
    private static String getGuestInfo(List<OrderGuests> orderGuestses) {
        String guestName = "";
        if (ArrayUtils.isNotEmpty(orderGuestses.toArray())) {
            for (OrderGuests guests : orderGuestses) {
                guestName = guestName + guests.getName() + ",";
            }
        }
        if (guestName.length() != 0) {
            guestName = guestName.substring(0, guestName.length() - 1);
        }
        return guestName;
    }

    /**
     * 入住人信息
     *
     * @param elements
     * @param order
     * @return
     */
    private static List<OrderGuests> getOrderGuest(List<Element> elements, Order order) {
        List<OrderGuests> result = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                OrderGuests orderGuests = new OrderGuests();
                orderGuests.setOrderId(order.getId());
                orderGuests.setName(e.elementText("NamePrefix") + "_" + e.elementText("Surname") + e.elementText("GivenName"));
                result.add(orderGuests);
            }
        }
        return result;
    }

    /**
     * 得到订单的备注信息
     *
     * @param elements
     * @return
     */
    private static String getComment(List<Element> elements) {
        String result = "";
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                result = result + element.getText();
            }
        }
        return result;
    }

    /**
     * 得到众荟每日入住信息
     *
     * @param elements
     * @param order
     * @return
     */
    private static List<DailyInfos> getOrderDailyInfos(List<Element> elements, Order order) {
        List<DailyInfos> result = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                DailyInfos d = new DailyInfos();
                d.setOrderId(order.getId());
                d.setDay(DateUtil.parse(element.attributeValue("EffectiveDate"), "yyyy-MM-dd"));
                d.setPrice(BigDecimal.valueOf(Double.valueOf(element.element("Base").attributeValue("AmountAfterTax"))));
                d.setBasicPrice(d.getPrice());
                result.add(d);
            }
        }
        return result;
    }

    /**
     * 得到众荟取消订单信息
     *
     * @param xml
     * @return
     */
    public static Order getCancelOrder(String xml) throws Exception {
        Order order = new Order();
        Element element = dealXmlStr(xml);
        List<Element> elements = element.element("HotelReservations").element("HotelReservation").element("ResGlobalInfo").element("HotelReservationIDs").elements("HotelReservationID");
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                String type = e.attributeValue("ResID_Type");
                if (ResponseType.R10.getText().equals(type)) {
                    order.setId(e.attributeValue("ResID_Value"));
                } else {
                    order.setChannelOrderCode(e.attributeValue("ResID_Value"));
                }
            }
        }
        order.setChannelSource(ChannelSource.ZH);
        return order;
    }

    /**
     * 将ota房型信息转换为toms每日价格信息
     *
     * @param roomTypeInfo
     * @return
     */
    public static List<DailyInfos> toDailyInfos(RoomTypeInfo roomTypeInfo) {
        List<DailyInfos> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roomTypeInfo.getRoomDetail())) {
            for (RoomDetail roomDetail : roomTypeInfo.getRoomDetail()) {
                DailyInfos dailyInfos = new DailyInfos();
                dailyInfos.setDay(DateUtil.parse(roomDetail.getRoomDate(), "yyyy-MM-dd"));
                if (roomTypeInfo.getRatePlanCode().equals("P_XCB")) {
                    dailyInfos.setPrice(BigDecimal.valueOf(roomDetail.getRoomPrice()).multiply(BigDecimal.ONE.subtract(roomTypeInfo.getCommissionPercent().divide(BigDecimal.valueOf(100)))));
                } else {
                    dailyInfos.setPrice(BigDecimal.valueOf(roomDetail.getRoomPrice()));
                }
                dailyInfos.setRoomNum(roomDetail.getRoomNum());
                result.add(dailyInfos);
            }
        }
        return result;
    }

    public static List<RatePlan> getRatePlanList(List<RoomDetail> roomDetails, RoomTypeInfo roomTypeInfo) {
        List<RatePlan> ratePlanList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roomDetails)) {
            for (RoomDetail roomDetail : roomDetails) {
                RatePlan ratePlan = new RatePlan();
                if (!roomTypeInfo.getRatePlanCode().equals("P_XCB")) {
                    ratePlan.setRatePlanCategory("16");
                } else {
                    ratePlan.setRatePlanCategory(ratePlan.getRatePlanCategory());
                }
                ratePlan.setRatePlanCode(roomTypeInfo.getRatePlanCode());
                RatePlanDescription ratePlanDescription = new RatePlanDescription();
                Text textRatePlan = new Text();
                textRatePlan.setValue(roomTypeInfo.getRatePlanCodeName());
                ratePlanDescription.setText(textRatePlan);
                ratePlanDescription.setName(roomTypeInfo.getRatePlanCodeName());
                ratePlan.setRatePlanDescription(ratePlanDescription);
                ratePlanList.add(ratePlan);
            }
        }
        return ratePlanList;
    }
}
