package com.tomasky.doms.support.util;

import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import org.apache.commons.lang.StringUtils;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/8
 * @version: v1.0.0
 */
public class QunarResultUtil {
    private QunarResultUtil(){

    }

    public static boolean isSuccess(String httpPost ,QunarResult qunarResult){
        return StringUtils.isNotEmpty(httpPost) && Constants.SUCCESS_QUNAR.equals(qunarResult.getCode());
    }
}
