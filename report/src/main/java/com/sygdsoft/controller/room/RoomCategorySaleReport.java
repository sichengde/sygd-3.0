package com.sygdsoft.controller.room;

import com.sygdsoft.jsonModel.RoomCategoryLine;
import com.sygdsoft.jsonModel.report.RoomCategoryRow;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by 舒展 on 2016-09-06.
 * 客房销售统计
 */
@RestController
public class RoomCategorySaleReport {
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    HotelService hotelService;
    @Autowired
    SzMath szMath;
    @Autowired
    RoomCategoryService roomCategoryService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    GroupIntegrationService groupIntegrationService;
    @Autowired
    RoomStateReportService roomStateReportService;

    /**
     * 结束日期时间要设置为24:00:00
     */
    @RequestMapping(value = "roomCategorySaleReport")
    public RoomCategoryParse roomCategorySaleReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = timeService.getMinTime(reportJson.getBeginTime());
        Date endTime = timeService.getMinTime(reportJson.getEndTime());
        /*效验roomStateReport表中有没有数据*/
        List<RoomStateReport> roomCategoryRowList=roomStateReportService.getSumByDateCategory(beginTime, endTime);
        RoomStateReport roomStateReport=roomStateReportService.getSumByDate(beginTime, endTime);
        roomCategoryRowList.add(roomStateReport);
        RoomCategoryParse roomCategoryParse = new RoomCategoryParse();
        roomCategoryParse.setRoomStateReportList(roomCategoryRowList);
        /*设置备注信息*/
        String remark="接待人数："+checkInIntegrationService.getSumNumByDate(beginTime, endTime,null);
        remark+=",接待团队"+groupIntegrationService.getSumByDate(beginTime, endTime);
        remark+=",接待外宾"+checkInIntegrationService.getSumForeignerNumByDate(beginTime, endTime);
        roomCategoryParse.setRemark(remark);
        return roomCategoryParse;
    }

    @RequestMapping(value = "parseRoomCategoryDebtLine")
    public List<RoomCategoryLine> parseRoomCategoryDebtLine(@RequestBody ReportJson reportJson) {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        String category = reportJson.getParam1();
        return debtIntegrationService.parseRoomCategoryDebtLine(beginTime, endTime, category);
    }

    @RequestMapping(value = "parseRoomCategoryDebtLineAll")
    public Map parseRoomCategoryDebtLineAll(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        Date beginTimeHistory = timeService.addYear(beginTime, -1);
        Date endTimeHistory = timeService.addYear(endTime, -1);
        Map map = new HashMap();
        List<RoomCategory> roomCategoryList = roomCategoryService.get(null);
        for (RoomCategory roomCategory : roomCategoryList) {
            map.put(roomCategory.getCategory(), debtIntegrationService.parseRoomCategoryDebtLine(beginTime, endTime, roomCategory.getCategory()));
            map.put("去年:" + roomCategory.getCategory(), debtIntegrationService.parseRoomCategoryDebtLine(beginTimeHistory, endTimeHistory, roomCategory.getCategory()));
        }
        return map;
    }

    /**
     * 分析数据，当前和历史都在此体现
     */
    private List<RoomCategoryRow> parseData(List<RoomCategoryRow> roomCategoryRowList) {
        if (roomCategoryRowList.size() == 0) {
            return roomCategoryRowList;
        }
        List<RoomCategoryRow> roomStateSaleList = new ArrayList<>();
        RoomCategoryRow roomStateSale = new RoomCategoryRow();
        String lastCategory = "";
        Double totalConsumeRow = 0.0;//每列一共金额
        Double totalConsumeRowWithOutAdd = 0.0;//每列一共金额(不考虑加收)
        Double totalCategoryConsumeRowWithOutAdd = 0.0;//合计不考虑加收房费
        for (RoomCategoryRow roomCategoryRow : roomCategoryRowList) {
            if (!lastCategory.equals("")) {//不等于空的话，把上一条的金额算了
                roomStateSale.setAveragePrice(szMath.formatTwoDecimal(totalConsumeRowWithOutAdd, roomStateSale.getRent()));
                roomStateSale.setRevper(szMath.formatTwoDecimal(totalConsumeRowWithOutAdd, roomStateSale.getTotal()));
                totalConsumeRowWithOutAdd=0.0;
                roomStateSale.setAverageRent(szMath.formatTwoDecimal(roomStateSale.getRent(), roomStateSale.getTotal()));
                roomStateSale.setTotalConsume(szMath.formatTwoDecimalReturnDouble(totalConsumeRow));
            }
            if (!lastCategory.equals(roomCategoryRow.getCategoryRoom())) {
                roomStateSale = new RoomCategoryRow();
                lastCategory = roomCategoryRow.getCategoryRoom();
                roomStateSale.setCategory(roomCategoryRow.getCategoryRoom());//设置房类
                roomStateSaleList.add(roomStateSale);
            }
            if (roomCategoryRow.getCategory().equals("全日房费")) {
                totalConsumeRowWithOutAdd += roomCategoryRow.getConsume();
                totalCategoryConsumeRowWithOutAdd += roomCategoryRow.getConsume();
                totalConsumeRow += roomCategoryRow.getConsume();
                roomStateSale.setAllDay(roomCategoryRow.getCount());
            }
            if (roomCategoryRow.getCategory().equals("加收房租")) {
                totalConsumeRow += roomCategoryRow.getConsume();
                roomStateSale.setAddDay(roomCategoryRow.getCount());
            }
            if (roomCategoryRow.getCategory().equals("小时房费")) {
                totalConsumeRow += roomCategoryRow.getConsume();
                roomStateSale.setHourRoom(roomCategoryRow.getCount());
            }
            /*设置房间信息（只需要设置一遍）*/
            if (roomStateSale.getTotal() == null) {
                roomStateSale.setTotal(roomCategoryRow.getTotal());
                roomStateSale.setEmpty(roomCategoryRow.getEmpty());
                roomStateSale.setRepair(roomCategoryRow.getRepair());
                roomStateSale.setSelf(roomCategoryRow.getSelf());
                roomStateSale.setBackUp(roomCategoryRow.getBackUp());
                roomStateSale.setRent(roomCategoryRow.getRent());
            }
        }
        /*最后一行平均金额在此计算*/
        roomStateSale.setAveragePrice(szMath.formatTwoDecimal(totalConsumeRowWithOutAdd, roomStateSale.getRent()));
        roomStateSale.setRevper(szMath.formatTwoDecimal(totalConsumeRowWithOutAdd, roomStateSale.getTotal()));
        roomStateSale.setAverageRent(szMath.formatTwoDecimal(roomStateSale.getRent(), roomStateSale.getTotal()));
        roomStateSale.setTotalConsume(szMath.formatTwoDecimalReturnDouble(totalConsumeRow));
        /*计算合计*/
        RoomCategoryRow roomStateSaleSubmit = new RoomCategoryRow();
        roomStateSaleSubmit.setCategory("合计");
        Integer submitTotal = 0;
        Integer submitEmpty = 0;
        Integer submitRepair = 0;
        Integer submitSelf = 0;
        Integer submitBackup = 0;
        Integer submitRent = 0;
        Integer submitAllday = 0;
        Integer submitAdd = 0;
        Integer submitHour = 0;
        Double submitTotalConsume = 0.0;
        for (RoomCategoryRow roomCategoryRow : roomStateSaleList) {
            submitTotal += roomCategoryRow.getTotal();
            submitEmpty += roomCategoryRow.getEmpty();
            submitRepair += roomCategoryRow.getRepair();
            submitSelf += roomCategoryRow.getSelf();
            submitBackup += roomCategoryRow.getBackUp();
            submitRent += roomCategoryRow.getRent();
            submitAllday += Integer.valueOf(szMath.nullToZero(roomCategoryRow.getAllDay()));
            submitAdd += Integer.valueOf(szMath.nullToZero(roomCategoryRow.getAddDay()));
            submitHour += Integer.valueOf(szMath.nullToZero(roomCategoryRow.getHourRoom()));
            submitTotalConsume += roomCategoryRow.getTotalConsume();
        }
        roomStateSaleSubmit.setTotal(submitTotal);
        roomStateSaleSubmit.setEmpty(submitEmpty);
        roomStateSaleSubmit.setRepair(submitRepair);
        roomStateSaleSubmit.setSelf(submitSelf);
        roomStateSaleSubmit.setBackUp(submitBackup);
        roomStateSaleSubmit.setRent(submitRent);
        roomStateSaleSubmit.setAllDay(szMath.ifNotNullGetString(submitAllday));
        roomStateSaleSubmit.setAddDay(szMath.ifNotNullGetString(submitAdd));
        roomStateSaleSubmit.setHourRoom(szMath.ifNotNullGetString(submitHour));
        roomStateSaleSubmit.setTotalConsume(submitTotalConsume);
        roomStateSaleSubmit.setAveragePrice(szMath.formatTwoDecimal(totalCategoryConsumeRowWithOutAdd, roomStateSaleSubmit.getRent()));
        roomStateSaleSubmit.setRevper(szMath.formatTwoDecimal(totalCategoryConsumeRowWithOutAdd, roomStateSaleSubmit.getTotal()));
        roomStateSaleSubmit.setAverageRent(szMath.formatTwoDecimal(roomStateSaleSubmit.getRent(), roomStateSaleSubmit.getTotal()));
        roomStateSaleList.add(roomStateSaleSubmit);
        return roomStateSaleList;
    }
}
