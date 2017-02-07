package com.sygdsoft.controller;

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
    /**
     * 结束日期时间要设置为24:00:00
     */
    @RequestMapping(value = "roomCategorySaleReport")
    public RoomCategoryParse roomCategorySaleReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        List<RoomCategoryRow> roomCategoryRowList=debtIntegrationService.parseRoomCategoryDebt(beginTime,endTime);
        /*没有数据的话直接返回空*/
        if(roomCategoryRowList.size()==0){
            return null;
        }
        Date beginTimeHistory=timeService.addYear(beginTime,-1);
        Date endTimeHistory=timeService.addYear(endTime,-1);
        List<RoomCategoryRow> roomCategoryRowHistoryList =debtIntegrationService.parseRoomCategoryDebt(beginTimeHistory,endTimeHistory);//历史同期数据
        RoomCategoryParse roomCategoryParse=new RoomCategoryParse();
        roomCategoryParse.setRoomCategoryRowList(this.parseData(roomCategoryRowList));
        roomCategoryParse.setRoomCategoryRowHistoryList(this.parseData(roomCategoryRowHistoryList));
        /*再填充线性数据，暂时没用*/
        /*Map map=new HashMap();
        List<RoomCategory> roomCategoryList=roomCategoryService.get(null);
        for (RoomCategory roomCategory : roomCategoryList) {
            map.put(roomCategory.getCategory(),debtIntegrationService.parseRoomCategoryDebtLine(beginTime, endTime, roomCategory.getCategory()));
            map.put("去年:"+roomCategory.getCategory(),debtIntegrationService.parseRoomCategoryDebtLine(beginTimeHistory, endTimeHistory, roomCategory.getCategory()));
        }
        roomCategoryParse.setRoomCategoryMap(map);*/
        return roomCategoryParse;
    }

    @RequestMapping(value = "parseRoomCategoryDebtLine")
    public List<RoomCategoryLine> parseRoomCategoryDebtLine(@RequestBody ReportJson reportJson){
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        String category=reportJson.getParam1();
        return debtIntegrationService.parseRoomCategoryDebtLine(beginTime, endTime, category);
    }

    @RequestMapping(value = "parseRoomCategoryDebtLineAll")
    public Map parseRoomCategoryDebtLineAll(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        Date beginTimeHistory=timeService.addYear(beginTime,-1);
        Date endTimeHistory=timeService.addYear(endTime,-1);
        Map map=new HashMap();
        List<RoomCategory> roomCategoryList=roomCategoryService.get(null);
        for (RoomCategory roomCategory : roomCategoryList) {
            map.put(roomCategory.getCategory(),debtIntegrationService.parseRoomCategoryDebtLine(beginTime, endTime, roomCategory.getCategory()));
            map.put("去年:"+roomCategory.getCategory(),debtIntegrationService.parseRoomCategoryDebtLine(beginTimeHistory, endTimeHistory, roomCategory.getCategory()));
        }
        return map;
    }

    /**
     * 分析数据，当前和历史都在此体现
     */
    private List<RoomCategoryRow> parseData(List<RoomCategoryRow> roomCategoryRowList){
        if(roomCategoryRowList.size()==0){
            return roomCategoryRowList;
        }
        List<RoomCategoryRow> roomStateSaleList=new ArrayList<>();
        RoomCategoryRow roomStateSale=new RoomCategoryRow();
        String lastCategory="";
        Double totalConsumeRow=0.0;//每列一共金额
        Integer totalCountRow=0;//每列一共卖了多少次
        for (RoomCategoryRow roomCategoryRow : roomCategoryRowList) {
            if(!lastCategory.equals("")){//不等于空的话，把上一条的金额算了
                roomStateSale.setAveragePrice(szMath.formatTwoDecimal(totalConsumeRow,roomStateSale.getRent()));
                roomStateSale.setRevper(szMath.formatTwoDecimal(totalConsumeRow,roomStateSale.getTotal()));
                roomStateSale.setAverageRent(szMath.formatTwoDecimal(roomStateSale.getRent(),roomStateSale.getTotal()));
                roomStateSale.setTotalConsume(szMath.formatTwoDecimalReturnDouble(totalConsumeRow));
            }
            if(!lastCategory.equals(roomCategoryRow.getCategoryRoom())){
                roomStateSale= new RoomCategoryRow();
                lastCategory=roomCategoryRow.getCategoryRoom();
                roomStateSale.setCategory(roomCategoryRow.getCategoryRoom());//设置房类
                roomStateSaleList.add(roomStateSale);
            }
            if(roomCategoryRow.getCategory().equals("全日房费")){
                totalConsumeRow+=roomCategoryRow.getConsume();
                totalCountRow+=Integer.valueOf(roomCategoryRow.getCount());
                roomStateSale.setAllDay(roomCategoryRow.getCount());
            }
            if(roomCategoryRow.getCategory().equals("加收房租")){
                totalConsumeRow+=roomCategoryRow.getConsume();
                totalCountRow+=Integer.valueOf(roomCategoryRow.getCount());
                roomStateSale.setAddDay(roomCategoryRow.getCount());
            }
            if(roomCategoryRow.getCategory().equals("小时房费")){
                totalConsumeRow+=roomCategoryRow.getConsume();
                totalCountRow+=Integer.valueOf(roomCategoryRow.getCount());
                roomStateSale.setHourRoom(roomCategoryRow.getCount());
            }
            /*设置房间信息（只需要设置一遍）*/
            if(roomStateSale.getTotal()==null){
                roomStateSale.setTotal(roomCategoryRow.getTotal());
                roomStateSale.setEmpty(roomCategoryRow.getEmpty());
                roomStateSale.setRepair(roomCategoryRow.getRepair());
                roomStateSale.setSelf(roomCategoryRow.getSelf());
                roomStateSale.setBackUp(roomCategoryRow.getBackUp());
                roomStateSale.setRent(roomCategoryRow.getRent());
            }
        }
        /*最后一行平均金额在此计算*/
        roomStateSale.setAveragePrice(szMath.formatTwoDecimal(totalConsumeRow,roomStateSale.getRent()));
        roomStateSale.setRevper(szMath.formatTwoDecimal(totalConsumeRow,roomStateSale.getTotal()));
        roomStateSale.setAverageRent(szMath.formatTwoDecimal(roomStateSale.getRent(),roomStateSale.getTotal()));
        roomStateSale.setTotalConsume(szMath.formatTwoDecimalReturnDouble(totalConsumeRow));
        return roomStateSaleList;
    }
}
