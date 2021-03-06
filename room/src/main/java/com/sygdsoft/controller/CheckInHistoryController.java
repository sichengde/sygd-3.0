package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.DebtIntegrationMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-08-24.
 */
@RestController
public class CheckInHistoryController {
    @Autowired
    CheckInHistoryService checkInHistoryService;
    @Autowired
    GuestMapCheckInService guestMapCheckInService;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    ReportService reportService;
    @Autowired
    DebtIntegrationMapper debtIntegrationMapper;
    @Autowired
    OtherParamService otherParamService;

    @RequestMapping(value = "checkInHistoryGet")
    public List<CheckInHistory> checkInHistoryGet(@RequestBody Query query) throws Exception {
        List<CheckInHistory> checkInHistoryList = checkInHistoryService.get(query);
        for (CheckInHistory checkInHistory : checkInHistoryList) {
            List<GuestMapCheckIn> guestMapCheckInList = guestMapCheckInService.getByCardId(checkInHistory.getCardId());
            List<CheckInIntegration> checkInIntegrationList = new ArrayList<>();
            for (GuestMapCheckIn guestMapCheckIn : guestMapCheckInList) {
                if(guestMapCheckIn.getSelfAccount()!=null) {
                    checkInIntegrationList.add(checkInIntegrationService.getBySelfAccount(guestMapCheckIn.getSelfAccount()));
                }
            }
            checkInHistory.setCheckInIntegrationList(checkInIntegrationList);
        }
        return checkInHistoryList;
    }

    /**
     * 通过自付账号获取历史宾客
     *
     * @param selfAccount 自付账号
     * @return 历史宾客数组
     */
    @RequestMapping(value = "checkInHistoryGetBySelfAccount")
    public List<CheckInHistory> checkInHistoryGetBySelfAccount(@RequestBody String selfAccount) {
        List<GuestMapCheckIn> guestMapCheckInList = guestMapCheckInService.getBySelfAccount(selfAccount);
        List<CheckInHistory> checkInHistoryList = new ArrayList<>();
        for (GuestMapCheckIn guestMapCheckIn : guestMapCheckInList) {
            checkInHistoryList.add(checkInHistoryService.getByCardId(guestMapCheckIn.getCardId()));
        }
        return checkInHistoryList;
    }

    /**
     * 通过身份证号和房类获取上次开房房价
     */
    @RequestMapping(value = "getHistoryRoomPriceByCardId")
    public CheckInHistoryLog getHistoryRoomPriceByCardId(@RequestBody List<String> param) {
        String cardId = param.get(0);
        String roomCategory = param.get(1);
        List<CheckInHistoryLog> guestMapCheckInList = guestMapCheckInService.getHistoryRoomPriceByCardId(cardId, roomCategory);
        if (guestMapCheckInList.size() > 0) {
            return guestMapCheckInList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据来店历史打印账务明细
     */
    @RequestMapping(value = "printDebtDetailList")
    public int printDebtDetailList(@RequestBody List<CheckInIntegration> checkInIntegrationList)throws Exception{
        DebtIntegration debtIntegrationQuery=new DebtIntegration();
        List<DebtIntegration> debtIntegrationList=new ArrayList<>();
        for (CheckInIntegration checkInIntegration : checkInIntegrationList) {
            debtIntegrationQuery.setSelfAccount(checkInIntegration.getSelfAccount());
            debtIntegrationList.addAll(debtIntegrationMapper.select(debtIntegrationQuery));
        }
        return reportService.generateReport(debtIntegrationList,new String[]{otherParamService.getValueByName("酒店名称")},"printDebtDetailList","pdf" );
    }
}
