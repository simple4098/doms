package com.tomasky.doms.helper;

import com.alibaba.fastjson.JSONObject;
import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dto.qunar.QunarAccountAndHotel;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarProductionData;
import com.tomasky.doms.dto.qunar.response.QunarRoomTypeData;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.support.util.HttpClientUtil;
import com.tomasky.doms.support.util.JacksonUtil;
import com.tomasky.doms.support.util.QunarUrlUtil;
import org.springframework.stereotype.Component;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */

public class QunarHotelInfoHelper {


    private QunarHotelInfoHelper(){}
    public static QunarHotelInfo obtQunarHotelInfo(String pmsInnCode)throws DmsException{
        QunarAccountAndHotel qunarAccountAndHotel = new QunarAccountAndHotel(pmsInnCode);
        String httpKvPost = null;
        try {
            httpKvPost = HttpClientUtil.httpKvPost(QunarUrlUtil.searchHotelListUrl(), qunarAccountAndHotel);
            JSONObject jsonObject = JSONObject.parseObject(httpKvPost);
            String data = jsonObject.getString("data");
            if (Constants.SUCCESS_QUNAR.equals(jsonObject.getString("code")) && jsonObject.get("data")!=null){
                return JacksonUtil.json2obj(data, QunarHotelInfo.class);
            }else {
                throw new DmsException(jsonObject.getString("msg"));
            }
        } catch (Exception e) {
           throw new DmsException("查询渠道酒店列表异常",e);
        }
    }

    public static QunarRoomTypeData obtQunarRoomTypeData(String json)throws DmsException{
        JSONObject jsonObject = JSONObject.parseObject(json);
        String data = jsonObject.getString("data");
        if (Constants.SUCCESS_QUNAR.equals(jsonObject.getString("code")) && jsonObject.get("data")!=null){
            return JacksonUtil.json2obj(data, QunarRoomTypeData.class);
        }else {
            throw new DmsException(jsonObject.getString("msg"));
        }
    }

    public static QunarProductionData obtQunarProductionData(String json)throws DmsException {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String data = jsonObject.getString("data");
        if (Constants.SUCCESS_QUNAR.equals(jsonObject.getString("code")) && jsonObject.get("data")!=null){
            return JacksonUtil.json2obj(data, QunarProductionData.class);
        }else {
            throw new DmsException(jsonObject.getString("msg"));
        }
    }
}