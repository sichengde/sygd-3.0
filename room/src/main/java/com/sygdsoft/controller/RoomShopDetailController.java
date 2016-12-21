package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.RoomShopDetail;
import com.sygdsoft.service.RoomShopDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-21.
 */
@RestController
public class RoomShopDetailController {
    @Autowired
    RoomShopDetailService roomShopDetailService;

    @RequestMapping(value = "roomShopDetailGet")
    public List<RoomShopDetail> roomShopDetailGet(@RequestBody Query query) throws Exception {
        return roomShopDetailService.get(query);
    }

    /**
     * 通过时间来查询房吧明细（）注意是房吧，不包括零售
     */
    @RequestMapping(value = "roomShopDetailGetByDate")
    public List<RoomShopDetail> roomShopDetailGetByDate(@RequestBody ReportJson reportJson){
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        return roomShopDetailService.getRoomShopByDoneTime(beginTime, endTime);
    }

    /**
     * 通过时间来查询零售明细（）注意是零售，不包括房吧
     */
    @RequestMapping(value = "retailGetByDate")
    public List<RoomShopDetail> retailGetByDate(@RequestBody ReportJson reportJson){
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        return roomShopDetailService.getRetailByDoneTime(beginTime, endTime);
    }
}
