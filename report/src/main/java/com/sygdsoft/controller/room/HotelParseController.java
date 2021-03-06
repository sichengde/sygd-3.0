package com.sygdsoft.controller.room;

import com.sygdsoft.model.HotelParseLine;
import com.sygdsoft.model.HotelParseRow;
import com.sygdsoft.model.PointOfSale;
import com.sygdsoft.model.ReportJson;
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
    public List<HotelParseRow> hotelParse(@RequestBody ReportJson reportJson) throws Exception {
        Date date=reportJson.getBeginTime();
        timeService.setNow();
        /*先获取有多少营业部门*/
        List<PointOfSale> pointOfSaleList = pointOfSaleService.get(null);
        List<HotelParseRow> hotelParseRowList = new ArrayList<>();
        Double totalFinal1=0.0;
        Double totalFinal2=0.0;
        Double totalFinal3=0.0;
        Double totalFinal4=0.0;
        Double totalFinal5=0.0;
        Double totalFinal6=0.0;
        for (PointOfSale pointOfSale : pointOfSaleList) {
            String module = pointOfSale.getModule();
            switch (module) {
                case "接待":
                    /*一行一行填写*/
                    String[] secondPointOfSales=pointOfSale.getSecondPointOfSale().split(" ");
                    Double total1=0.0;
                    Double total2=0.0;
                    Double total3=0.0;
                    Double total4=0.0;
                    Double total5=0.0;
                    Double total6=0.0;
                    HotelParseRow row1=new HotelParseRow();
                    hotelParseRowList.add(row1);
                    for (String secondPointOfSale : secondPointOfSales) {
                        HotelParseRow hotelParseRow=this.getHotelSumByPointOfSale(secondPointOfSale,date);
                        total1+=hotelParseRow.getDayTotal();
                        total2+=hotelParseRow.getMonthTotal();
                        total3+=hotelParseRow.getYearTotal();
                        total4+=hotelParseRow.getDayHistoryTotal();
                        total5+=hotelParseRow.getMonthHistoryTotal();
                        total6+=hotelParseRow.getYearHistoryTotal();
                        totalFinal1+=hotelParseRow.getDayTotal();
                        totalFinal2+=hotelParseRow.getMonthTotal();
                        totalFinal3+=hotelParseRow.getYearTotal();
                        totalFinal4+=hotelParseRow.getDayHistoryTotal();
                        totalFinal5+=hotelParseRow.getMonthHistoryTotal();
                        totalFinal6+=hotelParseRow.getYearHistoryTotal();
                        hotelParseRowList.add(hotelParseRow);
                    }
                    row1.setPointOfSale("客房总计");
                    row1.setDayTotal(total1);
                    row1.setMonthTotal(total2);
                    row1.setYearTotal(total3);
                    row1.setDayHistoryTotal(total4);
                    row1.setMonthHistoryTotal(total5);
                    row1.setYearHistoryTotal(total6);
                    row1.setModule("接待");
                    row1.setSum(true);
                    break;
                case "餐饮":
                    String[] secondPointOfSaleList = pointOfSale.getSecondPointOfSale().split(" ");
                    HotelParseRow hotelParseRow=this.getCKSumByPointOfSale(pointOfSale.getFirstPointOfSale(), null,date);
                    hotelParseRow.setSum(true);
                    hotelParseRow.setModule("餐饮");
                    hotelParseRowList.add(hotelParseRow);
                    totalFinal1+=hotelParseRow.getDayTotal();
                    totalFinal2+=hotelParseRow.getMonthTotal();
                    totalFinal3+=hotelParseRow.getYearTotal();
                    totalFinal4+=hotelParseRow.getDayHistoryTotal();
                    totalFinal5+=hotelParseRow.getMonthHistoryTotal();
                    totalFinal6+=hotelParseRow.getYearHistoryTotal();
                    for (String s : secondPointOfSaleList) {
                        hotelParseRowList.add(this.getCKSumByPointOfSale(pointOfSale.getFirstPointOfSale(), s,date));
                    }
                    break;
                case "桑拿":

                    break;
            }
        }
        HotelParseRow rowLast=new HotelParseRow();
        rowLast.setPointOfSale("总计");
        rowLast.setDayTotal(totalFinal1);
        rowLast.setMonthTotal(totalFinal2);
        rowLast.setYearTotal(totalFinal3);
        rowLast.setDayHistoryTotal(totalFinal4);
        rowLast.setMonthHistoryTotal(totalFinal5);
        rowLast.setYearHistoryTotal(totalFinal6);
        rowLast.setSumTotal(true);
        hotelParseRowList.add(rowLast);
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

    private HotelParseRow getHotelSumByPointOfSale(String pointOfSale,Date date) throws ParseException {
        HotelParseRow hotelParseRow = new HotelParseRow();
        hotelParseRow.setPointOfSale(pointOfSale);
        if (pointOfSale.equals("接待")) {
            pointOfSale = null;
        } else {
            hotelParseRow.setFatherFirstPointOfSale("接待");
        }
        hotelParseRow.setModule("接待");
        Date todayHistory = timeService.addYear(date, -1);
        hotelParseRow.setDayTotal(debtIntegrationService.getSumConsumeByDoTime(timeService.getMinTime(date), timeService.getMaxTime(date), pointOfSale,null,true));
        hotelParseRow.setMonthTotal(debtIntegrationService.getSumConsumeByDoTime(timeService.getMinMonth(date), timeService.getMaxMonth(date), pointOfSale,null,true));
        hotelParseRow.setYearTotal(debtIntegrationService.getSumConsumeByDoTime(timeService.getMinYear(date), timeService.getMaxYear(date), pointOfSale,null,true));
        hotelParseRow.setDayHistoryTotal(debtIntegrationService.getSumConsumeByDoTime(timeService.getMinTime(todayHistory), timeService.getMaxTime(todayHistory), pointOfSale,null,true));
        hotelParseRow.setMonthHistoryTotal(debtIntegrationService.getSumConsumeByDoTime(timeService.getMinMonth(todayHistory), timeService.getMaxMonth(todayHistory), pointOfSale,null,true));
        hotelParseRow.setYearHistoryTotal(debtIntegrationService.getSumConsumeByDoTime(timeService.getMinYear(todayHistory), timeService.getMaxYear(todayHistory), pointOfSale,null,true));
        return hotelParseRow;
    }

    private HotelParseRow getCKSumByPointOfSale(String firstPointOfSale, String secondPointOfSale,Date date) throws ParseException {
        HotelParseRow hotelParseRow = new HotelParseRow();
        hotelParseRow.setModule("餐饮");
        if (secondPointOfSale != null) {
            hotelParseRow.setPointOfSale(secondPointOfSale);
            hotelParseRow.setFatherFirstPointOfSale(firstPointOfSale);
        } else {
            hotelParseRow.setPointOfSale(firstPointOfSale+"总计");
        }
        Date todayHistory = timeService.addYear(date, -1);
        hotelParseRow.setDayTotal(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinTime(date), timeService.getMaxTime(date), firstPointOfSale, secondPointOfSale));
        hotelParseRow.setMonthTotal(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinMonth(date), timeService.getMaxMonth(date), firstPointOfSale, secondPointOfSale));
        hotelParseRow.setYearTotal(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinYear(date), timeService.getMaxYear(date), firstPointOfSale, secondPointOfSale));
        hotelParseRow.setDayHistoryTotal(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinTime(todayHistory), timeService.getMaxTime(todayHistory), firstPointOfSale, secondPointOfSale));
        hotelParseRow.setMonthHistoryTotal(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinMonth(todayHistory), timeService.getMaxMonth(todayHistory), firstPointOfSale, secondPointOfSale));
        hotelParseRow.setYearHistoryTotal(deskDetailHistoryService.getDeskMoneyByDatePointOfSale(timeService.getMinYear(todayHistory), timeService.getMaxYear(todayHistory), firstPointOfSale, secondPointOfSale));
        return hotelParseRow;
    }
}
