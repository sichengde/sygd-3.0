package com.sygdsoft.controller.room;

import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.model.GuestSource;
import com.sygdsoft.model.RoomStateReport;
import com.sygdsoft.model.room.RoomParseReportQuery;
import com.sygdsoft.model.room.RoomParseReportRow;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.table.TableStringConverter;
import java.text.ParseException;
import java.util.*;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

/**
 * Created by 舒展 on 2017-02-08.
 * 客房经营分析
 */
@RestController
public class RoomParseReportController {
    @Autowired
    TimeService timeService;
    @Autowired
    RoomStateReportService roomStateReportService;
    @Autowired
    SzMath szMath;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    GuestIntegrationService guestIntegrationService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    GroupIntegrationService groupIntegrationService;
    @Autowired
    GuestSourceService guestSourceService;
    @Autowired
    OtherParamService otherParamService;

    private Integer rent1;//出租率分子
    private Integer rent2;//出租率分母
    private Double price1;//平均房价分子
    private Integer price2;//平均房价分母
    private Double revper1;//REVPER分子
    private Integer revper2;//REVPER分母

    @RequestMapping("roomParseReport")
    public List<RoomParseReportRow> RoomParseReport(@RequestBody RoomParseReportQuery roomParseReportQuery) throws Exception {
        Date date = roomParseReportQuery.getDate();
        if(date!=null) {
            Date beginTime;
            Date endTime;
            List<RoomParseReportRow> roomParseReportRowList = new ArrayList<>();
            try {
                timeService.setAdjustDay(Integer.valueOf(otherParamService.getValueByName("会计月度")) - 1);
            } catch (NumberFormatException e) {
                throw new Exception("会计月度定义有误");
            }
            switch (roomParseReportQuery.getRange()) {
                case "月":
                /*分为3个10天*/
                    beginTime = timeService.getMinMonth(date);
                    beginTime = timeService.adjustDay(beginTime);
                    endTime = timeService.addDay(beginTime, 10);
                    this.clearSubmit();
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                    beginTime = endTime;
                    endTime = timeService.addDay(beginTime, 10);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                    beginTime = endTime;
                    endTime = timeService.getMaxMonth(endTime);
                    endTime = timeService.adjustDay(endTime);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                    this.parseSubmit(roomParseReportRowList);
                    break;
                case "季":
                /*分为3个月，每个月3个10天*/
                    beginTime = timeService.getMinQuarter(date);
                    beginTime = timeService.adjustDay(beginTime);
                    endTime = timeService.addDay(beginTime, 10);
                    this.clearSubmit();
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                    beginTime = endTime;
                    endTime = timeService.addDay(beginTime, 10);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                    beginTime = endTime;
                    endTime = timeService.getMaxMonth(endTime);
                    endTime = timeService.adjustDay(endTime);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                    this.parseSubmit(roomParseReportRowList);

                    beginTime = endTime;
                    beginTime = timeService.adjustDay(beginTime);
                    endTime = timeService.addDay(beginTime, 10);
                    this.clearSubmit();
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                    beginTime = endTime;
                    endTime = timeService.addDay(beginTime, 10);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                    beginTime = endTime;
                    endTime = timeService.getMaxMonth(endTime);
                    endTime = timeService.adjustDay(endTime);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                    this.parseSubmit(roomParseReportRowList);

                    beginTime = endTime;
                    beginTime = timeService.adjustDay(beginTime);
                    endTime = timeService.addDay(beginTime, 10);
                    this.clearSubmit();
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                    beginTime = endTime;
                    endTime = timeService.addDay(beginTime, 10);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                    beginTime = endTime;
                    endTime = timeService.adjustDay(endTime);
                    endTime = timeService.getMaxMonth(endTime);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                    this.parseSubmit(roomParseReportRowList);
                    break;
                case "年":
                /*分为4个季度，每个季度3个月*/
                    beginTime = timeService.getMinYear(date);
                    beginTime = timeService.adjustDay(beginTime);
                    endTime = timeService.addMonth(beginTime, 1);
                    this.clearSubmit();
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime));
                    for (int i = 0; i < 11; i++) {
                        beginTime = endTime;
                        endTime = timeService.addMonth(beginTime, 1);
                        this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime));
                        if (i % 3 == 1) {
                            this.parseSubmit(roomParseReportRowList);
                            this.clearSubmit();
                        }
                    }
                    break;
            }
            return roomParseReportRowList;
        }else {
            Date beginTime=roomParseReportQuery.getBeginTime();
            Date endTime=timeService.getMaxTime(roomParseReportQuery.getEndTime());
            List<RoomParseReportRow> roomParseReportRowList = new ArrayList<>();
            this.clearSubmit();
            this.parseData(beginTime, endTime, roomParseReportRowList, timeService.dateToStringShort(beginTime)+"至"+timeService.dateToStringShort(endTime));
            return roomParseReportRowList;
        }
    }

    private void parseData(Date beginTime, Date endTime, List<RoomParseReportRow> roomParseReportRowList, String monthTitle) throws Exception {
        RoomParseReportRow roomParseReportRow = new RoomParseReportRow();
        roomParseReportRow.setMonth(monthTitle);//设置左边第一栏
        RoomStateReport roomStateReport = roomStateReportService.getSumByDate(beginTime, endTime);
        if (roomStateReport != null) {
            Double allDayRoomConsume=szMath.nullToZero(roomStateReport.getAllDayRoomConsume());
            Double nightRoomConsume=szMath.nullToZero(roomStateReport.getNightRoomConsume());
            Integer allDayRoom=szMath.nullToZero(roomStateReport.getAllDayRoom());
            Integer nightRoom=szMath.nullToZero(roomStateReport.getNightRoom());
            this.rent1+=szMath.nullToZero(roomStateReport.getRent());
            this.rent2+=szMath.nullToZero(roomStateReport.getTotal());
            this.price1+=szMath.nullToZero(allDayRoomConsume+nightRoomConsume);
            this.price2+=szMath.nullToZero(nightRoom+allDayRoom);
            this.revper1+=szMath.nullToZero(roomStateReport.getAllDayRoomConsume());
            this.revper2+=szMath.nullToZero(roomStateReport.getTotalReal());
            roomParseReportRow.setAverageRent(szMath.formatTwoDecimal(roomStateReport.getRent(), roomStateReport.getTotal()));//设置住客率
            roomParseReportRow.setAveragePrice(szMath.formatTwoDecimal(allDayRoomConsume+nightRoomConsume, nightRoom+allDayRoom));//设置平均房价
            roomParseReportRow.setRevper(szMath.formatTwoDecimal(roomStateReport.getAllDayRoomConsume(), roomStateReport.getTotalReal()));//设置REVPER

        }
        List<String> titleList=new ArrayList<>();
        List<String> titleValueList=new ArrayList<>();
        /*接待总人数*/
        titleList.add("总人次");
        titleValueList.add(String.valueOf(szMath.nullToZero(guestIntegrationService.getSumNumByDate(beginTime, endTime,null))));//接待总人数
        /*按客源分析*/
        List<GuestSource> guestSourceList=guestSourceService.get(null);
        for (GuestSource guestSource : guestSourceList) {
            titleList.add(guestSource.getGuestSource()+"人数");
            titleValueList.add(String.valueOf(szMath.nullToZero(guestIntegrationService.getSumNumByDate(beginTime, endTime,guestSource.getGuestSource()))));//接待总人数
        }
        roomParseReportRow.setGroupNum(szMath.nullToZero(groupIntegrationService.getSumByDate(beginTime, endTime)));//团队接待数量
        roomParseReportRow.setForeigner(szMath.nullToZero(guestIntegrationService.getSumForeignerNumByDate(beginTime, endTime)));//外宾接待数量
        /*分析客源房费*/
        List<DebtIntegration> debtIntegrationList = debtIntegrationService.getSumRoomConsumeByDateGuestSource(beginTime, endTime);
        Integer index = 0;
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            if(debtIntegration==null){
                continue;
            }
            if (debtIntegration.getGuestSource() == null) {
                continue;
            }
            titleValueList.add(String.valueOf(szMath.nullToZero(debtIntegration.getConsume())));
            titleList.add(debtIntegration.getGuestSource()+"房费");
        }
        /*分析二级营业部门销售情况*/
        debtIntegrationList = debtIntegrationService.getSumConsumeByDatePointOfSale(beginTime, endTime);
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            if (debtIntegration.getPointOfSale() == null) {
                titleValueList.add(String.valueOf(szMath.nullToZero(debtIntegration.getConsume())));
                titleList.add("未定义");
                continue;
            }
            if (debtIntegration.getPointOfSale().equals("房费")) {
                continue;
            }
            titleValueList.add(String.valueOf(szMath.nullToZero(debtIntegration.getConsume())));
            titleList.add(debtIntegration.getPointOfSale());
            titleValueList.add(String.valueOf(szMath.nullToZero(debtIntegration.getCount())));
            titleList.add(debtIntegration.getPointOfSale() + "次数");
        }
        roomParseReportRow.setTitleValueList(titleValueList);
        roomParseReportRow.setTitleList(titleList);
        roomParseReportRowList.add(roomParseReportRow);
    }

    private void clearSubmit() {
        this.rent1 = 0;
        this.rent2 = 0;
        this.price1 = 0.0;
        this.price2 = 0;
        this.revper1 = 0.0;
        this.revper2 = 0;
    }

    private void parseSubmit(List<RoomParseReportRow> roomParseReportRowList) {
        RoomParseReportRow roomParseReportRow = new RoomParseReportRow();
        roomParseReportRow.setMonth("小计");//设置左边第一栏
        roomParseReportRow.setAverageRent(szMath.formatTwoDecimal(this.rent1, this.rent2));//设置住客率
        roomParseReportRow.setAveragePrice(szMath.formatTwoDecimal(this.price1, this.price2));//设置平均房价
        roomParseReportRow.setRevper(szMath.formatTwoDecimal(this.revper1, this.revper2));//设置REVPER
        roomParseReportRowList.add(roomParseReportRow);
    }

}
