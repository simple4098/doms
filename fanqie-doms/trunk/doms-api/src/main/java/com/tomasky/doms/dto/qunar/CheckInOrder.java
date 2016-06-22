package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.enums.StayStatus;
import com.tomasky.doms.model.QunarOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class CheckInOrder {
    //pms订单号
    private String orderNo;
    //入住信息
    private List<Stay> stayList;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<Stay> getStayList() {
        return stayList;
    }

    public void setStayList(List<Stay> stayList) {
        this.stayList = stayList;
    }

    /**
     * 得到订单信息
     *
     * @param qunarOrder
     * @return
     */
    public static List<CheckInOrder> getOrderList(QunarOrder qunarOrder) {
        List<CheckInOrder> checkInOrders = new ArrayList<CheckInOrder>();
        CheckInOrder checkInOrder = new CheckInOrder();
        checkInOrder.setOrderNo(qunarOrder.getOmsOrderNo());
        checkInOrder.setStayList(getOrderStayList(qunarOrder));
        checkInOrders.add(checkInOrder);
        return checkInOrders;
    }

    private static List<Stay> getOrderStayList(QunarOrder qunarOrder) {
        String roomNo = qunarOrder.getRoomNo();
        String customerName = qunarOrder.getCustomerName();
        List<Stay> stayList = new ArrayList<Stay>();
        if (null != roomNo && !"".equals(roomNo) && null != customerName && !"".equals(customerName)) {
            String[] roomNos = roomNo.split(",");
            String[] customerNames = customerName.split(",");
            if (roomNos.length == customerNames.length) {
                for (int i = 0; i < roomNos.length; i++) {
                    Stay stay = new Stay();
                    stay.setPhyRoomTypeCode(qunarOrder.getRoomTypeCode());
                    stay.setGuestName(customerNames[i]);
                    if (null != qunarOrder.getLiveInDate() && !"".equals(qunarOrder.getLiveInDate())) {
                        stay.setRealCheckInTime(qunarOrder.getLiveInDate());
                    } else {
                        stay.setRealCheckOutTime(qunarOrder.getCheckOutTime());
                    }
                    if (null != qunarOrder.getLeaveOutDate() && !"".equals(qunarOrder.getLeaveOutDate())) {
                        stay.setRealCheckOutTime(qunarOrder.getCheckOutTime());
                    } else {
                        stay.setRealCheckOutTime(qunarOrder.getCheckOutTime());
                    }
                    stay.setRemark(qunarOrder.getRemark());
                    stay.setRoomNo(roomNos[i]);
                    stay.setStayStatus(getStayStatus(qunarOrder).getKey());
                    stayList.add(stay);
                }
            }
        }
        return null;
    }

    private static StayStatus getStayStatus(QunarOrder qunarOrder) {
        String omsOrderStatus = qunarOrder.getOmsOrderStatus();
        if (omsOrderStatus.equals("0") || omsOrderStatus.equals("1") || omsOrderStatus.equals("5")) {
            return StayStatus.s1;
        } else if (omsOrderStatus.equals("2") || omsOrderStatus.equals("3") || omsOrderStatus.equals("4")) {
            return StayStatus.s5;
        } else if (omsOrderStatus.equals("6")) {
            return StayStatus.s2;
        } else if (omsOrderStatus.equals("7")) {
            return StayStatus.s3;
        }
        return StayStatus.s7;
    }
}
