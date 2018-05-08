package com.sygdsoft.controller.especially;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.DeskMapper;
import com.sygdsoft.mapper.PointOfSaleMapper;
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
    @Autowired
    DeskMapper deskMapper;
    @Autowired
    DeskService deskService;
    @Autowired
    DeskInHistoryService deskInHistoryService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;

    @RequestMapping(value = "huayuanEatParseReport")
    public List<HuaYuanRoomParseReturn> huayuanEatParseReport(@RequestBody ReportJson reportJson) throws Exception {
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
        Double totalDay = 0.0;
        Double totalMonth = 0.0;
        Double totalYear = 0.0;
        for (DeskGuestSource deskGuestSource : deskGuestSourceList) {
            if (deskGuestSource.getName() == null) {
                continue;
            }
            row = new HuaYuanRoomParseReturn();
            row.setProject("客源分类");
            row.setSubProject(deskGuestSource.getName() + "收入");
            row.setDay(huaYuanService.getEatGuestSourceConsume(beginTimeDay,endTimeDay,deskGuestSource.getName()));
            totalDay += row.getDay();
            row.setMonth(huaYuanService.getEatGuestSourceConsume(beginTimeMonth,endTimeMonth,deskGuestSource.getName()));
            totalMonth += row.getMonth();
            row.setYear(huaYuanService.getEatGuestSourceConsume(beginTimeYear,endTimeYear,deskGuestSource.getName()));
            totalYear += row.getYear();
            huaYuanRoomParseReturnList.add(row);
        }
        //合计
        row = new HuaYuanRoomParseReturn();
        row.setSubProject("中餐总收入合计");
        row.setDay(totalDay);
        row.setMonth(totalMonth);
        row.setYear(totalYear);
        huaYuanRoomParseReturnList.add(0,row);
        row = new HuaYuanRoomParseReturn();
        /*散客用餐*/
        row.setProject("散客用餐");
        row.setSubProject("可用桌数");
        Integer count=deskMapper.selectAll().size();
        row.setDay(Double.valueOf(count));
        row.setMonth(count*30.0);
        row.setYear(count*365.0);
        huaYuanRoomParseReturnList.add(row);
        row = new HuaYuanRoomParseReturn();
        row.setProject("散客用餐");
        row.setSubProject("用餐桌数");
        row.setDay(Double.valueOf(huaYuanService.getDeskNum(beginTimeDay,endTimeDay,"散客")));
        row.setMonth(Double.valueOf(huaYuanService.getDeskNum(beginTimeMonth,endTimeMonth,"散客")));
        row.setYear(Double.valueOf(huaYuanService.getDeskNum(beginTimeYear,endTimeYear,"散客")));
        huaYuanRoomParseReturnList.add(row);
        /*获取包房总数*/
        Query query=new Query();
        query.setCondition("category=\'包房\'");
        Integer getTotalGroupRoom=deskService.get(query).size();
        Integer day=huaYuanService.getGroupDeskNum(beginTimeDay,endTimeDay,"散客");
        Integer Month=huaYuanService.getGroupDeskNum(beginTimeMonth,endTimeMonth,"散客");
        Integer Year=huaYuanService.getGroupDeskNum(beginTimeYear,endTimeYear,"散客");
        row = new HuaYuanRoomParseReturn();
        row.setProject("散客用餐");
        row.setSubProject("包房使用率");
        row.setDay(szMath.formatTwoDecimalReturnDouble(getTotalGroupRoom,day));
        row.setMonth(szMath.formatTwoDecimalReturnDouble(getTotalGroupRoom*30,Month));
        row.setYear(szMath.formatTwoDecimalReturnDouble(getTotalGroupRoom*365,Year));
        huaYuanRoomParseReturnList.add(row);
        row = new HuaYuanRoomParseReturn();
        row.setProject("散客用餐");
        row.setSubProject("用餐人数");
        row.setDay(Double.valueOf(deskInHistoryService.getGuestNumByGuestSource(beginTimeDay,endTimeDay,"散客")));
        row.setMonth(Double.valueOf(deskInHistoryService.getGuestNumByGuestSource(beginTimeMonth,endTimeMonth,"散客")));
        row.setYear(Double.valueOf(deskInHistoryService.getGuestNumByGuestSource(beginTimeYear,endTimeYear,"散客")));
        huaYuanRoomParseReturnList.add(row);
        row = new HuaYuanRoomParseReturn();
        row.setProject("散客用餐");
        row.setSubProject("人平均消费数");
        row.setDay(szMath.formatTwoDecimalReturnDouble(huaYuanService.getEatGuestSourceConsume(beginTimeDay,endTimeDay,"散客"),deskInHistoryService.getGuestNumByGuestSource(beginTimeDay,endTimeDay,"散客")));
        row.setMonth(szMath.formatTwoDecimalReturnDouble(huaYuanService.getEatGuestSourceConsume(beginTimeMonth,endTimeMonth,"散客"),deskInHistoryService.getGuestNumByGuestSource(beginTimeMonth,endTimeMonth,"散客")));
        row.setYear(szMath.formatTwoDecimalReturnDouble(huaYuanService.getEatGuestSourceConsume(beginTimeYear,endTimeYear,"散客"),deskInHistoryService.getGuestNumByGuestSource(beginTimeYear,endTimeYear,"散客")));
        huaYuanRoomParseReturnList.add(row);
        /*婚宴包席收入*/
        row = new HuaYuanRoomParseReturn();
        row.setProject("婚宴包席收入");
        row.setSubProject("包席桌数");
        row.setDay((double) (huaYuanService.getSubDeskNum(beginTimeDay, endTimeDay, "婚宴") + huaYuanService.getSubDeskNum(beginTimeDay, endTimeDay, "包席")));
        row.setMonth((double) (huaYuanService.getSubDeskNum(beginTimeMonth, endTimeMonth, "婚宴") + huaYuanService.getSubDeskNum(beginTimeMonth, endTimeMonth, "包席")));
        row.setYear((double) (huaYuanService.getSubDeskNum(beginTimeYear, endTimeYear, "婚宴") + huaYuanService.getSubDeskNum(beginTimeYear, endTimeYear, "包席")));
        huaYuanRoomParseReturnList.add(row);
        /*展开菜单*/
        List<String> menuNameList=new ArrayList<>();
        menuNameList.add("灯光费");
        menuNameList.add("大屏费");
        menuNameList.add("场地费");
        menuNameList.add("其他");
        for (String menuSign : menuNameList) {
            row = new HuaYuanRoomParseReturn();
            row.setProject("婚宴包席收入");
            row.setSubProject(menuSign);
            row.setDay(huaYuanService.getMenuGuestSourceConsume(beginTimeDay,endTimeDay,"婚宴",menuSign)+huaYuanService.getMenuGuestSourceConsume(beginTimeDay,endTimeDay,"包席",menuSign));
            row.setMonth(huaYuanService.getMenuGuestSourceConsume(beginTimeMonth,endTimeMonth,"婚宴",menuSign)+huaYuanService.getMenuGuestSourceConsume(beginTimeMonth,endTimeMonth,"包席",menuSign));
            row.setYear(huaYuanService.getMenuGuestSourceConsume(beginTimeYear,endTimeYear,"婚宴",menuSign)+huaYuanService.getMenuGuestSourceConsume(beginTimeYear,endTimeYear,"包席",menuSign));
            huaYuanRoomParseReturnList.add(row);
        }
        /*会议收入*/
        row = new HuaYuanRoomParseReturn();
        row.setProject("会议收入");
        row.setSubProject("会议桌数");
        row.setDay((double) (huaYuanService.getSubDeskNum(beginTimeDay, endTimeDay, "会议")));
        row.setMonth((double) (huaYuanService.getSubDeskNum(beginTimeMonth, endTimeMonth, "会议")));
        row.setYear((double) (huaYuanService.getSubDeskNum(beginTimeYear, endTimeYear, "会议")));
        huaYuanRoomParseReturnList.add(row);
        /*展开菜单*/
        menuNameList.add(0,"自助餐");
        for (String menuSign : menuNameList) {
            row = new HuaYuanRoomParseReturn();
            row.setProject("会议收入");
            row.setSubProject(menuSign);
            row.setDay(huaYuanService.getMenuGuestSourceConsume(beginTimeDay,endTimeDay,"会议",menuSign));
            row.setMonth(huaYuanService.getMenuGuestSourceConsume(beginTimeMonth,endTimeMonth,"会议",menuSign));
            row.setYear(huaYuanService.getMenuGuestSourceConsume(beginTimeYear,endTimeYear,"会议",menuSign));
            huaYuanRoomParseReturnList.add(row);
        }
        List<PointOfSale> pointOfSaleList=pointOfSaleService.getCKPointOfSaleList();
        for (PointOfSale pointOfSale : pointOfSaleList) {
            for (String item : pointOfSale.getSecondPointOfSale().split(" ")) {
                row = new HuaYuanRoomParseReturn();
                row.setSubProject(item);
                row.setDay(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(beginTimeDay,endTimeDay, pointOfSale.getFirstPointOfSale(), item));
                row.setMonth(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(beginTimeMonth,endTimeMonth, pointOfSale.getFirstPointOfSale(), item));
                row.setYear(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(beginTimeYear,endTimeYear, pointOfSale.getFirstPointOfSale(), item));
                huaYuanRoomParseReturnList.add(row);
            }
        }
        return huaYuanRoomParseReturnList;
    }
}
