package com.tomasky.doms.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.tomasky.doms.dto.OtaInfoDto;
import com.tomasky.doms.service.IOtaInfoService;
import com.tomasky.doms.support.util.JsonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * DESC : 对接OTA ... controller
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/api")
public class APIController  {

    private static  final Logger log = LoggerFactory.getLogger(APIController.class);

    @Resource
    private IOtaInfoService otaInfoService;






    @RequestMapping("/hotel/timer")
    @ResponseBody
    public Object hotelTimer() throws IOException {
        log.info("=======start=======");
        JsonModel jsonModel = new JsonModel("200","sdsd");
        List<OtaInfoDto> infoDtoList = otaInfoService.findOtaInfoList();
        jsonModel.setMessage(JSON.json(infoDtoList));
        log.info("============end============");
        return  jsonModel;
    }





}
