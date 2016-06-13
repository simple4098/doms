package com.tomasky.doms.controller;

import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dto.TomatoOmsOtaInfo;
import com.tomasky.doms.dto.oms.response.OmsResult;
import com.tomasky.doms.dto.qunar.QunarStatusCode;
import com.tomasky.doms.dto.qunar.response.QunarDataResult;
import com.tomasky.doms.enums.EnumOta;
import com.tomasky.doms.service.ITomatoOmsOtaInfoService;
import com.tomasky.doms.support.exception.ProvidToQunarApiException;
import com.tomasky.doms.support.system.SysConfig;
import com.tomasky.doms.support.util.CommonUtil;
import com.tomasky.doms.support.util.HttpClientUtil;
import com.tomasky.doms.support.util.JacksonUtil;
import com.tomasky.doms.support.util.PassWordUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * DOTO: 番茄PMS提供给去呼呼的接口
 */
@Controller
@RequestMapping("/api")
public class ProvidToQunarApiController {
    private Logger log = LoggerFactory.getLogger(ProvidToQunarApiController.class);

    TomatoOmsOtaInfo tomatoOmsOtaInfo;
    @Autowired
    ITomatoOmsOtaInfoService tomatoOmsOtaInfoService;

    @PostConstruct
    public void init() {
        TomatoOmsOtaInfo otainfo = new TomatoOmsOtaInfo();
        otainfo.setOtaId(EnumOta.qunar_conn.getValue());
        tomatoOmsOtaInfo = tomatoOmsOtaInfoService.query(otainfo);
    }

    @Autowired
    SysConfig sysConfig;

    private final String BASE_PATH = sysConfig.domainOms + "/api/qunar/conn/";

    /**
     * 查询酒店列表服务
     *
     * @param hotelNos
     * @return
     */
    @RequestMapping("/tomasky/hotel/queryList.do")
    @ResponseBody
    public QunarDataResult queryHotelList(String hotelNos) {
        log.debug("=====hotelNos====" + hotelNos);
        QunarDataResult result;
        try {
            if (StringUtils.isEmpty(hotelNos)) {
                result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
            } else {
                result = getHotelList(hotelNos);
            }
        } catch (Exception e) {
            log.error("====查询酒店列表服务=====queryHotelList===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取酒店列表出错", null);
        }
        return result;
    }

    /**
     * 查询酒店房型列表服务
     *
     * @param hotelNos
     * @param roomTypeCodes
     * @return
     */
    @RequestMapping("/tomasky/roomType/queryList.do")
    @ResponseBody
    public QunarDataResult queryRoomTypeList(String hotelNos, String roomTypeCodes) {
        log.debug("=====hotelNos====" + hotelNos);
        QunarDataResult result;
        try {
            if (StringUtils.isEmpty(hotelNos)) {
                result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
            } else {
                result = getRoomTypeList(hotelNos, roomTypeCodes);
            }
        } catch (Exception e) {
            log.error("====查询酒店房型列表服务=====queryRoomTypeList===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取酒店房型列表出错", null);
        }
        return result;
    }

    /**
     * 查询价格计划服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/rateCode/queryRateCode.do")
    @ResponseBody
    public QunarDataResult queryRateCode(String hotelNos) {
        log.debug("=====hotelNos====" + hotelNos);
        QunarDataResult result;
        try {
            if (StringUtils.isEmpty(hotelNos)) {
                result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
            } else {
                result = getRateCode(hotelNos);
            }
        } catch (Exception e) {
            log.error("====查询价格计划服务=====queryRateCode===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取价格计划列表出错", null);
        }
        return result;
    }

    /**
     * 查询实时房量服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/roomStatus/queryRoomCount.do")
    @ResponseBody
    public QunarDataResult queryRoomCount(String hotelNos, String roomTypeCodes, String checkInDate, String checkOutDate) {
        QunarDataResult result;
        try {
            result = getRoomTemplateResult(hotelNos, roomTypeCodes, checkInDate, checkOutDate, "count");
        } catch (Exception e) {
            log.error("====查询实时房量服务=====queryRoomCount===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询实时房量服务出错", null);
        }
        return result;
    }

    /**
     * 查询实时房态服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/roomStatus/queryRoomStatus.do")
    @ResponseBody
    public QunarDataResult queryRoomStatus(String hotelNos, String phyRoomTypeCode, String checkInDate, String checkOutDate) {
        QunarDataResult result;
        try {
            result = getRoomTemplateResult(hotelNos, phyRoomTypeCode, checkInDate, checkOutDate, "status");
        } catch (Exception e) {
            log.error("====查询实时房态服务=====queryRoomStatus===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询实时房态服务", null);
        }
        return result;
    }

    /**
     * 查询实时房价服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/roomStatus/queryRoomPrice.do")
    @ResponseBody
    public QunarDataResult queryRoomPrice(String hotelNos, String phyRoomTypeCode, String checkInDate, String checkOutDate) {
        QunarDataResult result;
        try {
            result = getRoomTemplateResult(hotelNos, phyRoomTypeCode, checkInDate, checkOutDate, "price");
        } catch (Exception e) {
            log.error("====查询实时房价服务=====queryRoomStatus===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询实时房价服务", null);
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
    private QunarDataResult getRoomTemplateResult(String hotelNos, String phyRoomTypeCode, String checkInDate, String checkOutDate, String type) {
        QunarDataResult result;
        if (StringUtils.isEmpty(hotelNos)) {
            result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
        } else if (!CommonUtil.isDate(checkInDate)) {
            result = new QunarDataResult(QunarStatusCode.ERROR_1011, "开始日期参数错误", null);
        } else if (!CommonUtil.isDate(checkInDate)) {
            result = new QunarDataResult(QunarStatusCode.ERROR_1012, "结束日期参数错误", null);
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
    private QunarDataResult getRoomStatusTemplate(String hotelNos, String roomTypeCodes, String fromDate, String toDate, String type) throws ProvidToQunarApiException {
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
    private QunarDataResult getRoomTemplateResultByOms(String hotelNos, String roomTypeCodes, String fromDate, String toDate, String type) throws ProvidToQunarApiException {
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
            List<NameValuePair> paramList = new ArrayList<>();
            BasicNameValuePair nvp = new BasicNameValuePair("innIds", hotelNos);
            BasicNameValuePair roomTypeNvp = new BasicNameValuePair("roomTypeCodes", roomTypeCodes);
            BasicNameValuePair fromDateNvp = new BasicNameValuePair("fromDate", fromDate);
            BasicNameValuePair toDateNvp = new BasicNameValuePair("toDate", toDate);
            initOmsSecurityParam(paramList);
            paramList.add(nvp);
            paramList.add(roomTypeNvp);
            paramList.add(fromDateNvp);
            paramList.add(toDateNvp);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramList));
            String data = HttpClientUtil.getResponseInfoByPost(Constants.HTTP_GET_TYPE_STRING, url, paramList);
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
    private QunarDataResult getRateCode(String hotelNos) {
        QunarDataResult result;
        String url = BASE_PATH + "/getRateCode";
        log.debug("=========url=======" + url);
        List<NameValuePair> paramList = new ArrayList<>();
        BasicNameValuePair nvp = new BasicNameValuePair("innIds", hotelNos);
        paramList.add(nvp);
        initOmsSecurityParam(paramList);
        log.debug("=====参数====" + JacksonUtil.obj2json(paramList));
        String data = HttpClientUtil.getResponseInfoByPost(Constants.HTTP_GET_TYPE_STRING, url, paramList);
        OmsResult omsResult = JacksonUtil.json2obj(data, OmsResult.class);
        if (omsResult.getStatus().equals(Constants.HTTP_SUCCESS)) {
            result = new QunarDataResult(QunarStatusCode.SUCCESS, QunarStatusCode.SUCCESS_MSG, omsResult.getData());
        } else {
            log.error("调用oms查询价格计划列表返回错误");
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取价格计划列表出错," + omsResult.getMessage(), null);
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
    private QunarDataResult getRoomTypeList(String hotelNos, String roomTypeCodes) throws ProvidToQunarApiException {
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
    private String getRoomTypeCodes(String roomTypeCodes, List<Integer> innList) {
        if (StringUtils.isEmpty(roomTypeCodes)) {
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
    private QunarDataResult getRoomTypeResultByOms(String hotelNos, String roomTypeCodes) {
        QunarDataResult result;
        try {
            String url = BASE_PATH + "/getRoomTypeList";
            log.debug("=========url=======" + url);
            List<NameValuePair> paramList = new ArrayList<>();
            BasicNameValuePair nvp = new BasicNameValuePair("innIds", hotelNos);
            BasicNameValuePair roomTypeNvp = new BasicNameValuePair("roomTypeIds", roomTypeCodes);
            paramList.add(nvp);
            paramList.add(roomTypeNvp);
            initOmsSecurityParam(paramList);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramList));
            String data = HttpClientUtil.getResponseInfoByPost(Constants.HTTP_GET_TYPE_STRING, url, paramList);
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
    private List<Map> dealRoomTypeData(OmsResult omsResult) {
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
    private QunarDataResult getHotelList(String hotelNos) throws ProvidToQunarApiException {
        QunarDataResult result;
        try {
            String url = BASE_PATH + "/getInnList";
            log.debug("======url=======" + url);
            List<NameValuePair> paramList = new ArrayList<>();
            BasicNameValuePair nvp = new BasicNameValuePair("innIds", hotelNos);
            paramList.add(nvp);
            initOmsSecurityParam(paramList);
            log.debug("=====参数====" + JacksonUtil.obj2json(paramList));
            String data = HttpClientUtil.getResponseInfoByPost(Constants.HTTP_GET_TYPE_STRING, url, paramList);
            OmsResult omsResult = JacksonUtil.json2obj(data, OmsResult.class);
            if (omsResult.getStatus().equals(Constants.HTTP_SUCCESS)) {
                List<Map> omsListMap = (List<Map>) omsResult.getData();
                List<Map> domsListMap = new ArrayList<>();
                if (CommonUtil.isListNotEmpty(omsListMap)) {
                    for (Map oms : omsListMap) {
                        Map doms = new HashMap();
                        doms.put("hotelNo", oms.get("inn_id"));
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
     * @param paramList
     * @throws ProvidToQunarApiException
     */
    private void initOmsSecurityParam(List<NameValuePair> paramList) throws ProvidToQunarApiException{
        BasicNameValuePair otaNvp = new BasicNameValuePair("otaId", tomatoOmsOtaInfo.getOtaId().toString());
        Long time = System.currentTimeMillis();
        String signature =  tomatoOmsOtaInfo.getOtaId().toString() + "" + time + tomatoOmsOtaInfo.getUserCode() + tomatoOmsOtaInfo.getUserPassword();
        log.debug("生成key参数字符串:" + signature);
        signature = PassWordUtil.getMd5Pwd(signature);
        log.debug("生成的key字符串:" + signature);
        BasicNameValuePair signatureNvp = new BasicNameValuePair("signature", signature);
        BasicNameValuePair timestampNvp = new BasicNameValuePair("timestamp", time.toString());
        paramList.add(otaNvp);
        paramList.add(signatureNvp);
        paramList.add(timestampNvp);
    }
}
