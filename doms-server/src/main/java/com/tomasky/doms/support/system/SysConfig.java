package com.tomasky.doms.support.system;

import org.springframework.stereotype.Component;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * TODO: 系统常用配置
 */
@Component
public class SysConfig {
    public static String domainOms;//oms

    public static String getDomainOms() {
        return domainOms;
    }

    public static void setDomainOms(String domainOms) {
        SysConfig.domainOms = domainOms;
    }
}
