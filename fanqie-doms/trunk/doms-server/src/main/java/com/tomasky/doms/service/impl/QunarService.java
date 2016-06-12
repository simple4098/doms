package com.tomasky.doms.service.impl;

import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarAccount;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.QunarRemoveAccount;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.enums.LogDec;
import com.tomasky.doms.enums.OtaCode;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.helper.QunarHotelInfoHelper;
import com.tomasky.doms.helper.QunarServiceHelper;
import com.tomasky.doms.service.IQunarService;
import com.tomasky.doms.support.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private QunarServiceHelper qunarServiceHelper;
    @Resource
    private QunarHotelInfoHelper qunarHotelInfoHelper;


    @Override
    public QunarResult sendQunarPhoneCode(String otaId, QunarMobile qunarMobile) {
        //OtaInfoDto otaInfo = otaInfoDao.selectByOtaId(otaId);

        try {
            qunarServiceHelper.checkQunarMobile(qunarMobile);
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.sendCodeUrl(), qunarMobile);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            //成功、失败记日志
            if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                //todo
                logger.info("发送动态验证码 成功 【"+qunarMobile.getMobile()+"】");
            }else {
                logger.info("发送动态验证码 失败【"+qunarMobile.getMobile()+"】");
                MessageCenterUtils.saveDomsLog(OtaCode.QUNAR,null,null,qunarMobile.getOperatorName(), LogDec.SEND_CODE,"发送动态验证码 失败【"+qunarMobile.getMobile()+"】");
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿发送验证异常",e);
            return  new QunarResult(Constants.ERROR_QUNAR,e.getMessage());
        }
    }

    @Override
    public QunarHotelInfo createQunarPmsHotel(OmsPram omsPram)throws DmsException{
        //OtaInfoDto otaInfo = otaInfoDao.selectByOtaId(omsPram.getOtaId());
        try{
            QunarAccount qunarAccount = qunarServiceHelper.checkQunarAccount(omsPram);
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.openAccountUrl(), qunarAccount);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            //开通成功
            if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                //todo 开通成功 记录日志
                MessageCenterUtils.saveDomsLog(OtaCode.QUNAR,omsPram.getInnId(),null,omsPram.getOperatorName(), LogDec.OPEN_ACCOUNT,LogDec.OPEN_ACCOUNT.getValue()+qunarResult.getMsg());
                QunarHotelInfo qunarHotelInfo = qunarHotelInfoHelper.obtQunarHotelInfo(omsPram.getInnId().toString());
                if (qunarHotelInfo!=null){
                    logger.info("渠道酒店列表:"+JacksonUtil.obj2json(qunarHotelInfo));
                }
                return qunarHotelInfo;
            }else {
                throw  new DmsException(qunarResult.getMsg());
            }
        }catch (Exception e){
            logger.error("createQunarPmsHotel 异常",e);
            throw  new DmsException("获取渠道酒店列表异常",e);
        }
    }

    @Override
    public QunarResult removeDockingAccount(OmsPram omsPram) throws DmsException {
        QunarRemoveAccount qunarRemoveAccount = qunarServiceHelper.checkQunarRemoveAccount(omsPram);
        String httpPost = null;
        try{
            httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.closeAccountUrl(), qunarRemoveAccount);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                //todo 记录日志
                return qunarResult;
            }else {
                throw  new DmsException(qunarResult.getMsg());
            }
        }catch (Exception e){
            throw  new DmsException("关闭渠道直连异常",e);
        }
    }
}