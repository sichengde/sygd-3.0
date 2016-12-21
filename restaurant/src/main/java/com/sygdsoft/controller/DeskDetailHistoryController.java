package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskDetailHistory;
import com.sygdsoft.model.DeskDetailHistoryQuery;
import com.sygdsoft.model.PointOfSaleOnly;
import com.sygdsoft.service.DeskDetailHistoryService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-18.
 */
@RestController
public class DeskDetailHistoryController {
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    TimeService timeService;

    @RequestMapping(value = "deskDetailHistoryGet")
    public List<DeskDetailHistory> deskDetailHistoryGet(@RequestBody Query query) throws Exception {
        return deskDetailHistoryService.get(query);
    }

    @RequestMapping(value = "deskDetailHistoryGetByDatePointOfSale")
    public List<DeskDetailHistory> deskDetailHistoryGetByDatePointOfSale(@RequestBody DeskDetailHistoryQuery deskDetailHistoryQuery){
        String firstPointOfSale=deskDetailHistoryQuery.getFirstPointOfSale();
        String secondPointOfSale=deskDetailHistoryQuery.getSecondPointOfSale();
        Date beginTime=deskDetailHistoryQuery.getBeginTime();
        Date endTime=deskDetailHistoryQuery.getEndTime();
        return deskDetailHistoryService.getByDatePointOfSale(beginTime, endTime, firstPointOfSale, secondPointOfSale);
    }

    /**
     * 获得当日的自助餐消费人数
     */
    @RequestMapping(value = "getTodayBuffetPeople")
    public Integer getTodayBuffetPeople(@RequestBody PointOfSaleOnly pointOfSaleOnly) throws Exception {
        String pointOfSale=pointOfSaleOnly.getPointOfSale();
        return deskDetailHistoryService.getNumByDateSaleCount(timeService.getMinTime(new Date()),timeService.getMaxTime(new Date()),pointOfSale, "自助餐");
    }
}
