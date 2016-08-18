package com.tomasky.doms.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanqie.core.dto.CancelOrderParamDto;
import com.fanqie.core.dto.OrderParamDto;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.enums.OrderResponseType;
import com.tomasky.doms.enums.Version;
import com.tomasky.doms.model.*;
import com.tomasky.doms.service.IJointWisdomOrderService;
import com.tomasky.doms.service.jointwisdomService.*;
import com.tomasky.doms.support.helper.InnRoomHelper;
import com.tomasky.doms.support.util.HttpClientUtil;
import com.tomasky.doms.support.util.JsonModel;
import com.tomasky.doms.support.util.ResourceBundleUtil;
import com.tomasky.doms.support.util.XmlJointWisdomUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wangdayin on 2016/1/11.
 */
@Service
public class JointWisdomOrderService implements IJointWisdomOrderService {
    private static Logger logger = LoggerFactory.getLogger(JointWisdomOrderService.class);


    @Override
    public Map<String, Object> dealAvailCheckOrder(String xml) throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            //解析xml得到order的查询对象
            Order availOrder = XmlJointWisdomUtil.getJointWisdomAvailOrder(xml);
            //检查试订单日期
            if (availOrder.getLiveTime().getTime() > availOrder.getLeaveTime().getTime() || availOrder.getLiveTime().getTime() < DateUtil.addDay(new Date(), -1).getTime() || availOrder.getLeaveTime().getTime() < DateUtil.addDay(new Date(), -1).getTime()) {
                JointWisdomAvailCheckOrderSuccessResponse errorResult = new JointWisdomAvailCheckOrderSuccessResponse();
                JointWisdomAvailCheckOrderErrorResponse basicError = errorResult.getBasicError("请检查入住或离店时间");
                map.put("data", basicError);
                map.put("status", true);
                return map;
            }
            // 获取当前客栈的accountId 根据酒店信息查询开通渠道accountId
            Integer accountId = null;
            Map<String, Object> accountIdParam = new HashMap<>();
            accountIdParam.put("innId", availOrder.getInnId());
            accountIdParam.put("innOtaId", DomsConstants.XCOtaId.toString());
            logger.info("众荟试订单，获取accountId，传入参数=>" + accountIdParam.toString());
            String accountResponse = HttpClientUtil.httpKvPost(CommonApi.getOmsAccountIdUrl(), JSON.toJSON(accountIdParam));
            logger.info("众荟试订单，获取accountId，返回值=>" + accountResponse);
            if (StringUtils.isNotEmpty(accountResponse)) {
                JSONObject jsonObject = JSONObject.parseObject(accountResponse);
                if (null != jsonObject.getInteger("accountId")) {
                    accountId = jsonObject.getInteger("accountId");
                } else {
                    JointWisdomAvailCheckOrderSuccessResponse errorResult = new JointWisdomAvailCheckOrderSuccessResponse();
                    JointWisdomAvailCheckOrderErrorResponse basicError = errorResult.getBasicError("获取开通渠道accountId失败");
                    logger.info("对接众荟试订单出错,返回值=>" + basicError.toString());
                    map.put("status", false);
                    map.put("data", basicError);
                    return map;
                }
            }
            availOrder.setAccountId(accountId);
            //获取房态
            String room_type = DcUtil.omsRoomTYpeUrl(DomsConstants.XCOtaId, ResourceBundleUtil.getString("ctrip_oms_user_account"), ResourceBundleUtil.getString("ctrip_oms_password"), String.valueOf(accountId), CommonApi.getOmsRoomType());
            String roomStatus = DcUtil.omsRoomTYpeUrl(DomsConstants.XCOtaId, ResourceBundleUtil.getString("ctrip_oms_user_account"), ResourceBundleUtil.getString("ctrip_oms_password"), String.valueOf(accountId), CommonApi.getOmsRoomStatus());
            logger.info("room_type url:"+room_type);
            logger.info("roomStatus url:"+roomStatus);
            List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo(room_type);
            List<RoomStatusDetail> statusDetails = InnRoomHelper.getRoomStatus(roomStatus);
            InnRoomHelper.updateRoomTypeInfo(list, statusDetails);
            //刷选数据
            if (ArrayUtils.isNotEmpty(list.toArray())) {
                for (RoomTypeInfo roomTypeInfo : list) {
                    List<RoomDetail> roomDetailList = new ArrayList<>();
                    if (ArrayUtils.isNotEmpty(roomTypeInfo.getRoomDetail().toArray())) {
                        for (RoomDetail roomDetail : roomTypeInfo.getRoomDetail()) {
                            Date parse = DateUtil.parse(roomDetail.getRoomDate(), "yyyy-MM-dd");
                            if (parse.getTime() >= availOrder.getLiveTime().getTime() && parse.getTime() < availOrder.getLeaveTime().getTime()) {
                                roomDetailList.add(roomDetail);
                            }
                        }
                    }
                    roomTypeInfo.setRoomDetail(roomDetailList);
                }
            }
            //判断是否有房型
            if (null != availOrder.getRoomTypeCode()) {
                RoomTypeInfo typeInfo = new RoomTypeInfo();
                //刷选数据
                for (RoomTypeInfo roomTypeInfo : list) {
                    if (roomTypeInfo.getRoomTypeId().toString().equals(availOrder.getRoomTypeCode())) {
                        typeInfo = roomTypeInfo;
                        break;
                    }
                }
                //设置roomTypeId
                availOrder.setRoomTypeId(availOrder.getRoomTypeId());
                //酒店id
                availOrder.setInnId(availOrder.getInnId());
                //处理一下离店日期，不需要包含最后一天
                availOrder.setLeaveTime(DateUtil.addDay(availOrder.getLeaveTime(), -1));
                //转换oms房型信息为toms的每日入住信息
                List<DailyInfos> dailyInfos = XmlJointWisdomUtil.toDailyInfos(typeInfo);
                //组装返回参数
                //1.组装房型
                RoomType roomType = new RoomType();
                roomType.setRoomTypeCode(availOrder.getRoomTypeCode());
                boolean canBook = false;
                //判断是否可预定
                for (DailyInfos dailyInfos1 : dailyInfos) {
                    if (dailyInfos1.getRoomNum() < availOrder.getHomeAmount()) {
                        canBook = false;
                        break;
                    } else {
                        canBook = true;
                    }
                }
                if (canBook) {
                    roomType.setNumberOfUnits("true");
                } else {
                    roomType.setNumberOfUnits("false");
                    map.put("status", false);
                    map.put("data", new JointWisdomAvailCheckOrderSuccessResponse().getBasicError("房型剩余放量不满足预定，请重试"));
                    return map;
                }
                RoomDescription roomDescription = new RoomDescription();
                roomDescription.setName(availOrder.getRoomTypeName());
                Text text = new Text();
                text.setValue(availOrder.getRoomTypeName());
                text.setLanguage("en-US");
                roomDescription.setText(text);
                roomType.setDescription(roomDescription);
                //2.组装价格计划
                List<RatePlan> ratePlanList = XmlJointWisdomUtil.getRatePlanList(typeInfo.getRoomDetail(), typeInfo);
                //3.组装房型与价格计划的对应关系
                RoomRate roomRate = new RoomRate();
                roomRate.setRatePlanCode(typeInfo.getRatePlanCode());
                roomRate.setRoomTypeCode(availOrder.getRoomTypeCode());
                List<Rate> rateList = new ArrayList<>();
                BigDecimal totalPrice = BigDecimal.ZERO;
                if (ArrayUtils.isNotEmpty(dailyInfos.toArray())) {
                    for (DailyInfos dailyInfos1 : dailyInfos) {
                        Rate rate = new Rate();
                        rate.setEffectiveDate(DateUtil.format(dailyInfos1.getDay(), "yyyy-MM-dd"));
                        rate.setExpireDate(DateUtil.format(DateUtil.addDay(dailyInfos1.getDay(), 1), "yyyy-MM-dd"));
                        Base base = new Base();
                        base.setAmountAfterTax(String.valueOf(dailyInfos1.getPrice()));
                        base.setAmountBeforeTax(base.getAmountAfterTax());
                        base.setCurrencyCode(base.getCurrencyCode());
                        Taxes taxes = new Taxes();
                        taxes.setCurrencyCode(base.getCurrencyCode());
                        base.setTaxes(taxes);
                        rate.setBase(base);
                        rateList.add(rate);
                        totalPrice = totalPrice.add(BigDecimal.valueOf(Double.valueOf(base.getAmountAfterTax())));
                    }
                }
                roomRate.setRates(rateList);
                Total total = new Total();
                total.setCurrencyCode("RMB");
                total.setAmountBeforeTax(String.valueOf(totalPrice));
                total.setAmountAfterTax(String.valueOf(totalPrice));
                roomRate.setTotal(total);
                List<RoomRate> roomRateList = new ArrayList<>();
                roomRateList.add(roomRate);
                JointWisdomAvailCheckOrderSuccessResponse responseResult = new JointWisdomAvailCheckOrderSuccessResponse();
                responseResult.setEchoToken(responseResult.getEchoToken());
                responseResult.setTimeStamp(responseResult.getTimeStamp());
                responseResult.setSuccess("success");
                responseResult.setVersion(responseResult.getVersion());
//                    responseResult.setXmlns(responseResult.getXmlns());
                RoomStay roomStay = new RoomStay();
                //房型与价格对应关系
                roomStay.setRoomRates(roomRateList);

                roomStay.setRatePlans(ratePlanList);
                //房型
                List<RoomType> roomTypeList = new ArrayList<>();
                roomTypeList.add(roomType);
                roomStay.setRoomTypes(roomTypeList);
                List<GuestCount> guestCountList = new ArrayList<>();
                GuestCount guestCount = new GuestCount();
                guestCount.setCount(String.valueOf(availOrder.getHomeAmount()));
                guestCount.setAgeQualifyingCode("10");
                guestCountList.add(guestCount);
                roomStay.setGuestCounts(guestCountList);
                BasicPropertyInfo basicPropertyInfo = new BasicPropertyInfo();
                basicPropertyInfo.setHotelName(typeInfo.getInnName());
                basicPropertyInfo.setHotelCode(availOrder.getInnCode());
                roomStay.setBasicPropertyInfo(basicPropertyInfo);

                TimeSpan timeSpan = new TimeSpan();
                timeSpan.setStart(DateUtil.format(availOrder.getLiveTime(), "yyyy-MM-dd"));
                timeSpan.setEnd(DateUtil.format(availOrder.getLeaveTime(), "yyyy-MM-dd"));
                roomStay.setTimeSpan(timeSpan);
                List<RoomStay> roomStayList = new ArrayList<>();
                roomStayList.add(roomStay);
                responseResult.setRoomStays(roomStayList);
                map.put("status", true);
                map.put("data", responseResult);
                return map;
            } else {
                //组装返回参数
                RoomStay roomStay = new RoomStay();
                //房型与价格计划对应关系
                List<RoomRate> roomRateList = new ArrayList<>();
                //房型list
                List<RoomType> roomTypeList = new ArrayList<>();
                //价格计划
                List<RatePlan> ratePlanList = new ArrayList<>();
                if (ArrayUtils.isNotEmpty(list.toArray())) {
                    outer:
                    for (RoomTypeInfo roomTypeInfo : list) {
                        //查询房型信息toms
                        availOrder.setRoomTypeId(String.valueOf(roomTypeInfo.getRoomTypeId()));
                        //房型
                        RoomType roomType = new RoomType();
                        roomType.setRoomTypeCode(String.valueOf(roomTypeInfo.getRoomTypeId()));
                        RoomDescription roomDescription = new RoomDescription();
                        roomDescription.setName(roomTypeInfo.getRoomTypeName());
                        Text text = new Text();
                        text.setValue(roomTypeInfo.getRoomTypeName());
                        text.setLanguage("en-US");
                        roomDescription.setText(text);
                        roomType.setDescription(roomDescription);
                        boolean isCanbook = true;
                        //价格计划与房型对应
                        RoomRate roomRate = new RoomRate();
                        roomRate.setRoomTypeCode(String.valueOf(roomTypeInfo.getRoomTypeId()));
                        roomRate.setRatePlanCode(roomTypeInfo.getRatePlanCode());
                        List<Rate> rateList = new ArrayList<>();
                        BigDecimal totalPrice = BigDecimal.ZERO;
                        if (ArrayUtils.isNotEmpty(roomTypeInfo.getRoomDetail().toArray())) {
                            for (RoomDetail detail : roomTypeInfo.getRoomDetail()) {
                                //是否可预定
                                if (isCanbook) {
                                    if (detail.getRoomNum() >= availOrder.getHomeAmount()) {
                                        isCanbook = true;
                                    } else {
                                        isCanbook = false;
                                    }
                                }
                                //得到试订单的日期，每天的日期
                                List<Date> dateList = DateUtil.getDateEntrysByDifferenceDate(availOrder.getLiveTime(), availOrder.getLeaveTime());

                                Rate rate = new Rate();
                                rate.setEffectiveDate(DateUtil.format(DateUtil.parseDate(detail.getRoomDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
                                rate.setExpireDate(DateUtil.format(DateUtil.addDay(DateUtil.parseDate(detail.getRoomDate(), "yyyy-MM-dd"), 1), "yyyy-MM-dd"));
                                Base base = new Base();
                                if (!roomTypeInfo.getRatePlanCode().equals("P_XCB")) {
                                    base.setAmountAfterTax(String.valueOf(detail.getRoomPrice()));
                                } else {
                                    base.setAmountAfterTax(String.valueOf(BigDecimal.valueOf(detail.getRoomPrice()).multiply(BigDecimal.ONE.subtract(roomTypeInfo.getCommissionPercent().divide(BigDecimal.valueOf(100))))));
                                }
                                base.setCurrencyCode(base.getCurrencyCode());
                                base.setAmountBeforeTax(base.getAmountAfterTax());
                                base.setCurrencyCode(base.getCurrencyCode());
                                Taxes taxes = new Taxes();
                                taxes.setCurrencyCode(base.getCurrencyCode());
                                base.setTaxes(taxes);
                                rate.setBase(base);
                                rateList.add(rate);
                                totalPrice = totalPrice.add(BigDecimal.valueOf(Double.valueOf(base.getAmountAfterTax())));

                            }
                        } else {
                            map.put("status", false);
                            map.put("data", new JointWisdomAvailCheckOrderSuccessResponse().getBasicError("客栈中不存在满足预定的房型，请重试"));
                            return map;
                        }
                        roomRate.setRates(rateList);
                        Total total = new Total();
                        total.setAmountAfterTax(String.valueOf(totalPrice));
                        total.setAmountBeforeTax(total.getAmountAfterTax());
                        total.setCurrencyCode("RMB");
                        if (isCanbook) {
                            roomRate.setTotal(total);
                            //加载房型
                            roomType.setNumberOfUnits(isCanbook ? "true" : "false");
                            roomTypeList.add(roomType);
                        }
                        //价格计划
                        RatePlan ratePlan = new RatePlan();
                        if (roomTypeInfo.getRatePlanCode().equals("P_XCB")) {
                            ratePlan.setRatePlanCategory("501");
                        } else {
                            ratePlan.setRatePlanCategory("16");
                        }
                        ratePlan.setRatePlanCode(roomTypeInfo.getRatePlanCode());
                        RatePlanDescription ratePlanDescription = new RatePlanDescription();
                        ratePlanDescription.setName(roomTypeInfo.getRatePlanCodeName());
                        Text rateText = new Text();
                        rateText.setLanguage("en-US");
                        rateText.setValue(roomTypeInfo.getRatePlanCodeName());
                        ratePlanDescription.setText(rateText);
                        ratePlan.setRatePlanDescription(ratePlanDescription);
                        boolean weatherAdd = true;
                        if (CollectionUtils.isNotEmpty(rateList)) {
                            for (RatePlan ratePlan1 : ratePlanList) {
                                if (ratePlan1.getRatePlanCode().equals(ratePlan.getRatePlanCode())) {
                                    weatherAdd = false;
                                    break;
                                }
                            }
                        } else {
                            ratePlanList.add(ratePlan);
                        }
                        if (weatherAdd) {
                            ratePlanList.add(ratePlan);
                        }
                        if (isCanbook) {
                            roomRateList.add(roomRate);
                        }
                    }

                }
                roomStay.setRoomTypes(roomTypeList);
                if (ArrayUtils.isEmpty(roomTypeList.toArray())) {
                    map.put("status", false);
                    map.put("data", new JointWisdomAvailCheckOrderSuccessResponse().getBasicError("房型剩余放量不足，请重试"));
                    return map;
                }
                roomStay.setRoomRates(roomRateList);
                roomStay.setRatePlans(ratePlanList);
                List<GuestCount> guestCountList = new ArrayList<>();
                GuestCount guestCount = new GuestCount();
                guestCount.setCount(String.valueOf(availOrder.getHomeAmount()));
                guestCount.setAgeQualifyingCode("10");
                guestCountList.add(guestCount);
                roomStay.setGuestCounts(guestCountList);
                JointWisdomAvailCheckOrderSuccessResponse responseResult = new JointWisdomAvailCheckOrderSuccessResponse();
                responseResult.setEchoToken(responseResult.getEchoToken());
                responseResult.setTimeStamp(responseResult.getTimeStamp());
                responseResult.setSuccess("success");
                responseResult.setVersion(responseResult.getVersion());
//                responseResult.setXmlns(responseResult.getXmlns());

                BasicPropertyInfo basicPropertyInfo = new BasicPropertyInfo();
                basicPropertyInfo.setHotelName(list.get(0).getInnName());
                basicPropertyInfo.setHotelCode(availOrder.getInnCode());
                roomStay.setBasicPropertyInfo(basicPropertyInfo);

                TimeSpan timeSpan = new TimeSpan();
                timeSpan.setStart(DateUtil.format(availOrder.getLiveTime(), "yyyy-MM-dd"));
                timeSpan.setEnd(DateUtil.format(availOrder.getLeaveTime(), "yyyy-MM-dd"));
                roomStay.setTimeSpan(timeSpan);
                List<RoomStay> roomStayList = new ArrayList<>();
                roomStayList.add(roomStay);
                responseResult.setRoomStays(roomStayList);
                map.put("status", true);
                map.put("data", responseResult);
                return map;
            }
        } catch (Exception e) {
            JointWisdomAvailCheckOrderSuccessResponse errorResult = new JointWisdomAvailCheckOrderSuccessResponse();
            JointWisdomAvailCheckOrderErrorResponse basicError = errorResult.getBasicError("处理众荟试订单异常");
            logger.info("对接众荟试订单出错,返回值=>" + basicError.toString(), e);
            map.put("status", false);
            map.put("data", basicError);
            return map;
        }
    }


    @Override
    public Map<String, Object> dealAddOrder(String xml) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //解析xml得到订单对象
        Order order = XmlJointWisdomUtil.getAddOrder(xml);
        if (order.getLiveTime().getTime() > order.getLeaveTime().getTime() || order.getLiveTime().getTime() < DateUtil.addDay(new Date(), -1).getTime() || order.getLeaveTime().getTime() < DateUtil.addDay(new Date(), -1).getTime()) {
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("预定失败，请检查入住或离店时间", Version.v1003.getText(), OrderResponseType.Committed.name()));
            return map;
        }
        logger.info("众荟下单单号为：" + order.getChannelOrderCode());
        // 根据下单酒店查询omsaccountId
        Integer accountId = null;
        String omsRoomTypeName = "";
        Map<String, Integer> accountIdParam = new HashMap<>();
        accountIdParam.put("innId", order.getInnId());
        accountIdParam.put("innOtaId", DomsConstants.XCOtaId);
        accountIdParam.put("otaRoomTypeId", Integer.valueOf(order.getRoomTypeId()));
        logger.info("下单获取accountIdAndOmsRoomTypeName，传入参数=>>" + accountIdParam.toString());
        String accountResponse = HttpClientUtil.httpKvPost(CommonApi.getOmsAccountIdUrl(), JSON.toJSON(accountIdParam));
        logger.info("下单获取accountIdAndOmsRoomTypeName，返回值=>" + accountResponse);
        if (StringUtils.isNotEmpty(accountResponse)) {
            JSONObject jsonObject = JSONObject.parseObject(accountResponse);
            accountId = jsonObject.getInteger("accountId");
            omsRoomTypeName = jsonObject.getString("roomTypeName");
        } else {
            //预定失败
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("获取accountId失败," + "  预定失败", Version.v1003.getText(), OrderResponseType.Committed.name()));
            return map;
        }
        order.setAccountId(accountId);
        order.setRoomTypeName(omsRoomTypeName);
        //请求oms下单接口
        OrderParamDto orderParamDto = order.toOrderParamDto(order, DomsConstants.XCOtaId, ResourceBundleUtil.getString("ctrip_oms_user_account"), ResourceBundleUtil.getString("ctrip_oms_password"), 1);
        logger.info("请求oms下单接口，请求地址=>" + CommonApi.getOmsCreateOrder() + "参数=>" + JSON.toJSONString(orderParamDto));
        String response = com.fanqie.util.HttpClientUtil.httpPostOrder(CommonApi.getOmsCreateOrder(), orderParamDto);
        logger.info("请求oms下单接口，响应值=>" + response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (jsonObject.getInteger("status").equals(DomsConstants.HTTP_SUCCESS)) {
            //预定成功
            JointWisdomAddOrderSuccessResponse result = new JointWisdomAddOrderSuccessResponse();
            result.setMessage("预定成功");
            //oms订单号
            String orderNo = jsonObject.getString("orderNo");
            if (StringUtils.isNotEmpty(orderNo)) {
                order.setId(orderNo);
            }
            result.setVersion(Version.v1003.getText());
            result.setResponseType(OrderResponseType.Committed.name());
            result.setHotelReservations(result.getHotelReservationResult(order.getChannelOrderCode(), order.getId()));
            map.put("status", true);
            map.put("data", result);
            return map;
        } else {
            //预定失败
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError(jsonObject.getString("message") + "  预定失败", Version.v1003.getText(), OrderResponseType.Committed.name()));
            return map;
        }
    }

    @Override
    public Map<String, Object> dealCancelOrder(String xml) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //解析xml
        Order order = XmlJointWisdomUtil.getCancelOrder(xml);
        logger.info("众荟取消订单号为：" + order.getChannelOrderCode());
        //调用oms取消订单接口
        CancelOrderParamDto cancelOrderParamDto = order.toCancelOrderParam(order, DomsConstants.XCOtaId, ResourceBundleUtil.getString("ctrip_oms_user_account"), ResourceBundleUtil.getString("ctrip_oms_password"));
        logger.info("众荟取消订单传入参数" + JSON.toJSONString(cancelOrderParamDto));
        String response = com.fanqie.util.HttpClientUtil.httpGetCancelOrder(CommonApi.getCancelOrderUrl(), cancelOrderParamDto);
        logger.info("众荟取消订单返回值=>" + response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        //取消订单，同步
        if (jsonObject.getInteger("status").equals(DomsConstants.HTTP_SUCCESS)) {
            //预定成功
            JointWisdomAddOrderSuccessResponse result = new JointWisdomAddOrderSuccessResponse();
            result.setMessage("取消订单成功");
            result.setVersion(Version.v1003.getText());
            result.setResponseType(OrderResponseType.Cancelled.name());
            result.setHotelReservations(result.getHotelReservationResult(order.getChannelOrderCode(), order.getId()));
            map.put("status", true);
            map.put("data", result);
            return map;
        } else {
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("酒店拒绝取消订单," + jsonObject.getString("message"), Version.v1003.getText(), OrderResponseType.Cancelled.name()));
            return map;
        }
    }

    @Override
    public Map<String, Object> dealModifyOrder(String xml) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //解析xml得到订单对象
        Order order = XmlJointWisdomUtil.getAddOrder(xml);
        if (order.getLiveTime().getTime() > order.getLeaveTime().getTime() || order.getLiveTime().getTime() < DateUtil.addDay(new Date(), -1).getTime() || order.getLeaveTime().getTime() < DateUtil.addDay(new Date(), -1).getTime()) {
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("修改失败，请检查入住或离店时间", Version.v1003.getText(), OrderResponseType.Modified.name()));
            return map;
        }
        logger.info("众荟修改订单单号为：" + order.getChannelOrderCode());
        // 根据下单酒店查询omsaccountId
        Integer accountId = null;
        String omsRoomTypeName = "";
        Map<String, Integer> accountIdParam = new HashMap<>();
        accountIdParam.put("innId", order.getInnId());
        accountIdParam.put("innOtaId", DomsConstants.XCOtaId);
        accountIdParam.put("otaRoomTypeId", Integer.valueOf(order.getRoomTypeId()));
        logger.info("修改订单获取accountIdAndOmsRoomTypeName，传入参数=>>" + accountIdParam.toString());
        String accountResponse = HttpClientUtil.httpKvPost(CommonApi.getOmsAccountIdUrl(), JSON.toJSON(accountIdParam));
        logger.info("修改订单获取accountIdAndOmsRoomTypeName，返回值=>" + accountResponse);
        if (StringUtils.isNotEmpty(accountResponse)) {
            JSONObject jsonObject = JSONObject.parseObject(accountResponse);
            accountId = jsonObject.getInteger("accountId");
            omsRoomTypeName = jsonObject.getString("roomTypeName");
        } else {
            //预定失败
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("获取accountId失败," + "  修改失败", Version.v1003.getText(), OrderResponseType.Committed.name()));
            return map;
        }
        order.setAccountId(accountId);
        order.setRoomTypeName(omsRoomTypeName);
        //请求oms下单接口
        OrderParamDto orderParamDto = order.toOrderParamDto(order, DomsConstants.XCOtaId, ResourceBundleUtil.getString("ctrip_oms_user_account"), ResourceBundleUtil.getString("ctrip_oms_password"), 2);
        logger.info("请求oms修改订单接口，请求地址=>" + CommonApi.getOmsCreateOrder() + "参数=>" + JSON.toJSONString(orderParamDto));
        String response = com.fanqie.util.HttpClientUtil.httpPostOrder(CommonApi.getOmsCreateOrder(), orderParamDto);
        logger.info("请求oms修改订单接口，响应值=>" + response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (jsonObject.getInteger("status").equals(DomsConstants.HTTP_SUCCESS)) {
            //预定成功
            JointWisdomAddOrderSuccessResponse result = new JointWisdomAddOrderSuccessResponse();
            result.setMessage("修改成功");
            //oms订单号
            String orderNo = jsonObject.getString("orderNo");
            if (StringUtils.isNotEmpty(orderNo)) {
                order.setId(orderNo);
            }
            result.setVersion(Version.v1003.getText());
            result.setResponseType(OrderResponseType.Modified.name());
            result.setHotelReservations(result.getHotelReservationResult(order.getChannelOrderCode(), order.getId()));
            map.put("status", true);
            map.put("data", result);
            return map;
        } else {
            //修改失败
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError(jsonObject.getString("message") + "  修改订单失败", Version.v1003.getText(), OrderResponseType.Modified.name()));
            return map;
        }
    }
}
