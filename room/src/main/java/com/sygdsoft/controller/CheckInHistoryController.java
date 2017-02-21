package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.model.GuestMapCheckIn;
import com.sygdsoft.service.CheckInHistoryService;
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

    @RequestMapping(value = "checkInHistoryGet")
    public List<CheckInHistory> checkInHistoryGet(@RequestBody Query query) throws Exception {
        return checkInHistoryService.get(query);
    }

    @RequestMapping(value = "checkInHistoryGetBySelfAccount")
    public List<CheckInHistory> checkInHistoryGetBySelfAccount(@RequestBody String selfAccount){
        List<GuestMapCheckIn> guestMapCheckInList=guestMapCheckInService.getBySelfAccount(selfAccount);
        List<CheckInHistory> checkInHistoryList=new ArrayList<>();
        for (GuestMapCheckIn guestMapCheckIn : guestMapCheckInList) {
            checkInHistoryList.add(checkInHistoryService.getByCardId(guestMapCheckIn.getCardId()));
        }
        return checkInHistoryList;
    }
}
