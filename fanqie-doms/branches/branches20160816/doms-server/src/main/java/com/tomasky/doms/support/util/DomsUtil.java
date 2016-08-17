package com.tomasky.doms.support.util;

import com.tomasky.doms.dto.qunar.QunarUpdateOrderRequest;
import com.tomasky.doms.enums.ResStatus;
import com.tomasky.doms.model.jointWisdom.OTAHotelStayInfoNotifRQ;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Created by wangdayin on 2016/7/22.
 */
public class DomsUtil {
    /**
     * 转换携程的日期
     *
     * @param arrival
     * @return
     */
    public static String getDateString(String arrival) {
        if (StringUtils.isNotEmpty(arrival)) {
            String[] arry = arrival.split("T");
            if (arry.length > 1) {
                return arry[0] + " " + arry[1];
            } else {
                return arry[0];
            }
        }
        return null;
    }

    /**
     * 0:未处理、1:已接受（已分房）、2:已拒绝、3:已取消、4:验证失败、5：已接受（未分房）、6：入住、7：离店、8：noshow、9：变更已确认
     * 得到携程同步订单状态
     *
     * @param qunarUpdateOrderRequest
     * @return
     */
    public static ResStatus getCtripOrderStatus(QunarUpdateOrderRequest qunarUpdateOrderRequest) {
        switch (qunarUpdateOrderRequest.getOrderStatus()) {
            case "0":
                return ResStatus.Confirmed;
            case "1":
                return ResStatus.Confirmed;
            case "2":
                return ResStatus.Cancelled;
            case "3":
                return ResStatus.Cancelled;
            case "4":
                return ResStatus.Cancelled;
            case "5":
                return ResStatus.Confirmed;
            case "6":
                return ResStatus.InHouse;
            case "7":
                return ResStatus.CheckedOut;
            case "8":
                return ResStatus.NoShow;
        }
        return null;
    }

}
