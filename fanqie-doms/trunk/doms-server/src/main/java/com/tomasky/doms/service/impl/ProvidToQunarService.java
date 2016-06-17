package com.tomasky.doms.service.impl;

import com.fanqie.util.PropertiesUtil;
import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dto.TomatoOmsOtaInfo;
import com.tomasky.doms.dto.oms.response.OmsResult;
import com.tomasky.doms.dto.qunar.QunarStatusCode;
import com.tomasky.doms.dto.qunar.response.QunarDataResult;
import com.tomasky.doms.enums.EnumOta;
import com.tomasky.doms.support.exception.ProvidToQunarApiException;
import com.tomasky.doms.support.system.SysConfig;
import com.tomasky.doms.support.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Create by jame
 * Date: 2016/6/14
 * Version: 1.0
 * Description: 阐述
 */
@Service
public class ProvidToQunarService {

    public Logger log = LoggerFactory.getLogger(ProvidToQunarService.class);

    TomatoOmsOtaInfo tomatoOmsOtaInfo = new TomatoOmsOtaInfo();

    @Autowired
    SysConfig sysConfig;

    public String BASE_PATH = "";


    @PostConstruct
    public void init() {
        tomatoOmsOtaInfo.setOtaId(EnumOta.qunar_conn.getValue());
        tomatoOmsOtaInfo.setUserCode(PropertiesUtil.readFile("/config.properties", "qunar_conn_ota_user_code"));
        tomatoOmsOtaInfo.setUserPassword(PropertiesUtil.readFile("/config.properties", "qunar_conn_ota_user_pwd"));
        BASE_PATH = sysConfig.domainOms + "/api/qunar/conn/";
    }


    /**
     * c服务  - 数据组装
     *
     * @param hotelNo
     * @param channelOrderNos
     * @return
     */
    public QunarDataResult getOderStatus(String hotelNo, String channelOrderNos) {
        QunarDataResult result;
        try {
            String url = BASE_PATH + "/getOrderStatus";
            log.debug("=========url=======" + url);
            Map paramMap = new HashMap<>();
            paramMap.put("innId", hotelNo);
            paramMap.put("channelOrderNos", channelOrderNos);
            initOmsSecurityParam(paramMap);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramMap));
            String data = HttpClientUtil.httpKvPost(url, paramMap);
            log.debug("=======返回值=======" + data);
            OmsResult omsResult = JacksonUtil.json2obj(data, OmsResult.class);
            if (omsResult.getStatus().equals(Constants.HTTP_SUCCESS)) {
                Map omsData = (Map) omsResult.getData();
                result = new QunarDataResult(QunarStatusCode.SUCCESS, QunarStatusCode.SUCCESS_MSG, omsData);
            } else {
                log.error("调用oms查询订单入住信息返回错误");
                result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询订单入住信息出错," + omsResult.getMessage(), null);
            }
        } catch (Exception e) {
            log.error("===== 查询订单入驻信息服务  - 数据组装 - getOderStatus=====异常", e);
            throw new ProvidToQunarApiException("===== 查询订单入驻信息服务  - 数据组装 getOderStatus=====异常", e);
        }
        return result;
    }


    /**
     * 查询房态 - 参数校验
     *
     * @param hotelNos
     * @param phyRoomTypeCode
     * @param checkInDate
     * @param checkOutDate
     * @param type
     * @return
     */
    public QunarDataResult getRoomTemplateResult(String hotelNos, String phyRoomTypeCode, String checkInDate, String checkOutDate, String type) {
        QunarDataResult result;
        if (StringUtils.isEmpty(hotelNos)) {
            result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
        } else if (StringUtils.isEmpty(checkInDate) || !CommonUtil.isDate(checkInDate)) {
            result = new QunarDataResult(QunarStatusCode.ERROR_1011, "开始日期参数错误", null);
        } else if (StringUtils.isEmpty(checkOutDate) || !CommonUtil.isDate(checkInDate)) {
            result = new QunarDataResult(QunarStatusCode.ERROR_1012, "结束日期参数错误", null);
        } else if (!JodaTimeUtil.compareDate2(checkInDate, checkOutDate)) {
            result = new QunarDataResult(QunarStatusCode.ERROR_1013, "开始日期不能大于结束日期", null);
        } else {
            result = getRoomStatusTemplate(hotelNos, phyRoomTypeCode, checkInDate, checkOutDate, type);
        }
        return result;
    }


    /**
     * 查询房态模版 - 数据组装
     *
     * @param hotelNos
     * @param
     * @return
     */
    public QunarDataResult getRoomStatusTemplate(String hotelNos, String roomTypeCodes, String fromDate, String toDate, String type) throws ProvidToQunarApiException {
        QunarDataResult result;
        try {
            List<Integer> innList = CommonUtil.StrByCommaToArray(hotelNos, Integer.class);
            roomTypeCodes = getRoomTypeCodes(roomTypeCodes, innList);
            List<String> roomTypeList = CommonUtil.StrByCommaToArray(roomTypeCodes, String.class);
            if (innList.size() != roomTypeList.size()) {
                result = new QunarDataResult(QunarStatusCode.ERROR_10001, "参数格式不正确", null);
            } else {
                result = getRoomTemplateResultByOms(hotelNos, roomTypeCodes, fromDate, toDate, type);
            }
        } catch (Exception e) {
            log.error("====查询房态模版 - 数据组装 - getRoomStatusTemplate=====异常", e);
            throw new ProvidToQunarApiException("=====查询房态模版 - 数据组装- getRoomStatusTemplate=====异常", e);
        }
        return result;
    }

    /**
     * 查询房态模版 - 获取oms数据组装
     *
     * @param hotelNos
     * @param roomTypeCodes
     * @param fromDate
     * @param toDate
     * @return
     */
    public QunarDataResult getRoomTemplateResultByOms(String hotelNos, String roomTypeCodes, String fromDate, String toDate, String type) throws ProvidToQunarApiException {
        QunarDataResult result;
        try {
            String url = "";
            if ("count".equals(type)) {
                url = BASE_PATH + "/getRoomCount";
            } else if ("price".equals(type)) {
                url = BASE_PATH + "/getRoomPrice";
            } else if ("status".equals(type)) {
                url = BASE_PATH + "/getRoomStatus";
            }
            log.debug("=========url=======" + url);
            Map paramMap = new HashMap<>();
            paramMap.put("innIds", hotelNos);
            paramMap.put("roomTypeCodes", roomTypeCodes);
            paramMap.put("fromDate", fromDate);
            paramMap.put("toDate", toDate);
            initOmsSecurityParam(paramMap);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramMap));
            String data = HttpClientUtil.httpKvPost(url, paramMap);
            log.debug("=======返回值=======" + data);
            OmsResult omsResult = JacksonUtil.json2obj(data, OmsResult.class);
            if (omsResult.getStatus().equals(Constants.HTTP_SUCCESS)) {
                List<Map> domsListMap = (List<Map>) omsResult.getData();
                result = new QunarDataResult(QunarStatusCode.SUCCESS, QunarStatusCode.SUCCESS_MSG, domsListMap);
            } else {
                log.error("调用oms查询房态模版返回错误");
                result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取房态出错," + omsResult.getMessage(), null);
            }
        } catch (Exception e) {
            log.error("=====查询房态模版 - 获取oms数据组装 - getRoomTemplateResultByOms=====异常", e);
            throw new ProvidToQunarApiException("=====查询房态模版 - 获取oms数据组装 getRoomTemplateResultByOms=====异常", e);
        }
        return result;
    }

    /**
     * 查询价格计划服务  - 数据组装
     *
     * @param hotelNos
     * @return
     */
    public QunarDataResult getRateCode(String hotelNos) {
        QunarDataResult result;
        try {
            String url = BASE_PATH + "/getRateCode";
            log.debug("=========url=======" + url);
            Map paramMap = new HashMap<>();
            paramMap.put("innIds", hotelNos);
            initOmsSecurityParam(paramMap);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramMap));
            String data = HttpClientUtil.httpKvPost(url, paramMap);
            log.debug("=======返回值=======" + data);
            OmsResult omsResult = JacksonUtil.json2obj(data, OmsResult.class);
            if (omsResult.getStatus().equals(Constants.HTTP_SUCCESS)) {
                result = new QunarDataResult(QunarStatusCode.SUCCESS, QunarStatusCode.SUCCESS_MSG, omsResult.getData());
            } else {
                log.error("调用oms查询价格计划列表返回错误");
                result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取价格计划列表出错," + omsResult.getMessage(), null);
            }
        } catch (Exception e) {
            log.error("===== 查询价格计划服务  - 数据组装 - getRateCode=====异常", e);
            throw new ProvidToQunarApiException("===== 查询价格计划服务  - 数据组装 getRateCode=====异常", e);
        }
        return result;
    }

    /**
     * * 查询酒店房型列表服务 - 数据组装
     *
     * @param hotelNos
     * @param roomTypeCodes
     * @return
     */
    public QunarDataResult getRoomTypeList(String hotelNos, String roomTypeCodes) throws ProvidToQunarApiException {
        QunarDataResult result;
        try {
            List<Integer> innList = CommonUtil.StrByCommaToArray(hotelNos, Integer.class);
            roomTypeCodes = getRoomTypeCodes(roomTypeCodes, innList);
            List<String> roomTypeList = CommonUtil.StrByCommaToArray(roomTypeCodes, String.class);
            if (innList.size() != roomTypeList.size()) {
                result = new QunarDataResult(QunarStatusCode.ERROR_10001, "参数格式不正确", null);
            } else {
                result = getRoomTypeResultByOms(hotelNos, roomTypeCodes);
            }
        } catch (Exception e) {
            log.error("=====查询酒店房型列表服务- 数据组装 - getRoomTypeList=====异常", e);
            throw new ProvidToQunarApiException("=====查询酒店房型列表服务- 数据组装 - getRoomTypeList=====异常", e);
        }
        return result;
    }

    /**
     * 校验房型是否为空，如果为空则重新组装
     *
     * @param roomTypeCodes
     * @param innList
     * @return
     */
    public String getRoomTypeCodes(String roomTypeCodes, List<Integer> innList) {
        if (StringUtils.isEmpty(roomTypeCodes)) {
            roomTypeCodes = "";
            for (int i = 0; i < innList.size(); i++) {
                roomTypeCodes += " ,";
            }
        }
        return roomTypeCodes;
    }

    /**
     * * 查询酒店房型列表服务 -oms获取数据
     *
     * @param hotelNos
     * @param roomTypeCodes
     * @return
     */
    public QunarDataResult getRoomTypeResultByOms(String hotelNos, String roomTypeCodes) {
        QunarDataResult result;
        try {
            String url = BASE_PATH + "/getRoomTypeList";
            log.debug("=========url=======" + url);
            Map paramMap = new HashMap<>();
            paramMap.put("innIds", hotelNos);
            paramMap.put("roomTypeIds", roomTypeCodes);
            initOmsSecurityParam(paramMap);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramMap));
            String data = HttpClientUtil.httpKvPost(url, paramMap);
            log.debug("=======返回值=======" + data);
            OmsResult omsResult = JacksonUtil.json2obj(data, OmsResult.class);
            if (omsResult.getStatus().equals(Constants.HTTP_SUCCESS)) {
                List<Map> domsListMap = dealRoomTypeData(omsResult);
                result = new QunarDataResult(QunarStatusCode.SUCCESS, QunarStatusCode.SUCCESS_MSG, domsListMap);
            } else {
                log.error("调用oms查询酒店房型列表返回错误");
                result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取酒店房型列表出错," + omsResult.getMessage(), null);
            }
        } catch (Exception e) {
            log.error("=====查询酒店房型列表服务 -oms获取数据 - getRoomTypeResultByOms=====异常", e);
            throw new ProvidToQunarApiException("=====查询酒店房型列表服务 -oms获取数据 - getRoomTypeResultByOms=====异常", e);
        }
        return result;
    }

    /**
     * 查询酒店房型列表服务 -处理oms获取数据
     *
     * @param omsResult
     * @return
     */
    public List<Map> dealRoomTypeData(OmsResult omsResult) {
        List<Map> domsListMap = new ArrayList<>();
        try {
            List<Map> omsListMap = (List<Map>) omsResult.getData();
            if (CommonUtil.isListNotEmpty(omsListMap)) {
                for (Map oms : omsListMap) {
                    Map doms = new HashMap();
                    List<Map> roomTypeList = (List<Map>) oms.get("roomTypeList");
                    List<Map> domsRoomTypeList = new ArrayList<>();
                    if (CommonUtil.isListNotEmpty(roomTypeList)) {
                        for (Map detail : roomTypeList) {
                            Map domsDetail = new HashMap();
                            domsDetail.put("roomTypeCode", detail.get("id"));
                            domsDetail.put("roomTypeName", detail.get("room_type_name"));
                            domsRoomTypeList.add(domsDetail);
                        }
                    }
                    doms.put("roomTypeList", domsRoomTypeList);
                    doms.put("hotelNo", oms.get("hotelNo"));
                    domsListMap.add(doms);
                }
            }
        } catch (Exception e) {
            log.error("=====查询酒店房型列表服务 -处理oms获取数据 - dealRoomTypeData=====异常", e);
            throw new ProvidToQunarApiException("=====查询酒店房型列表服务 -处理oms获取数据- dealRoomTypeData=====异常", e);
        }
        return domsListMap;
    }

    /**
     * 查询酒店列表服务- 数据组装
     *
     * @param hotelNos
     * @return
     */
    public QunarDataResult getHotelList(String hotelNos) throws ProvidToQunarApiException {
        QunarDataResult result;
        try {
            String url = BASE_PATH + "/getInnList";
            log.debug("======url=======" + url);
            Map paramMap = new HashMap<>();
            paramMap.put("innIds", hotelNos);
            initOmsSecurityParam(paramMap);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramMap));
            String data = HttpClientUtil.httpKvPost(url, paramMap);
            log.debug("=====返回值===" + data);
            OmsResult omsResult = JacksonUtil.json2obj(data, OmsResult.class);
            if (omsResult.getStatus().equals(Constants.HTTP_SUCCESS)) {
                List<Map> omsListMap = (List<Map>) omsResult.getData();
                List<Map> domsListMap = new ArrayList<>();
                if (CommonUtil.isListNotEmpty(omsListMap)) {
                    for (Map oms : omsListMap) {
                        Map doms = new HashMap();
                        doms.put("hotelNo", oms.get("account_id"));
                        doms.put("hotelName", oms.get("brand_name"));
                        domsListMap.add(doms);
                    }
                }
                result = new QunarDataResult(QunarStatusCode.SUCCESS, QunarStatusCode.SUCCESS_MSG, domsListMap);
            } else {
                log.error("调用oms查询酒店列表返回错误");
                result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取酒店列表出错," + omsResult.getMessage(), null);
            }
        } catch (Exception e) {
            log.error("=====查询酒店列表服务- 数据组装 - getHotelList=====异常", e);
            throw new ProvidToQunarApiException("=====查询酒店列表服务- 数据组装 - getHotelList=====异常", e);
        }
        return result;
    }


    /**
     * oms签名参数
     *
     * @param paramMap
     * @throws ProvidToQunarApiException
     */
    public void initOmsSecurityParam(Map paramMap) throws ProvidToQunarApiException {
        paramMap.put("otaId", tomatoOmsOtaInfo.getOtaId().toString());
        Long time = System.currentTimeMillis();
        String signature = tomatoOmsOtaInfo.getOtaId().toString() + "" + time + tomatoOmsOtaInfo.getUserCode() + tomatoOmsOtaInfo.getUserPassword();
        log.debug("生成key参数字符串:" + signature);
        signature = PassWordUtil.getMd5Pwd(signature);
        log.debug("生成的key字符串:" + signature);
        paramMap.put("signature", signature);
        paramMap.put("timestamp", time.toString());
    }
}
