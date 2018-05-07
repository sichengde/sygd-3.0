package com.sygdsoft.controller.especially;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.SqlMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class HuaYuanEatParseController {
    @Autowired
    HuaYuanService huaYuanService;
    @Autowired
    TimeService timeService;
    @Autowired
    SqlMapper sqlMapper;
    @Autowired
    SzMath szMath;
    @Autowired
    DeskGuestSourceService deskGuestSourceService;

    @RequestMapping(value = "huayuanRoomParseReport")
    public List<HuaYuanRoomParseReturn> huayuanRoomParseReport(@RequestBody ReportJson reportJson) throws Exception {
        Date date = reportJson.getBeginTime();
        Date beginTimeDay = timeService.getMinTime(date);
        Date endTimeDay = timeService.getMaxTime(date);
        Date beginTimeMonth = timeService.getMinMonth(date);
        Date endTimeMonth = timeService.getMaxMonth(date);
        Date beginTimeYear = timeService.getMinYear(date);
        Date endTimeYear = timeService.getMaxYear(date);
        List<HuaYuanRoomParseReturn> huaYuanRoomParseReturnList = new ArrayList<>();
        /*分析客源消费*/
        HuaYuanRoomParseReturn row;
        List<DeskGuestSource> deskGuestSourceList=deskGuestSourceService.get(null);
        for (DeskGuestSource deskGuestSource : deskGuestSourceList) {
            if (deskGuestSource.getName() == null) {
                continue;
            }
            row = new HuaYuanRoomParseReturn();
            row.setProject("房间收入明细");
            row.setSubProject(deskGuestSource.getName() + "收入");
            //row.setDay();
            //row.setMonth();
            //row.setYear();
            huaYuanRoomParseReturnList.add(row);
        }
        return huaYuanRoomParseReturnList;
    }
}
