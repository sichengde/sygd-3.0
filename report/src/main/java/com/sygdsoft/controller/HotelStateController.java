package com.sygdsoft.controller;

import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.service.DebtHistoryService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-07-08.
 * 酒店经营状况控制器
 */
@RestController
public class HotelStateController {
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    TimeService timeService;

    /**
     * 查询一段时间内的当日发生额
     * 1.起始时间
     * 2.终止时间
     */
    @RequestMapping(value = "consumePerDay")
    public List<DebtHistory> consumePerDay(@RequestBody List<Date> time) throws Exception {
        Date beginTime=time.get(0);
        Date endTime=time.get(1);
        return debtHistoryService.getConsumePerDay(beginTime, endTime);
    }
}
