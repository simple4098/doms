package com.tomasky.doms.utils;

import com.fanqie.util.DateUtil;

import java.util.Date;

/**
 * Created by wangdayin on 2016/1/12.
 * 众荟的工具类
 */
public class JointWisdomUtils {
    public static String getTimeStamp() {
        Date date = new Date();
        return DateUtil.format(date, "yyyy-MM-dd") + "T" + DateUtil.format(date, "HH:mm:dd") + "+08:00";
    }
}
