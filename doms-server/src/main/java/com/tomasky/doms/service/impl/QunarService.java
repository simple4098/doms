package com.tomasky.doms.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dao.IOtaInfoDao;
import com.tomasky.doms.dao.IOtaInnDao;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.OtaInfoDto;
import com.tomasky.doms.dto.qunar.QunarAccount;
import com.tomasky.doms.dto.qunar.QunarAccountAndHotel;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.helper.QunarHotelInfoHelper;
import com.tomasky.doms.helper.QunarServiceHelper;
import com.tomasky.doms.model.OtaInn;
import com.tomasky.doms.service.IQunarService;
import com.tomasky.doms.support.util.SecurityUtil;
import com.tomasky.doms.support.util.HttpClientUtil;
import com.tomasky.doms.support.util.JacksonUtil;
import com.tomasky.doms.support.util.QunarUrlUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * DESC : 对接去哪接口实现类
 * 建立去哪与番茄的关系
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
@Service
public class QunarService implements IQunarService {
    private final  static Logger logger = LoggerFactory.getLogger(QunarService.class);
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private QunarServiceHelper qunarServiceHelper;
    @Resource
    private QunarHotelInfoHelper qunarHotelInfoHelper;
    @Resource
    private IOtaInnDao otaInnDao;

    @Override
    public QunarResult sendQunarPhoneCode(String otaId, QunarMobile qunarMobile) {
        OtaInfoDto otaInfo = otaInfoDao.selectByOtaId(otaId);

        try {
            qunarServiceHelper.checkQunarMobile(qunarMobile);
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.sendCodeUrl(), qunarMobile);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            //成功、失败记日志
            if (StringUtils.isNotEmpty(httpPost) && Constants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
                //todo
                logger.info("发送动态验证码 成功 【"+qunarMobile.getMobile()+"】");
            }else {
                logger.info("发送动态验证码 失败【"+qunarMobile.getMobile()+"】");
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿发送验证异常",e);
            return  new QunarResult(Constants.ERROR_QUNAR,e.getMessage());
        }
    }

    @Override
    public QunarHotelInfo createQunarPmsHotel(OmsPram omsPram)throws DmsException{
        try{
            QunarAccount qunarAccount = qunarServiceHelper.checkQunarAccount(omsPram);
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.openAccountUrl(), qunarAccount);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            //开通成功
            if (StringUtils.isNotEmpty(httpPost) && Constants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
                //todo 开通成功
                otaInnDao.saveOtaInn(new OtaInn());
                return qunarHotelInfoHelper.obtQunarHotelInfo(omsPram.getInnCode());
            }
        }catch (Exception e){
            logger.error("createQunarPmsHotel 异常",e);
            throw  new DmsException("获取渠道酒店列表异常",e);
        }
        return null;
    }
}
