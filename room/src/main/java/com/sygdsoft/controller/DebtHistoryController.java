package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.jsonModel.QuerySubReport;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DebtHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
@RestController
public class DebtHistoryController {
    @Autowired
    DebtHistoryService debtHistoryService;

    @RequestMapping(value = "debtHistoryGet")
    public List<DebtHistory> debtHistoryGet(@RequestBody Query query) throws Exception {
        return debtHistoryService.get(query);
    }

    @RequestMapping(value = "debtHistoryGetByCheckOutSerial")
    public List<DebtHistory> debtHistoryGetByCheckOutSerial(@RequestBody OnlyString string){
        String checkOutSerial=string.getData();
        return debtHistoryService.debtHistoryGetByCheckOutSerial(checkOutSerial);
    }

    /**
     * 根结账表联表查询押金，带操作员
     */
    @RequestMapping(value = "getByPayUser")
    public List<DebtHistory> getByPayUser(@RequestBody ReportJson reportJson){
        String userId=reportJson.getUserId();
        String currency=reportJson.getCurrency();
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        return debtHistoryService.getCancelDeposit(userId, currency, beginTime, endTime);
    }
}
