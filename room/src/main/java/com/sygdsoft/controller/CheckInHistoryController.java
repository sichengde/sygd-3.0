package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.model.CheckInIntegration;
import com.sygdsoft.model.GuestMapCheckIn;
import com.sygdsoft.service.CheckInHistoryLogService;
import com.sygdsoft.service.CheckInHistoryService;
import com.sygdsoft.service.CheckInIntegrationService;
import com.sygdsoft.service.GuestMapCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value = "checkInHistoryGet")
    public List<CheckInHistory> checkInHistoryGet(@RequestBody Query query) throws Exception {
        List<CheckInHistory> checkInHistoryList = checkInHistoryService.get(query);
        for (CheckInHistory checkInHistory : checkInHistoryList) {
            List<GuestMapCheckIn> guestMapCheckInList = guestMapCheckInService.getByCardId(checkInHistory.getCardId());
            List<CheckInIntegration> checkInIntegrationList = new ArrayList<>();
            for (GuestMapCheckIn guestMapCheckIn : guestMapCheckInList) {
                checkInIntegrationList.add(checkInIntegrationService.getBySelfAccount(guestMapCheckIn.getSelfAccount()));
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
        //List<GuestMapCheckIn> guestMapCheckInList=guestMapCheckInService.getByCardId(cardId);
        //CheckInHistoryLog checkInHistoryLog=checkInHistoryLogService.getOneBySelfAccount()
        List<CheckInHistoryLog> guestMapCheckInList = guestMapCheckInService.getHistoryRoomPriceByCardId(cardId, roomCategory);
        if (guestMapCheckInList.size() > 0) {
            return guestMapCheckInList.get(0);
        } else {
            return null;
        }
    }
}
