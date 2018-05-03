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
    @Autowired
    GuestSnapshotService guestSnapshotService;
    @Autowired
    GuestSourceService guestSourceService;

    @RequestMapping(value = "huayuanRoomParseReport")
    public List<HuaYuanRoomParseReturn> huayuanRoomParseReport(@RequestBody ReportJson reportJson) throws Exception {
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
        row.setDay((double) (roomSnapshotDay.getSumAllDayRoom() + roomSnapshotDay.getSumNightRoom()));
        row.setMonth((double) (roomSnapshotMonth.getSumAllDayRoom() + roomSnapshotMonth.getSumNightRoom()));
        row.setYear((double) (roomSnapshotYear.getSumAllDayRoom() + roomSnapshotYear.getSumNightRoom()));
        huaYuanRoomParseReturnList.add(row);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("招待房间数");
        row.setDay(Double.valueOf(roomSnapshotDay.getSumFree()));
        row.setMonth(Double.valueOf(roomSnapshotMonth.getSumFree()));
        row.setYear(Double.valueOf(roomSnapshotYear.getSumFree()));
        huaYuanRoomParseReturnList.add(row);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("自用房间数");
        row.setDay(Double.valueOf(roomSnapshotDay.getSumSelf()));
        row.setMonth(Double.valueOf(roomSnapshotMonth.getSumSelf()));
        row.setYear(Double.valueOf(roomSnapshotYear.getSumSelf()));
        huaYuanRoomParseReturnList.add(row);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("维修房间数");
        row.setDay(Double.valueOf(roomSnapshotDay.getSumRepair()));
        row.setMonth(Double.valueOf(roomSnapshotMonth.getSumRepair()));
        row.setYear(Double.valueOf(roomSnapshotYear.getSumRepair()));
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("房间出租率%");
        row.setDay(szMath.formatTwoDecimalReturnDouble((double) (roomSnapshotDay.getSumAllDayRoom() + roomSnapshotDay.getSumNightRoom()),roomSnapshotDay.getSumRealRoom()));
        row.setMonth(szMath.formatTwoDecimalReturnDouble((double) (roomSnapshotMonth.getSumAllDayRoom() + roomSnapshotMonth.getSumNightRoom()),roomSnapshotMonth.getSumRealRoom()));
        row.setYear(szMath.formatTwoDecimalReturnDouble((double) (roomSnapshotYear.getSumAllDayRoom() + roomSnapshotYear.getSumNightRoom()),roomSnapshotYear.getSumRealRoom()));
        huaYuanRoomParseReturnList.add(row);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("平均房价");
        row.setDay(szMath.formatTwoDecimalReturnDouble(roomSnapshotDay.getAllDayRoomConsume()+roomSnapshotDay.getNightRoomConsume(),roomSnapshotDay.getSumAllDayRoom()+roomSnapshotDay.getSumNightRoom()));
        row.setMonth(szMath.formatTwoDecimalReturnDouble(roomSnapshotMonth.getAllDayRoomConsume()+roomSnapshotMonth.getNightRoomConsume(),roomSnapshotMonth.getSumAllDayRoom()+roomSnapshotMonth.getSumNightRoom()));
        row.setYear(szMath.formatTwoDecimalReturnDouble(roomSnapshotYear.getAllDayRoomConsume()+roomSnapshotYear.getNightRoomConsume(),roomSnapshotYear.getSumAllDayRoom()+roomSnapshotYear.getSumNightRoom()));
        huaYuanRoomParseReturnList.add(row);
        /*人数快照*/
        GuestSnapshot guestSnapshotDay=guestSnapshotService.getSum(beginTimeDay, endTimeDay);
        GuestSnapshot guestSnapshotMonth=guestSnapshotService.getSum(beginTimeMonth, endTimeMonth);
        GuestSnapshot guestSnapshotYear=guestSnapshotService.getSum(beginTimeYear, endTimeYear);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("到店客人数");
        row.setDay(Double.valueOf(guestSnapshotDay.getSumCome()));
        row.setMonth(Double.valueOf(guestSnapshotMonth.getSumCome()));
        row.setYear(Double.valueOf(guestSnapshotYear.getSumCome()));
        huaYuanRoomParseReturnList.add(row);
        row=new HuaYuanRoomParseReturn();
        row.setProject("房间状态");
        row.setSubProject("当天客人总数");
        row.setDay(Double.valueOf(guestSnapshotDay.getSumExist()));
        row.setMonth(Double.valueOf(guestSnapshotMonth.getSumExist()));
        row.setYear(Double.valueOf(guestSnapshotYear.getSumExist()));
        huaYuanRoomParseReturnList.add(row);
        /*开始客源分析*/
        Query query=new Query();
        query.setOrderByList(new String[]{"countCategory"});
        List<GuestSource> guestSourceList1=guestSourceService.get(query);
        String lastCountCategory=null;
        for (GuestSource guestSource : guestSourceList1) {
            if(lastCountCategory!=null&&!lastCountCategory.equals(guestSource.getCountCategory())){
                /*生成上一个大类的记录*/
                row=new HuaYuanRoomParseReturn();
                row.setProject("客源分析");
                row.setSubProject(lastCountCategory+"平均房价");
                row.setDay(roomSnapshotService.getConsumeByGuestSource(beginTimeDay,endTimeDay,lastCountCategory));
                row.setMonth(roomSnapshotService.getConsumeByGuestSource(beginTimeMonth,endTimeMonth,lastCountCategory));
                row.setYear(roomSnapshotService.getConsumeByGuestSource(beginTimeYear,endTimeYear,lastCountCategory));
                huaYuanRoomParseReturnList.add(row);
            }
            lastCountCategory=guestSource.getCountCategory();
            row=new HuaYuanRoomParseReturn();
            row.setProject("客源分析");
            row.setSubProject(guestSource.getGuestSource()+"房间数");
            row.setDay(roomSnapshotService.getSumByGuestSource(beginTimeDay,endTimeDay,guestSource.getGuestSource()));
            row.setMonth(roomSnapshotService.getSumByGuestSource(beginTimeMonth,endTimeMonth,guestSource.getGuestSource()));
            row.setYear(roomSnapshotService.getSumByGuestSource(beginTimeYear,endTimeYear,guestSource.getGuestSource()));
            huaYuanRoomParseReturnList.add(row);
        }
        /*生成上一个大类的记录*/
        row=new HuaYuanRoomParseReturn();
        row.setProject("客源分析");
        row.setSubProject(lastCountCategory+"平均房价");
        row.setDay(roomSnapshotService.getConsumeByGuestSource(beginTimeDay,endTimeDay,lastCountCategory));
        row.setMonth(roomSnapshotService.getConsumeByGuestSource(beginTimeMonth,endTimeMonth,lastCountCategory));
        row.setYear(roomSnapshotService.getConsumeByGuestSource(beginTimeYear,endTimeYear,lastCountCategory));
        huaYuanRoomParseReturnList.add(row);
        /*会议室收入*/
        row=new HuaYuanRoomParseReturn();
        row.setProject("会议室收入");
        row.setSubProject("会议室总数");
        row.setDay((double) roomSnapshotService.getNotRoomCount(beginTimeDay,endTimeDay));
        row.setMonth((double) roomSnapshotService.getNotRoomCount(beginTimeMonth,endTimeMonth));
        row.setYear((double) roomSnapshotService.getNotRoomCount(beginTimeYear,endTimeYear));
        huaYuanRoomParseReturnList.add(row);
        /*会议室收入*/
        row=new HuaYuanRoomParseReturn();
        row.setProject("会议室收入");
        row.setSubProject("可出租会议室数");
        row.setDay((double) roomSnapshotService.getNotRoomAvailableCount(beginTimeDay,endTimeDay));
        row.setMonth((double) roomSnapshotService.getNotRoomAvailableCount(beginTimeMonth,endTimeMonth));
        row.setYear((double) roomSnapshotService.getNotRoomAvailableCount(beginTimeYear,endTimeYear));
        huaYuanRoomParseReturnList.add(row);
        /*统计其他收入合计*/
        row=new HuaYuanRoomParseReturn();
        row.setSubProject("其他收入合计");
        huaYuanRoomParseReturnList.add(row);
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
