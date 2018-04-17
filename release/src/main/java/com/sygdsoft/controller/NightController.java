package com.sygdsoft.controller;

import com.sygdsoft.conf.dataSource.DynamicDataSourceContextHolder;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.CheckInMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-01.
 */
@RestController
public class NightController {
    @Autowired
    Night night;
    @Autowired
    DebtService debtService;
    @Autowired
    CheckInMapper checkInMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    BookService bookService;
    @Autowired
    BookHistoryService bookHistoryService;
    @Autowired
    RoomService roomService;
    @Autowired
    SerialService serialService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    DeskBookService deskBookService;
    @Autowired
    DeskBookHistoryService deskBookHistoryService;
    @Autowired
    RoomPriceService roomPriceService;
    @Autowired
    Util util;
    @Autowired
    ReportService reportService;
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "manualNightAction")
    @Transactional(rollbackFor = Exception.class)
    public void manualNightAction() throws Exception {
        night.manualNightAction();
        userLogService.addUserLog("手动夜审", userLogService.reception, userLogService.night, null);
    }

    @RequestMapping(value = "checkDebtDate")
    @Transactional(rollbackFor = Exception.class)
    public String checkDebtDate() throws Exception {
        timeService.setNow();
        String result = null;
        String debtDateString = otherParamService.getValueByName(otherParamService.debtDate);
        String todayString = timeService.getNowShort();
        Date today = timeService.getNow();
        if (debtDateString == null) {
            result = "没有设置账务日期,系统默认设置为今天:" + todayString;
            otherParamService.insertNewRow(otherParamService.debtDate, todayString, "公共");
        } else {
            Date debtDate = timeService.stringToDateShort(debtDateString);
            /*若小于一天，则视为时间出错*/
            if (today.getTime() - debtDate.getTime() > 2 * 24 * 60 * 60 * 1000 || debtDate.getTime() > today.getTime()) {
                otherParamService.updateValueByName(otherParamService.debtDate, todayString);
                result = "账务日期不准确,原日期:" + debtDateString + ",现改为:" + todayString + ",如果不对,请联系售后";
            }
        }
        return result;
    }
}
