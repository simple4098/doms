package com.tomasky.doms.support.util;


import com.tomasky.doms.enums.LogDec;
import com.tomasky.doms.enums.OtaCode;
import com.tomato.framework.log.client.BizLogClient;
import com.tomato.framework.log.model.BizLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  消息中心类
 */
public class MessageCenterUtils {

    private static BizLogClient bizLogClient = new BizLogClient();

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCenterUtils.class);


    /**
     * 去呼呼直连日志
     * @param otaCode 渠道
     * @param innId 客栈id
     * @param roomTypeId 房型id
     * @param userName 用户名或者用户id
     * @param logDec 日志类型
     * @param content 日志内容
     */
    public static void saveDomsLog(OtaCode otaCode, Integer innId, Integer roomTypeId,
                                       String userName, LogDec logDec, String content){
        boolean logOpen = ResourceBundleUtil.getBoolean("log.open");
        if (logOpen){
            LOGGER.info("=====记录日志开始======");
            BizType parentBizType = new BizType(logDec.getpId(),logDec.getValue(), null);
            BizType bizType = new BizType(logDec.getLogTypeId(), logDec.getValue(), parentBizType);
            BizData bizData = new BizData(logDec,userName==null?"系统操作":userName,content,innId,roomTypeId,otaCode);
            BizLog bizLog = new BizLog(innId, bizType, "DOMS", bizData);
            bizLogClient.save(bizLog);
            LOGGER.info("=====记录日志结束======");
        }
    }




}


