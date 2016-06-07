package com.tomasky.doms.service;

import com.tomasky.doms.dto.OtaInfoDto;

import java.util.List;

/**
 * DESC : 查询企业开通渠道
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
public interface IOtaInfoService {

    /**
     *  全部的渠道的关联表信息
     */
    List<OtaInfoDto> findOtaInfoList();

}
