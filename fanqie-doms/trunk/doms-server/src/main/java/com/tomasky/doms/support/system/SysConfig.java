package com.tomasky.doms.support.system;

import com.fanqie.util.PropertiesUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * DOTO: 系统常用配置
 */
@Component
public class SysConfig {
    public static String domainOms;//oms

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        domainOms = PropertiesUtil.readFile("/config.properties", "domain_oms");
//    }


    public static String getDomainOms() {
        return domainOms;
    }

    public static void setDomainOms(String domainOms) {
        SysConfig.domainOms = domainOms;
    }
}
