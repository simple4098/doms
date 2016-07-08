package com.tomasky.doms.dao;

import com.tomasky.doms.model.OtaRoomTypeRelationship;

/**
 * DESC : 客栈房型与渠道酒店房型关联 持久化层
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public interface IOtaRoomTypeRelationshipDao {

    void saveOtaRoomTypeRelationship(OtaRoomTypeRelationship otaRoomTypeRelationship);
}
