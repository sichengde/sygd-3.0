package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.jsonModel.QuerySubReport;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DebtHistoryService;
import com.sygdsoft.service.GuestIntegrationService;
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
    @Autowired
    GuestIntegrationService guestIntegrationService;

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
    @RequestMapping(value = "getDebtHistoryByPayUser")
    public List<DebtHistory> getDebtHistoryByPayUser(@RequestBody ReportJson reportJson){
        String userId=reportJson.getUserId();
        if("".equals(userId)){
            userId=null;
        }
        String currency=reportJson.getCurrency();
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        List<DebtHistory> debtHistoryList=debtHistoryService.getCancelDeposit(userId, currency, beginTime, endTime);
        for (DebtHistory debtHistory : debtHistoryList) {
            debtHistory.setGuestName(guestIntegrationService.getGuestNameBySelfAccount(debtHistory.getSelfAccount()));
        }
        return debtHistoryList;
    }

    /**
     * 根据结账序列号获取押金总和
     */
    @RequestMapping("getTotalDepositByPaySerial")
    public Double getTotalDepositByPaySerial(@RequestBody String paySerial){
        return debtHistoryService.getTotalDeposit(paySerial);
    }
}
