package com.tomasky.doms.service;

import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.exception.DmsException;

/**
 * DESC : 去哪儿对接接口逻辑层
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public interface IQunarService {


    /**
     * 去哪儿发送动态验证码
     * @param otaId 渠道id
     * @param qunarMobile 调用去哪儿接口参数对象
     */
    QunarResult sendQunarPhoneCode(String otaId,QunarMobile qunarMobile);

    /**
     * 1 去哪儿开通渠道 2 查询渠道酒店列表
     * @param omsPram oms参数列表
     * @return
     */
    QunarHotelInfo createQunarPmsHotel(OmsPram omsPram) throws DmsException;

}
