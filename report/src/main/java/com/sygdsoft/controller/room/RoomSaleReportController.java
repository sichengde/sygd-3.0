package com.sygdsoft.controller.room;

import com.sygdsoft.model.OneDateQuery;
import com.sygdsoft.model.RoomStateReport;
import com.sygdsoft.model.room.RoomSaleReportRow;
import com.sygdsoft.service.OtherParamService;
import com.sygdsoft.service.RoomStateReportService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14 0014.
 * 客房销售统计
 */
@RestController
public class RoomSaleReportController {
    @Autowired
    TimeService timeService;
    @Autowired
    RoomStateReportService roomStateReportService;
    @Autowired
    SzMath szMath;
    @Autowired
    OtherParamService otherParamService;
    @RequestMapping("roomSaleReport")
    public List<RoomSaleReportRow> roomSaleReport(@RequestBody OneDateQuery oneDateQuery) throws Exception {
        Date date=oneDateQuery.getDate();
        Date beginTime=timeService.getMinTime(date);
        Date endTime=timeService.getMaxTime(date);
        try {
            timeService.setAdjustDay(Integer.valueOf(otherParamService.getValueByName("会计月度"))-1);
        } catch (NumberFormatException e) {
            throw new Exception("会计月度定义有误");
        }
        RoomStateReport roomStateReportToday = roomStateReportService.getSumByDate(beginTime, endTime);
        beginTime=timeService.adjustDay(timeService.getMinMonth(date));
        endTime=timeService.adjustDay(timeService.getMaxMonth(date));
        RoomStateReport roomStateReportMonth = roomStateReportService.getSumByDate(beginTime, endTime);
        beginTime=timeService.adjustDay(timeService.getMinYear(date));
        endTime=timeService.adjustDay(timeService.getMaxYear(date));
        RoomStateReport roomStateReportYear = roomStateReportService.getSumByDate(beginTime, endTime);
        List<RoomSaleReportRow> roomSaleReportRowList=new ArrayList<>();
        RoomSaleReportRow roomSaleReportRow;
        if(roomStateReportToday!=null){
            roomSaleReportRow=new RoomSaleReportRow();
            roomSaleReportRow.setCategory("客房总数");
            roomSaleReportRow.setToday(szMath.ifNotNullGetString(roomStateReportToday.getTotal()));
            roomSaleReportRow.setMonth(szMath.ifNotNullGetString(roomStateReportMonth.getTotal()));
            roomSaleReportRow.setYear(szMath.ifNotNullGetString(roomStateReportYear.getTotal()));
            roomSaleReportRowList.add(roomSaleReportRow);
            roomSaleReportRow=new RoomSaleReportRow();
            roomSaleReportRow.setCategory("自用");
            roomSaleReportRow.setToday(szMath.ifNotNullGetString(roomStateReportToday.getSelf()));
            roomSaleReportRow.setMonth(szMath.ifNotNullGetString(roomStateReportMonth.getSelf()));
            roomSaleReportRow.setYear(szMath.ifNotNullGetString(roomStateReportYear.getSelf()));
            roomSaleReportRowList.add(roomSaleReportRow);
            roomSaleReportRow=new RoomSaleReportRow();
            roomSaleReportRow.setCategory("备用");
            roomSaleReportRow.setToday(szMath.ifNotNullGetString(roomStateReportToday.getBackUp()));
            roomSaleReportRow.setMonth(szMath.ifNotNullGetString(roomStateReportMonth.getBackUp()));
            roomSaleReportRow.setYear(szMath.ifNotNullGetString(roomStateReportYear.getBackUp()));
            roomSaleReportRowList.add(roomSaleReportRow);
            roomSaleReportRow=new RoomSaleReportRow();
            roomSaleReportRow.setCategory("维修");
            roomSaleReportRow.setToday(szMath.ifNotNullGetString(roomStateReportToday.getRepair()));
            roomSaleReportRow.setMonth(szMath.ifNotNullGetString(roomStateReportMonth.getRepair()));
            roomSaleReportRow.setYear(szMath.ifNotNullGetString(roomStateReportYear.getRepair()));
            roomSaleReportRowList.add(roomSaleReportRow);
            roomSaleReportRow=new RoomSaleReportRow();
            roomSaleReportRow.setCategory("出租");
            roomSaleReportRow.setToday(szMath.ifNotNullGetString(roomStateReportToday.getRent()));
            roomSaleReportRow.setMonth(szMath.ifNotNullGetString(roomStateReportMonth.getRent()));
            roomSaleReportRow.setYear(szMath.ifNotNullGetString(roomStateReportYear.getRent()));
            roomSaleReportRowList.add(roomSaleReportRow);
            roomSaleReportRow=new RoomSaleReportRow();
            roomSaleReportRow.setCategory("出租");
            roomSaleReportRow.setToday(szMath.ifNotNullGetString(roomStateReportToday.getRent()));
            roomSaleReportRow.setMonth(szMath.ifNotNullGetString(roomStateReportMonth.getRent()));
            roomSaleReportRow.setYear(szMath.ifNotNullGetString(roomStateReportYear.getRent()));
            roomSaleReportRowList.add(roomSaleReportRow);
        }else {
            throw new Exception("该日期没有数据");
        }
    }

    private void parseData(Date beginTime,Date endTime){

    }
}
