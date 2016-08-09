package com.tomasky.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.fanqie.jw.JointWiddomRequest;
import com.fanqie.util.DateUtil;
import com.tomasky.doms.dto.qunar.QunarUpdateOrderRequest;
import com.tomasky.doms.enums.ResStatus;
import com.tomasky.doms.model.Person;
import com.tomasky.doms.service.ICtripOrderHelperService;
import com.tomasky.doms.support.util.DomsUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.opentravel.ota._2003._05.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by wangdayin on 2016/7/28.
 */
@Service
public class CtripOrderHelperServiceImpl implements ICtripOrderHelperService {
    private static final Logger logger = LoggerFactory.getLogger(CtripOrderHelperServiceImpl.class);

    @Override
    public void pushOrderStatus(QunarUpdateOrderRequest qunarUpdateOrderRequest, String otaRoomTypeId) {
        //组装携程同步订单参数
        //1.根据同步状态设置订单状态
        ResStatus ctripOrderStatus = DomsUtil.getCtripOrderStatus(qunarUpdateOrderRequest);
        if (null != ctripOrderStatus) {
            //只同步入住离店
            if (ctripOrderStatus.equals(ResStatus.InHouse) || ctripOrderStatus.equals(ResStatus.CheckedOut) || ctripOrderStatus.equals(ResStatus.NoShow)) {
                OTAHotelStayInfoNotifRQ otaHotelStayInfoNotifRQ = new OTAHotelStayInfoNotifRQ();
                otaHotelStayInfoNotifRQ.setVersion(BigDecimal.valueOf(0.0));
                StayInfosType stayInfosType = new StayInfosType();
                StayInfoType stayInfoType = new StayInfoType();
                HotelReservationType hotelReservationType = new HotelReservationType();
                hotelReservationType.setResStatus(ctripOrderStatus.name());
                //组装酒店和房型信息
                ArrayOfRoomStaysTypeRoomStay arrayOfRoomStaysTypeRoomStay = new ArrayOfRoomStaysTypeRoomStay();
                ArrayOfRoomStaysTypeRoomStay.RoomStay roomStay = new ArrayOfRoomStaysTypeRoomStay.RoomStay();
                //酒店信息
                BasicPropertyInfoType basicPropertyInfoType = new BasicPropertyInfoType();
                basicPropertyInfoType.setHotelCode(qunarUpdateOrderRequest.getInnId());
                basicPropertyInfoType.setHotelName(qunarUpdateOrderRequest.getInnName());
                roomStay.setBasicPropertyInfo(basicPropertyInfoType);
                //入住离店日期
                DateTimeSpanType timeSpan = new DateTimeSpanType();
                if (StringUtils.isNotEmpty(qunarUpdateOrderRequest.getLiveInDate())) {
                    timeSpan.setStart(DateUtil.format(DateUtil.parse(qunarUpdateOrderRequest.getLiveInDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
                }
                if (StringUtils.isNotEmpty(qunarUpdateOrderRequest.getLeaveOutDate())) {
                    timeSpan.setEnd(DateUtil.format(DateUtil.parse(qunarUpdateOrderRequest.getLeaveOutDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
                }
                roomStay.setTimeSpan(timeSpan);
                //房型信息
                ArrayOfRoomTypeType arrayOfRoomTypeType = new ArrayOfRoomTypeType();
                RoomTypeType roomTypeType = new RoomTypeType();
                roomTypeType.setRoomTypeCode(otaRoomTypeId);
                roomTypeType.setRoomID(qunarUpdateOrderRequest.getRoomNames());
                arrayOfRoomTypeType.getRoomType().add(roomTypeType);
                roomStay.setRoomTypes(arrayOfRoomTypeType);
                arrayOfRoomStaysTypeRoomStay.getRoomStay().add(roomStay);
                hotelReservationType.setRoomStays(arrayOfRoomStaysTypeRoomStay);

                //入住人信息
                ArrayOfResGuestType arrayOfResGuestType = new ArrayOfResGuestType();
                ResGuestType resGuestType = new ResGuestType();
                ArrayOfProfilesTypeProfileInfo arrayOfProfilesTypeProfileInfo = new ArrayOfProfilesTypeProfileInfo();
                if (CollectionUtils.isNotEmpty(qunarUpdateOrderRequest.getPersonList())) {
                    for (Person person : qunarUpdateOrderRequest.getPersonList()) {
                        ArrayOfProfilesTypeProfileInfo.ProfileInfo profileInfo = new ArrayOfProfilesTypeProfileInfo.ProfileInfo();
                        ProfileType profileType = new ProfileType();
                        CustomerType customerType = new CustomerType();
                        PersonNameType personNameType = new PersonNameType();
                        if (StringUtils.isNotEmpty(person.getName())) {
                            personNameType.setSurname(person.getName().substring(0, 1));
                            personNameType.getGivenName().add(person.getName().substring(1, person.getName().length()));
                        }
                        customerType.getPersonName().add(personNameType);
                        profileType.setCustomer(customerType);
                        profileInfo.setProfile(profileType);
                        arrayOfProfilesTypeProfileInfo.getProfileInfo().add(profileInfo);
                    }
                }
                resGuestType.setProfiles(arrayOfProfilesTypeProfileInfo);
                arrayOfResGuestType.getResGuest().add(resGuestType);
                hotelReservationType.setResGuests(arrayOfResGuestType);

                //订单信息
                ResGlobalInfoType resGlobalInfoType = new ResGlobalInfoType();
                ArrayOfHotelReservationIDsTypeHotelReservationID arrayOfHotelReservationIDsTypeHotelReservationID = new ArrayOfHotelReservationIDsTypeHotelReservationID();
                ArrayOfHotelReservationIDsTypeHotelReservationID.HotelReservationID hotelReservationIDChannel = new ArrayOfHotelReservationIDsTypeHotelReservationID.HotelReservationID();
                hotelReservationIDChannel.setResIDType("13");
                hotelReservationIDChannel.setResIDValue(qunarUpdateOrderRequest.getChannelOrderNo());
                arrayOfHotelReservationIDsTypeHotelReservationID.getHotelReservationID().add(hotelReservationIDChannel);
                ArrayOfHotelReservationIDsTypeHotelReservationID.HotelReservationID hotelReservationIDOms = new ArrayOfHotelReservationIDsTypeHotelReservationID.HotelReservationID();
                hotelReservationIDOms.setResIDType("14");
                hotelReservationIDOms.setResIDValue(qunarUpdateOrderRequest.getOmsOrderNo());
                arrayOfHotelReservationIDsTypeHotelReservationID.getHotelReservationID().add(hotelReservationIDOms);

                resGlobalInfoType.setHotelReservationIDs(arrayOfHotelReservationIDsTypeHotelReservationID);
                hotelReservationType.setResGlobalInfo(resGlobalInfoType);

                stayInfoType.setHotelReservation(hotelReservationType);
                stayInfosType.getStayInfo().add(stayInfoType);
                otaHotelStayInfoNotifRQ.setStayInfos(stayInfosType);
                //调用同步订单状态接口
                logger.info("请求众荟同步订单状态接口，传入参数=>" + JSON.toJSONString(otaHotelStayInfoNotifRQ));
                OTAHotelStayInfoNotifRS otaHotelStayInfoNotifRS = JointWiddomRequest.getDefaultInstance().otaHotelStayInfoNotifRQ(otaHotelStayInfoNotifRQ);
                logger.info("请求众荟同步订单状态接口，返回值=>" + JSON.toJSONString(otaHotelStayInfoNotifRS));
            } else {
                throw new RuntimeException("携程订单状态类型不在同步状态之中，pms订单状态=>" + qunarUpdateOrderRequest.getOrderStatus());
            }
        }
    }
}

