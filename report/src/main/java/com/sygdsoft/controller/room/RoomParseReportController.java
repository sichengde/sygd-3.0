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
    @RequestMapping("RoomParseReport")
    public void RoomParseReport(@RequestBody RoomParseReportQuery roomParseReportQuery) throws Exception {
        Date date=roomParseReportQuery.getDate();
        Date beginTime;
        Date endTime;
        List<RoomParseReportRow> roomParseReportRowList=new ArrayList<>();
        switch (roomParseReportQuery.getRange()){
            case "月":
                /*分为3个10天*/
                beginTime=timeService.getMinMonth(date);
                endTime=timeService.addDay(date,10);
                RoomParseReportRow roomParseReportRow=new RoomParseReportRow();
                roomParseReportRow.setMonth(timeService.getMonthCN(beginTime)+"上旬");//设置左边第一栏
                RoomStateReport roomStateReport=roomStateReportService.getSumByDate(beginTime, endTime);
                roomParseReportRow.setAverageRent(szMath.formatTwoDecimal(roomStateReport.getRent(),roomStateReport.getTotal()));//设置住客率
                Double allDayConsume=debtIntegrationService.getSumAllDayConsumeByDate(beginTime, endTime);
                roomParseReportRow.setAveragePrice(szMath.formatTwoDecimal(allDayConsume,roomStateReport.getRent()));//设置平均房价
                roomParseReportRow.setRevper(szMath.formatTwoDecimal(allDayConsume,roomStateReport.getTotal()));//设置REVPER
                roomParseReportRow.setGuestNum(checkInIntegrationService.getSumNumByDate(beginTime, endTime));//接待总人数
                roomParseReportRow.setGroupNum(groupIntegrationService.getSumByDate(beginTime, endTime));//团队接待数量
                roomParseReportRow.setForeigner(checkInIntegrationService.getSumForeignerNumByDate(beginTime, endTime));//外宾接待数量
                List<String> incomeList=new ArrayList<>();
                List<String> incomeTitleList=new ArrayList<>();
                /*分析客源房费*/
                List<DebtIntegration> debtIntegrationList=debtIntegrationService.getSumRoomConsumeByDateGuestSource(beginTime, endTime);
                for (DebtIntegration debtIntegration : debtIntegrationList) {
                    if(debtIntegration.getGuestSource()==null){
                        continue;
                    }
                    incomeList.add(ifNotNullGetString(debtIntegration.getConsume()));
                    incomeTitleList.add(debtIntegration.getGuestSource());
                }
                /*分析二级营业部门销售情况*/
                debtIntegrationList=debtIntegrationService.getSumConsumeByDatePointOfSale(beginTime, endTime);
                for (DebtIntegration debtIntegration : debtIntegrationList) {
                    if(debtIntegration.getPointOfSale()==null){
                        continue;
                    }
                    if(debtIntegration.getPointOfSale().equals("房费")){
                        continue;
                    }
                    incomeList.add(ifNotNullGetString(debtIntegration.getConsume()));
                    incomeTitleList.add(debtIntegration.getPointOfSale());
                    incomeList.add(ifNotNullGetString(debtIntegration.getConsume()));
                    incomeTitleList.add(debtIntegration.getPointOfSale());
                }
                endTime=timeService.addDay(date,10);

                endTime=timeService.getMinMonth(date);

                break;
            case "季":
                /*分为3个月，每个月3个10天*/
                beginTime=timeService.getMinQuarter(date);
                endTime=timeService.getMaxQuarter(date,beginTime);
                break;
            case "年":
                /*分为4个季度，每个季度3个月*/
                beginTime=timeService.getMinYear(date);
                endTime=timeService.getMaxYear(date);
                break;
        }
    }
}
