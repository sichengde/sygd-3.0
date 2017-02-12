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

/**
 * Created by 舒展 on 2017-02-08.
 * 客房经营状况
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
    CheckInHistoryService checkInHistoryService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    GroupIntegrationService groupIntegrationService;
    @Autowired
    GuestSourceService guestSourceService;
    private Integer rentSubmit;
    private Integer totalSubmit;
    private Double consumeSubmit;

    @RequestMapping("RoomParseReport")
    public List<RoomParseReportRow> RoomParseReport(@RequestBody RoomParseReportQuery roomParseReportQuery) throws Exception {
        Date date = roomParseReportQuery.getDate();
        Date beginTime;
        Date endTime;
        List<RoomParseReportRow> roomParseReportRowList = new ArrayList<>();
        switch (roomParseReportQuery.getRange()) {
            case "月":
                /*分为3个10天*/
                beginTime = timeService.getMinMonth(date);
                endTime = timeService.addDay(beginTime, 10);
                this.clearSubmit();
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                beginTime = endTime;
                endTime = timeService.addDay(beginTime, 10);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                beginTime = endTime;
                endTime = timeService.getMaxMonth(endTime);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                this.parseSubmit(roomParseReportRowList);
                break;
            case "季":
                /*分为3个月，每个月3个10天*/
                beginTime = timeService.getMinQuarter(date);
                endTime = timeService.addDay(beginTime, 10);
                this.clearSubmit();
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                beginTime = endTime;
                endTime = timeService.addDay(beginTime, 10);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                beginTime = endTime;
                endTime = timeService.getMaxMonth(endTime);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                this.parseSubmit(roomParseReportRowList);
                beginTime = endTime;
                endTime = timeService.addDay(beginTime, 10);
                this.clearSubmit();
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                beginTime = endTime;
                endTime = timeService.addDay(beginTime, 10);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                beginTime = endTime;
                endTime = timeService.getMaxMonth(endTime);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                this.parseSubmit(roomParseReportRowList);
                beginTime = endTime;
                endTime = timeService.addDay(beginTime, 10);
                this.clearSubmit();
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "上旬");
                beginTime = endTime;
                endTime = timeService.addDay(beginTime, 10);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "中旬");
                beginTime = endTime;
                endTime = timeService.getMaxMonth(endTime);
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime) + "下旬");
                this.parseSubmit(roomParseReportRowList);
                break;
            case "年":
                /*分为4个季度，每个季度3个月*/
                beginTime = timeService.getMinYear(date);
                endTime = timeService.addMonth(beginTime, 1);
                this.clearSubmit();
                this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime));
                for (int i = 0; i < 11; i++) {
                    beginTime = endTime;
                    endTime = timeService.addMonth(beginTime, 1);
                    this.parseData(beginTime, endTime, roomParseReportRowList, timeService.getMonthCN(beginTime));
                    if(i%3==1){
                        this.parseSubmit(roomParseReportRowList);
                        this.clearSubmit();
                    }
                }
                break;
        }
        return roomParseReportRowList;
    }

    private void parseData(Date beginTime, Date endTime, List<RoomParseReportRow> roomParseReportRowList, String monthTitle) throws Exception {
        RoomParseReportRow roomParseReportRow = new RoomParseReportRow();
        roomParseReportRow.setMonth(monthTitle);//设置左边第一栏
        RoomStateReport roomStateReport = roomStateReportService.getSumByDate(beginTime, endTime);
        if (roomStateReport != null) {
            roomParseReportRow.setAverageRent(szMath.formatTwoDecimal(roomStateReport.getRent(), roomStateReport.getTotal()));//设置住客率
            Double allDayConsume = szMath.nullToZero(debtIntegrationService.getSumAllDayConsumeByDate(beginTime, endTime));
            roomParseReportRow.setAveragePrice(szMath.formatTwoDecimal(allDayConsume, roomStateReport.getRent()));//设置平均房价
            roomParseReportRow.setRevper(szMath.formatTwoDecimal(allDayConsume, roomStateReport.getTotal()));//设置REVPER
            this.rentSubmit += roomStateReport.getRent();
            this.totalSubmit += roomStateReport.getTotal();
            this.consumeSubmit += allDayConsume;
        }
        roomParseReportRow.setGuestNum(szMath.nullToZero(checkInIntegrationService.getSumNumByDate(beginTime, endTime)));//接待总人数
        roomParseReportRow.setGroupNum(szMath.nullToZero(groupIntegrationService.getSumByDate(beginTime, endTime)));//团队接待数量
        roomParseReportRow.setForeigner(szMath.nullToZero(checkInIntegrationService.getSumForeignerNumByDate(beginTime, endTime)));//外宾接待数量
        List<String> incomeList = new ArrayList<>();
        List<String> incomeTitleList = new ArrayList<>();
        /*分析客源房费*/
        List<DebtIntegration> debtIntegrationList = debtIntegrationService.getSumRoomConsumeByDateGuestSource(beginTime, endTime);
        Integer index = 0;
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            if (debtIntegration.getGuestSource() == null) {
                continue;
            }
            incomeList.add(ifNotNullGetString(debtIntegration.getConsume()));
            incomeTitleList.add(debtIntegration.getGuestSource());
            index++;
        }
        roomParseReportRow.setGuestSourceIndex(index);
                /*分析二级营业部门销售情况*/
        Double totalConsume = 0.0;
        debtIntegrationList = debtIntegrationService.getSumConsumeByDatePointOfSale(beginTime, endTime);
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            totalConsume += debtIntegration.getConsume();
            if (debtIntegration.getPointOfSale() == null) {
                incomeList.add(ifNotNullGetString(debtIntegration.getConsume()));
                incomeTitleList.add("未定义");
                incomeList.add(debtIntegration.getCount());
                incomeTitleList.add("未定义次数");
                continue;
            }
            if (debtIntegration.getPointOfSale().equals("房费")) {
                continue;
            }
            incomeList.add(ifNotNullGetString(debtIntegration.getConsume()));
            incomeTitleList.add(debtIntegration.getPointOfSale());
            incomeList.add(debtIntegration.getCount());
            incomeTitleList.add(debtIntegration.getPointOfSale() + "次数");
        }
        roomParseReportRow.setIncome(incomeList);
        roomParseReportRow.setIncomeTitle(incomeTitleList);
        roomParseReportRowList.add(roomParseReportRow);
    }

    private void clearSubmit() {
        this.rentSubmit = 0;
        this.totalSubmit = 0;
        this.consumeSubmit = 0.0;
    }

    private void parseSubmit(List<RoomParseReportRow> roomParseReportRowList) {
        RoomParseReportRow roomParseReportRow = new RoomParseReportRow();
        roomParseReportRow.setMonth("小计");//设置左边第一栏
        roomParseReportRow.setAverageRent(szMath.formatTwoDecimal(this.rentSubmit, this.totalSubmit));//设置住客率
        roomParseReportRow.setAveragePrice(szMath.formatTwoDecimal(this.consumeSubmit, this.rentSubmit));//设置平均房价
        roomParseReportRow.setRevper(szMath.formatTwoDecimal(this.consumeSubmit, this.rentSubmit));//设置REVPER
        roomParseReportRowList.add(roomParseReportRow);
    }
}
