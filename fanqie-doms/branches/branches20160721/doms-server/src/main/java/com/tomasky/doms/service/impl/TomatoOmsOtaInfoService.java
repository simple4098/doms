package com.tomasky.doms.service.impl;

import com.tomasky.doms.dao.ITomatoOmsOtaInfoDao;
import com.tomasky.doms.dto.TomatoOmsOtaInfo;
import com.tomasky.doms.service.ITomatoOmsOtaInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Create by jame
 * Date: 2016/6/13
 * Version: 1.0
 * Description: 阐述
 */
@Service
public class TomatoOmsOtaInfoService implements ITomatoOmsOtaInfoService {
    @Resource
    ITomatoOmsOtaInfoDao iTomatoOmsOtaInfoDao;

    @Override
    public TomatoOmsOtaInfo query(TomatoOmsOtaInfo tomatoOmsOtaInfo) {
        return iTomatoOmsOtaInfoDao.query(tomatoOmsOtaInfo);
    }
}
