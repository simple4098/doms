package com.tomasky.doms.service.impl;

import com.tomasky.doms.dto.OtaInfoDto;
import com.tomasky.doms.service.IOtaInfoService;
import com.tomasky.doms.dao.IOtaInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
@Service
public class OtaInfoService implements IOtaInfoService {
    @Resource
    private IOtaInfoDao otaInfoDao;




    @Override
    public List<OtaInfoDto> findOtaInfoList() {
        return otaInfoDao.selectOtaInfoList();
    }



}
