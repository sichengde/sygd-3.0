package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.RoomCategoryLine;
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
        List<DebtIntegration> debtIntegrationList=debtIntegrationService.parseRoomCategoryDebt(beginTime,endTime);
        /*没有数据的话直接返回空*/
        if(debtIntegrationList.size()==0){
            return null;
        }
        Date beginTimeHistory=timeService.addYear(beginTime,-1);
        Date endTimeHistory=timeService.addYear(endTime,-1);
        List<DebtIntegration> debtIntegrationHistoryList=debtIntegrationService.parseRoomCategoryDebt(beginTimeHistory,endTimeHistory);//历史同期数据
        RoomCategoryParse roomCategoryParse=new RoomCategoryParse();
        roomCategoryParse.setRoomCategoryRowList(this.parseData(debtIntegrationList));
        roomCategoryParse.setRoomCategoryRowHistoryList(this.parseData(debtIntegrationHistoryList));
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
    private List<RoomCategoryRow> parseData(List<DebtIntegration> debtIntegrationList){
        List<RoomCategoryRow> roomStateSaleList=new ArrayList<>();
        RoomCategoryRow roomStateSale=new RoomCategoryRow();
        String lastCategory="";
        Double totalConsumeRow=0.0;//每列一共金额
        Integer totalCountRow=0;//每列一共卖了多少次
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            if(!lastCategory.equals("")){//不等于空的话，把上一条的金额算了
                roomStateSale.setAveragePrice(szMath.formatTwoDecimal(totalConsumeRow,totalCountRow));
                roomStateSale.setTotalConsume(szMath.formatTwoDecimalReturnDouble(totalConsumeRow));
            }
            if(!lastCategory.equals(debtIntegration.getCategoryRoom())){
                roomStateSale= new RoomCategoryRow();
                lastCategory=debtIntegration.getCategoryRoom();
                roomStateSale.setCategory(debtIntegration.getCategoryRoom());//设置房类
                roomStateSale.setTotal(roomService.getTotalCategoryNum(lastCategory));//设置该房类的总数
                roomStateSaleList.add(roomStateSale);
            }
            if(debtIntegration.getCategory().equals("全日房费")){
                totalConsumeRow+=debtIntegration.getConsume();
                totalCountRow+=Integer.valueOf(debtIntegration.getCount());
                roomStateSale.setAllDay(debtIntegration.getCount());
            }
            if(debtIntegration.getCategory().equals("加收房租")){
                totalConsumeRow+=debtIntegration.getConsume();
                totalCountRow+=Integer.valueOf(debtIntegration.getCount());
                roomStateSale.setAddDay(debtIntegration.getCount());
            }
            if(debtIntegration.getCategory().equals("小时房费")){
                totalConsumeRow+=debtIntegration.getConsume();
                totalCountRow+=Integer.valueOf(debtIntegration.getCount());
                roomStateSale.setHourRoom(debtIntegration.getCount());
            }
        }
        /*最后一行平均金额在此计算*/
        roomStateSale.setAveragePrice(szMath.formatTwoDecimal(totalConsumeRow,totalCountRow));
        roomStateSale.setTotalConsume(szMath.formatTwoDecimalReturnDouble(totalConsumeRow));
        return roomStateSaleList;
    }
}
