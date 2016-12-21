package com.sygdsoft.controller;

import com.sygdsoft.model.HotelParseLine;
import com.sygdsoft.model.HotelParseRow;
import com.sygdsoft.model.PointOfSale;
import com.sygdsoft.service.DebtIntegrationService;
import com.sygdsoft.service.DeskDetailHistoryService;
import com.sygdsoft.service.PointOfSaleService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

/**
 * Created by Administrator on 2016/11/29 0029.
 * 全店收入表
 */
@RestController
public class HotelParseController {
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    TimeService timeService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;

    @RequestMapping(value = "hotelParse")
    public List<HotelParseRow> hotelParse() throws Exception {
        timeService.setNow();
        /*先获取有多少营业部门*/
        List<PointOfSale> pointOfSaleList = pointOfSaleService.get(null);
        List<HotelParseRow> hotelParseRowList = new ArrayList<>();
        for (PointOfSale pointOfSale : pointOfSaleList) {
            String module = pointOfSale.getModule();
            switch (module) {
                case "接待":
                    /*一行一行填写*/
                    hotelParseRowList.add(this.getHotelSumByPointOfSale("接待"));
                    hotelParseRowList.add(this.getHotelSumByPointOfSale("房费"));
                    hotelParseRowList.add(this.getHotelSumByPointOfSale("房吧"));
                    hotelParseRowList.add(this.getHotelSumByPointOfSale("零售"));
                    break;
                case "餐饮":
                    String[] secondPointOfSaleList = pointOfSale.getSecondPointOfSale().split(" ");
                    hotelParseRowList.add(this.getCKSumByPointOfSale(pointOfSale.getFirstPointOfSale(), null));
                    for (String s : secondPointOfSaleList) {
                        hotelParseRowList.add(this.getCKSumByPointOfSale(pointOfSale.getFirstPointOfSale(), s));
                    }
                    break;
                case "桑拿":

                    break;
            }
        }
        return hotelParseRowList;
    }

    /**
     * 接待曲线图生成
     */
    @RequestMapping(value = "hotelParseLine")
    public HotelParseLine hotelParseLine(@RequestBody List<String> params) throws ParseException {
        timeService.setNow();
        Date today = timeService.getNow();
        Date todayHistory = timeService.addYear(today, -1);
        String timezone = params.get(0);
        String pointOfSale = params.get(1);
        Date beginTime = null;
        Date endTime = null;
        Date beginTimeHistory = null;
        Date endTimeHistory = null;
        if (timezone.equals("month")) {
            beginTime = timeService.getMinMonth(today);
            endTime = timeService.getMaxMonth(today);
            beginTimeHistory = timeService.getMinMonth(todayHistory);
            endTimeHistory = timeService.getMaxMonth(todayHistory);
        } else if (timezone.equals("year")) {
            beginTime = timeService.getMinYear(today);
            endTime = timeService.getMaxYear(today);
            beginTimeHistory = timeService.getMinYear(todayHistory);
            endTimeHistory = timeService.getMaxYear(todayHistory);
        }
        HotelParseLine hotelParseLine = new HotelParseLine();
        hotelParseLine.setHotelParseLineRowList(debtIntegrationService.getSumDateLineByPointOfSale(beginTime, endTime, pointOfSale));
        hotelParseLine.setHotelParseLineRowListHistory(debtIntegrationService.getSumDateLineByPointOfSale(beginTimeHistory, endTimeHistory, pointOfSale));
        return hotelParseLine;
    }

    /**
     * 餐饮曲线图生成
     */
    @RequestMapping(value = "hotelParseLineCK")
    public HotelParseLine hotelParseLineCK(@RequestBody List<String> params) throws ParseException {
        timeService.setNow();
        Date today = timeService.getNow();
        Date todayHistory = timeService.addYear(today, -1);
        String timezone = params.get(0);
        String firstPointOfSale = params.get(1);
        String secondPointOfSale = params.get(2);
        Date beginTime = null;
        Date endTime = null;
        Date beginTimeHistory = null;
        Date endTimeHistory = null;
        if (timezone.equals("year")) {
            beginTime = timeService.getMinYear(today);
            endTime = timeService.getMaxYear(today);
            beginTimeHistory = timeService.getMinYear(todayHistory);
            endTimeHistory = timeService.getMaxYear(todayHistory);
        } else if (timezone.equals("month")) {
            beginTime = timeService.getMinMonth(today);
            endTime = timeService.getMaxMonth(today);
            beginTimeHistory = timeService.getMinMonth(todayHistory);
            endTimeHistory = timeService.getMaxMonth(todayHistory);
        }
        HotelParseLine hotelParseLine = new HotelParseLine();
        hotelParseLine.setHotelParseLineRowList(deskDetailHistoryService.getDeskMoneyDateLineByDatePointOfSale(beginTime, endTime, firstPointOfSale, secondPointOfSale));
        hotelParseLine.setHotelParseLineRowListHistory(deskDetailHistoryService.getDeskMoneyDateLineByDatePointOfSale(beginTimeHistory, endTimeHistory, firstPointOfSale, secondPointOfSale));
        return hotelParseLine;
    }

    private HotelParseRow getHotelSumByPointOfSale(String pointOfSale) throws ParseException {
        HotelParseRow hotelParseRow = new HotelParseRow();
        hotelParseRow.setPointOfSale(pointOfSale);
        if (pointOfSale.equals("接待")) {
            pointOfSale = null;
        } else {
            hotelParseRow.setFatherFirstPointOfSale("接待");
        }
        hotelParseRow.setModule("接待");
        Date today = timeService.getNow();
        Date todayHistory = timeService.addYear(today, -1);
        hotelParseRow.setDayTotal(debtIntegrationService.getSumByPointOfSale(timeService.getMinTime(today), timeService.getMaxTime(today), pointOfSale));
        hotelParseRow.setMonthTotal(debtIntegrationService.getSumByPointOfSale(timeService.getMinMonth(today), timeService.getMaxMonth(today), pointOfSale));
        hotelParseRow.setYearTotal(debtIntegrationService.getSumByPointOfSale(timeService.getMinYear(today), timeService.getMaxYear(today), pointOfSale));
        hotelParseRow.setDayHistoryTotal(debtIntegrationService.getSumByPointOfSale(timeService.getMinTime(todayHistory), timeService.getMaxTime(todayHistory), pointOfSale));
        hotelParseRow.setMonthHistoryTotal(debtIntegrationService.getSumByPointOfSale(timeService.getMinMonth(todayHistory), timeService.getMaxMonth(todayHistory), pointOfSale));
        hotelParseRow.setYearHistoryTotal(debtIntegrationService.getSumByPointOfSale(timeService.getMinYear(todayHistory), timeService.getMaxYear(todayHistory), pointOfSale));
        return hotelParseRow;
    }

    private HotelParseRow getCKSumByPointOfSale(String firstPointOfSale, String secondPointOfSale) throws ParseException {
        HotelParseRow hotelParseRow = new HotelParseRow();
        hotelParseRow.setModule("餐饮");
        if (secondPointOfSale != null) {
            hotelParseRow.setPointOfSale(secondPointOfSale);
            hotelParseRow.setFatherFirstPointOfSale(firstPointOfSale);
        } else {
            hotelParseRow.setPointOfSale(firstPointOfSale);
        }
        Date today = timeService.getNow();
        Date todayHistory = timeService.addYear(today, -1);
        hotelParseRow.setDayTotal(ifNotNullGetString(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinTime(today), timeService.getMaxTime(today), firstPointOfSale, secondPointOfSale)));
        hotelParseRow.setMonthTotal(ifNotNullGetString(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinMonth(today), timeService.getMaxMonth(today), firstPointOfSale, secondPointOfSale)));
        hotelParseRow.setYearTotal(ifNotNullGetString(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinYear(today), timeService.getMaxYear(today), firstPointOfSale, secondPointOfSale)));
        hotelParseRow.setDayHistoryTotal(ifNotNullGetString(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinTime(todayHistory), timeService.getMaxTime(todayHistory), firstPointOfSale, secondPointOfSale)));
        hotelParseRow.setMonthHistoryTotal(ifNotNullGetString(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinMonth(todayHistory), timeService.getMaxMonth(todayHistory), firstPointOfSale, secondPointOfSale)));
        hotelParseRow.setYearHistoryTotal(ifNotNullGetString(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinYear(todayHistory), timeService.getMaxYear(todayHistory), firstPointOfSale, secondPointOfSale)));
        return hotelParseRow;
    }
}
