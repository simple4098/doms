package com.tomasky.doms.support.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fanqie.core.domain.ChildOrder;
import com.fanqie.core.domain.OMSOrder;
import com.fanqie.core.domain.Person;
import com.fanqie.util.DateUtil;
import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.dto.qunar.QunarUpdateOrderRequest;
import com.tomasky.doms.enums.EnumOta;
import com.tomasky.doms.model.QunarOrder;
import com.tomasky.doms.model.QunarPriceDetail;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/14.
 * 去哪儿订单处理工具类
 */
public class QunarOrderUtil {

    /**
     * 转换去哪儿订单对象为oms订单对象
     *
     * @param qunarOrder
     * @return
     */
    public static OMSOrder getOmsOrderObject(QunarOrder qunarOrder) {
        OMSOrder omsOrder = new OMSOrder();
        omsOrder.setOtaId(EnumOta.qunar_conn.getValue());
        omsOrder.setAccountId(Integer.parseInt(qunarOrder.getHotelNo()));
        omsOrder.setChildOtaId(EnumOta.qunar_conn.name());
        omsOrder.setNeedConfirm(false);
        omsOrder.setContact(qunarOrder.getContactTelNo());
        omsOrder.setOperateType(1);
        omsOrder.setOtaOrderNo(qunarOrder.getChannelOrderNo());
        omsOrder.setPaidAmount(BigDecimal.valueOf(Double.valueOf(qunarOrder.getPrepaidAmount())));
        omsOrder.setProductTime(qunarOrder.getOrderDate());
        omsOrder.setRemind(qunarOrder.getRemark());
        omsOrder.setRoomTypeNum(Integer.parseInt(qunarOrder.getRoomNum()));
        omsOrder.setTotalPrice(BigDecimal.valueOf(Double.valueOf(qunarOrder.getOrderPrice())));
        if ("0".equals(qunarOrder.getPayType())) {
            omsOrder.setTypePay(1);
        } else {
            omsOrder.setTypePay(2);
        }
        omsOrder.setUserName(qunarOrder.getContactorName());
        //入住人信息
        omsOrder.setPersons(getOmsPersons(qunarOrder));
        omsOrder.setChildOrders(getOmsChildOrders(qunarOrder));
        return omsOrder;
    }

    /**
     * 得到oms子订单信息
     *
     * @param qunarOrder
     * @return
     */
    private static List<ChildOrder> getOmsChildOrders(QunarOrder qunarOrder) {
        List<ChildOrder> childOrderList = new ArrayList<>();
        List<QunarPriceDetail> qunarPriceDetails = JSON.parseObject(qunarOrder.getPriceDetail(), new TypeReference<List<QunarPriceDetail>>() {
        });
        if (CollectionUtils.isNotEmpty(qunarPriceDetails)) {
            childOrderList = dealPriceDetail(qunarOrder, qunarPriceDetails);
        }
        return childOrderList;
    }


    /**
     * 处理去哪儿每日价格
     *
     * @param qunarOrder
     * @param qunarPriceDetails
     * @return
     */
    private static List<ChildOrder> dealPriceDetail(QunarOrder qunarOrder, List<QunarPriceDetail> qunarPriceDetails) {
        List<ChildOrder> childOrderList = new ArrayList<>();
        for (QunarPriceDetail qunarPriceDetail : qunarPriceDetails) {
            ChildOrder childOrder = new ChildOrder();
            childOrder.setBookRoomPrice(BigDecimal.valueOf(Double.valueOf(qunarPriceDetail.getPrice())));
            childOrder.setCheckInAt(qunarPriceDetail.getDate());
            childOrder.setCheckOutAt(DateUtil.format(DateUtil.addDay(DateUtil.parseDate(qunarPriceDetail.getDate(), "yyyy-MM-dd"), 1), "yyyy-MM-dd"));
            childOrder.setRatePlanCode(qunarOrder.getRatePlanCode());
            childOrder.setRoomTypeId(getOtaRoomTypeId(qunarOrder));
            childOrder.setRoomTypeName(qunarOrder.getChannelRoomTypeName());
            childOrderList.add(childOrder);
        }
        return childOrderList;
    }

    /**
     * 获取omsota房型id
     *
     * @param qunarOrder
     * @return
     */
    private static String getOtaRoomTypeId(QunarOrder qunarOrder) {
        try {
            String response = HttpClientUtil.httpKvPost(CommonApi.getOtaRoomTypeIdUrl(), qunarOrder);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (String.valueOf(jsonObject.get("status")).equals("200")) {
                return String.valueOf(jsonObject.get("otaRoomTypeId"));
            } else {
                throw new RuntimeException("获取oms ota房型id异常");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取oms ota房型id异常", e);
        }
    }

    /**
     * 得到oms入住人信息
     *
     * @param qunarOrder
     * @return
     */
    private static List<Person> getOmsPersons(QunarOrder qunarOrder) {
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setName(qunarOrder.getCustomerName());
        personList.add(person);
        return personList;
    }

    /**
     * 解析订单请求响应值
     *
     * @param response
     */
    public static Map<String, Object> dealOrderRequestResponse(String response) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isNotEmpty(response)) {
            JSONObject jsonObject = JSONObject.parseObject(response);
            result.put("status", jsonObject.get("status"));
            result.put("message", jsonObject.get("message"));
            result.put("orderNo", jsonObject.get("orderNo"));
        } else {
            result.put("status", DomsConstants.HTTP_ERROR);
            result.put("message", "请求oms接口，返回值为空");
        }
        return result;
    }

    /**
     * 转换对象为post请求参数
     *
     * @param qunarOrder
     * @return
     */
    public static List<NameValuePair> toNameValuePair(QunarOrder qunarOrder) {
        return HttpClientUtil.commonParam(qunarOrder);
    }

    public static List<NameValuePair> getUpdateQunarOrderStatusParm(QunarUpdateOrderRequest qunarUpdateOrderRequest) {
        return HttpClientUtil.commonParam(qunarUpdateOrderRequest);
    }

    /**
     * 解析共有的响应值
     *
     * @param response
     * @return
     */
    public static boolean dealObjectResponse(String response) {
        if (StringUtils.isNotEmpty(response)) {
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (String.valueOf(DomsConstants.HTTP_SUCCESS).equals(String.valueOf(jsonObject.get("status")))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查创建订单参数
     *
     * @param qunarOrder
     * @return
     */
    public static Map<String, Object> checkCreateOrderParam(QunarOrder qunarOrder) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", false);
        if (StringUtils.isEmpty(qunarOrder.getChannelOrderNo())) {
            result.put("message", "渠道订单号为空");
        } else if (StringUtils.isEmpty(qunarOrder.getHotelNo())) {
            result.put("message", "酒店code为空");
        } else if (StringUtils.isEmpty(qunarOrder.getChannelHotelNo())) {
            result.put("message", "渠道酒店code为空");
        } else if (StringUtils.isEmpty(qunarOrder.getOrderPrice())) {
            result.put("message", "订单价格为空");
        } else {
            result.put("status", true);
        }
        return result;
    }
}
