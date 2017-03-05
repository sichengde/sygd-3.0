package com.sygdsoft.controller;

import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.model.GuestParse;
import com.sygdsoft.model.GuestRoomCategoryParse;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/26 0026.
 * 客源分析
 */
@RestController
public class GuestParseController {
    @Autowired
    TimeService timeService;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    GuestIntegrationService guestIntegrationService;

    @RequestMapping(value = "guestSourceParseReport")
    public GuestParse guestSourceParseReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        Date beginTimeHistory = timeService.addYear(beginTime, -1);
        Date endTimeHistory = timeService.addYear(endTime, -1);
        GuestParse guestParse = new GuestParse();
        guestParse.setGuestParseRowList(checkInHistoryLogService.guestSourceParse(beginTime, endTime));
        guestParse.setGuestParseRowListHistory(checkInHistoryLogService.guestSourceParse(beginTimeHistory, endTimeHistory));
        String remark="本地客人:"+guestIntegrationService.getLocalGuestSum(beginTime, endTime)+",外地客人:"+guestIntegrationService.getOtherGuestSum(beginTime, endTime);
        guestParse.setRemark(remark);
        return guestParse;
    }

    @RequestMapping(value = "guestSourceRoomCategoryParseReport")
    public GuestRoomCategoryParse guestSourceRoomCategoryParseReport(@RequestBody ReportJson reportJson) {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        GuestRoomCategoryParse guestRoomCategoryParse=new GuestRoomCategoryParse();
        guestRoomCategoryParse.setReportJson(reportJson);
        guestRoomCategoryParse.setGuestSourceRoomCategoryRowList(checkInIntegrationService.guestSourceRoomCategoryParse(beginTime, endTime));
        return guestRoomCategoryParse;
    }


}
