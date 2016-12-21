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
        userLogService.addUserLog("手动夜审", userLogService.reception, userLogService.night,null);
    }

    /**
     * 阿里云服务器端的功能，进行该用户在云端数据库的夜审
     */
    @RequestMapping(value = "manualNightCloud")
    public void manualNightCloud(String hotelId) throws Exception {
        if (DynamicDataSourceContextHolder.containsDataSource(hotelId)) {
            DynamicDataSourceContextHolder.setDataSourceType(hotelId);
            night.manualNightAction();
        }
    }
}
