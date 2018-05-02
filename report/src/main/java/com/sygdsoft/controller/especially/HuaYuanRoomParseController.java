package com.sygdsoft.controller.especially;

import com.sygdsoft.mapper.SqlMapper;
import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.model.GuestSource;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.RoomSnapshot;
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
public class HuaYuanRoomParseController {
    @Autowired
    HuaYuanService huaYuanService;
    @Autowired
    TimeService timeService;
    @Autowired
    SqlMapper sqlMapper;
    @Autowired
    RoomShopDetailService roomShopDetailService;
    @Autowired
    RoomSnapshotService roomSnapshotService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "huayuanRoomParseReport")
    public List<HuaYuanRoomParseReturn> huayuanRoomParseReport(@RequestBody ReportJson reportJson) throws ParseException {
        Date date=reportJson.getBeginTime();
        Date beginTimeDay=timeService.getMinTime(date);
        Date endTimeDay=timeService.getMaxTime(date);
        Date beginTimeMonth=timeService.getMinMonth(date);
        Date endTimeMonth=timeService.getMaxMonth(date);
        Date beginTimeYear=timeService.getMinYear(date);
        Date endTimeYear=timeService.getMaxYear(date);
        List<HuaYuanRoomParseReturn> huaYuanRoomParseReturnList=new ArrayList<>();
        /*先分析客源*/
        List<String> guestSourceList=sqlMapper.getStringList("SELECT DISTINCT count_category from guest_source;");
        guestSourceList.add("未定义");
        Double totalDay=0.0;
        Double totalMonth=0.0;
        Double totalYear=0.0;
        HuaYuanRoomParseReturn row;
        for (String guestSource : guestSourceList) {
            if (guestSource==null){
                continue;
            }
            row=new HuaYuanRoomParseReturn();
            row.setProject("房间收入明细");
            row.setSubProject(guestSource+"收入");
            row.setDay(huaYuanService.getGuestSourceConsume(beginTimeDay,endTimeDay,guestSource));
            totalDay+=row.getDay();
            row.setMonth(huaYuanService.getGuestSourceConsume(beginTimeMonth,endTimeMonth,guestSource));
            totalMonth+=row.getMonth();
            row.setYear(huaYuanService.getGuestSourceConsume(beginTimeYear,endTimeYear,guestSource));
            totalYear+=row.getYear();
            huaYuanRoomParseReturnList.add(row);
        }
        //合计
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间收入明细");
        row.setSubProject("客房收入合计=散客+网络+协议+会员+会议房");
        row.setDay(totalDay);
        row.setMonth(totalMonth);
        row.setYear(totalYear);
        huaYuanRoomParseReturnList.add(0,row);
        row=new HuaYuanRoomParseReturn();
        row.setSubProject("其他收入合计");
        huaYuanRoomParseReturnList.add(row);
        /*开始统计房数*/
        RoomSnapshot roomSnapshotDay=roomSnapshotService.getSumByDate(beginTimeDay, endTimeDay,null);
        RoomSnapshot roomSnapshotMonth=roomSnapshotService.getSumByDate(beginTimeMonth, endTimeMonth,null);
        RoomSnapshot roomSnapshotYear=roomSnapshotService.getSumByDate(beginTimeYear, endTimeYear,null);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("房间总数");
        row.setDay(Double.valueOf(roomSnapshotDay.getSumRealRoom()));
        row.setMonth(Double.valueOf(roomSnapshotMonth.getSumRealRoom()));
        row.setYear(Double.valueOf(roomSnapshotYear.getSumRealRoom()));
        huaYuanRoomParseReturnList.add(row);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("可出租房间数");
        row.setDay(Double.valueOf(roomSnapshotDay.getSumAvailable()));
        row.setMonth(Double.valueOf(roomSnapshotMonth.getSumAvailable()));
        row.setYear(Double.valueOf(roomSnapshotYear.getSumAvailable()));
        huaYuanRoomParseReturnList.add(row);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("已出租房间数");
        row.setDay(Double.valueOf(roomSnapshotDay.getSumRent()));
        row.setMonth(Double.valueOf(roomSnapshotMonth.getSumRent()));
        row.setYear(Double.valueOf(roomSnapshotYear.getSumRent()));
        huaYuanRoomParseReturnList.add(row);
        /*统计其他收入合计*/
        return huaYuanRoomParseReturnList;
    }

    static class HuaYuanRoomParseReturn{
        private String project;
        private String subProject;
        private Double day;
        private Double month;
        private Double year;

        public HuaYuanRoomParseReturn() {
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getSubProject() {
            return subProject;
        }

        public void setSubProject(String subProject) {
            this.subProject = subProject;
        }

        public Double getDay() {
            return day;
        }

        public void setDay(Double day) {
            this.day = day;
        }

        public Double getMonth() {
            return month;
        }

        public void setMonth(Double month) {
            this.month = month;
        }

        public Double getYear() {
            return year;
        }

        public void setYear(Double year) {
            this.year = year;
        }
    }
}
