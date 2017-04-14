package com.sygdsoft.controller.room;

import com.sygdsoft.jsonModel.GuestParseRow;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    @Autowired
    GuestSourceService guestSourceService;
    @Autowired
    RoomCategoryService roomCategoryService;

    @RequestMapping(value = "guestSourceParseReport")
    public List<GuestParseRow> guestSourceParseReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = timeService.getMaxTime(reportJson.getEndTime());
        List<GuestSource> guestSourceList = guestSourceService.get(null);
        List<RoomCategory> roomCategoryList = roomCategoryService.get(null);
        List<GuestParseRow> guestParseRowList = new ArrayList<>();
        for (GuestSource guestSource : guestSourceList) {
            GuestParseRow guestParseRow = new GuestParseRow();
            guestParseRow.setGuestSource(guestSource.getGuestSource());
            guestParseRow.setGuestNum(guestIntegrationService.getSumNumByDate(beginTime, endTime, guestSource.getGuestSource()));//消费人数
            List<String> titleList = new ArrayList<>();
            List<String> titleValueList = new ArrayList<>();
            for (RoomCategory roomCategory : roomCategoryList) {
                /*按房类统计开房数*/
                titleList.add(roomCategory.getCategory() + "房数");
                titleValueList.add(String.valueOf(checkInIntegrationService.getSumCount(beginTime, endTime, guestSource.getGuestSource(), roomCategory.getCategory())));
                /*按房类统计消费*/
                titleList.add(roomCategory.getCategory() + "消费");
                titleValueList.add(String.valueOf(checkInIntegrationService.getSumConsume(beginTime, endTime, guestSource.getGuestSource(), roomCategory.getCategory())));
            }
            guestParseRow.setTitleList(titleList);
            guestParseRow.setTitleValueList(titleValueList);
            guestParseRowList.add(guestParseRow);
        }
        return guestParseRowList;
    }
}
