package com.tomasky.doms.helper;

import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarAccount;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.exception.DmsException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
@Component
public class QunarServiceHelper {

    public  void checkQunarMobile(QunarMobile qunarMobile)throws DmsException{
        try{
            Assert.notNull(qunarMobile.getMobile());
        }catch (Exception e){
            throw  new DmsException("发送验证码参数异常,电话号码为空");
        }
    }

    public QunarAccount checkQunarAccount(OmsPram omsPram)throws DmsException {
        try{
            Assert.notNull(omsPram.getOtaId());
            Assert.notNull(omsPram.getPhoneCode());
            Assert.notNull(omsPram.getMobile());
            Assert.notNull(omsPram.getInnId());
            QunarAccount qunarAccount = new QunarAccount();
            qunarAccount.setUserAccount(omsPram.getMobile());
            qunarAccount.setVerificationCode(omsPram.getPhoneCode());
            qunarAccount.setOperatorGuid(omsPram.getOperatorGuid());
            qunarAccount.setOperatorName(omsPram.getOperatorName());
            return qunarAccount;
        }catch (Exception e){
            throw  new DmsException("开通渠道参数异常");
        }
    }
}
