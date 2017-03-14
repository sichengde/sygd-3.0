package com.sygdsoft.controller.room;

import com.sygdsoft.controller.DailyReportController;
import com.sygdsoft.controller.HotelParseController;
import com.sygdsoft.model.CompanyPay;
import com.sygdsoft.model.HotelParseRow;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.room.DailyReportReturn;
import com.sygdsoft.model.room.DebtAndPayReturn;
import com.sygdsoft.model.room.DebtAndPayRow;
import com.sygdsoft.service.*;
import com.sygdsoft.util.NullJudgement;
import com.sygdsoft.util.SzMath;
import org.omg.CORBA.portable.ValueInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
@RestController
public class DebtAndPayReportController {
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    HotelParseController hotelParseController;
    @Autowired
    DailyReportController dailyReportController;
    @Autowired
    SzMath szMath;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    VipIntegrationService vipIntegrationService;
    @Autowired
    CompanyPayService companyPayService;

    @RequestMapping(value = "debtAndPayReport")
    public DebtAndPayReturn debtAndPayReport(@RequestBody ReportJson reportJson) throws Exception {
        Date date = reportJson.getBeginTime();
        DebtAndPayReturn debtAndPayReturn = new DebtAndPayReturn();
        List<DebtAndPayRow> debtAndPayRowList = new ArrayList<>();
        DebtAndPayRow debtAndPayRow;
        /*先生成全店收入表*/
        List<HotelParseRow> hotelParseRowList = hotelParseController.hotelParse(reportJson);
        Double total1 = 0.0;
        Double total2 = 0.0;
        Double total3 = 0.0;
        /*按照全店收入表的格式生成*/
        for (HotelParseRow hotelParseRow : hotelParseRowList) {
            debtAndPayRow = new DebtAndPayRow();
            /*全店收入金额*/
            debtAndPayRow.setTitle(hotelParseRow.getPointOfSale());
            debtAndPayRow.setDebtDay(hotelParseRow.getDayTotal());
            debtAndPayRow.setDebtMonth(hotelParseRow.getMonthTotal());
            debtAndPayRow.setDebtYear(hotelParseRow.getYearTotal());
            /*餐饮和桑拿的发生额都等于结算款*/
            if ("接待".equals(hotelParseRow.getModule())) {
                if (hotelParseRow.getNotNullSum()) {
                    debtAndPayRow.setPayDay(debtHistoryService.getHistoryConsume(timeService.getMinTime(date), timeService.getMaxTime(date), null));
                    debtAndPayRow.setPayMonth(debtHistoryService.getHistoryConsume(timeService.getMinMonth(date), timeService.getMaxMonth(date), null));
                    debtAndPayRow.setPayYear(debtHistoryService.getHistoryConsume(timeService.getMinYear(date), timeService.getMaxYear(date), null));
                    total1 += debtAndPayRow.getPayDay();
                    total2 += debtAndPayRow.getPayMonth();
                    total3 += debtAndPayRow.getPayYear();
                } else {
                    debtAndPayRow.setPayDay(debtHistoryService.getHistoryConsume(timeService.getMinTime(date), timeService.getMaxTime(date), hotelParseRow.getPointOfSale()));
                    debtAndPayRow.setPayMonth(debtHistoryService.getHistoryConsume(timeService.getMinMonth(date), timeService.getMaxMonth(date), hotelParseRow.getPointOfSale()));
                    debtAndPayRow.setPayYear(debtHistoryService.getHistoryConsume(timeService.getMinYear(date), timeService.getMaxYear(date), hotelParseRow.getPointOfSale()));
                }
            } else {
                if (hotelParseRow.getNotNullSumTotal()) {//最终的合计
                    debtAndPayRow.setPayDay(total1);
                    debtAndPayRow.setPayMonth(total2);
                    debtAndPayRow.setPayYear(total3);
                } else {
                    debtAndPayRow.setPayDay(debtAndPayRow.getDebtDay());
                    debtAndPayRow.setPayMonth(debtAndPayRow.getDebtMonth());
                    debtAndPayRow.setPayYear(debtAndPayRow.getDebtYear());
                    if(hotelParseRow.getNotNullSum()){
                        total1 += debtAndPayRow.getPayDay();
                        total2 += debtAndPayRow.getPayMonth();
                        total3 += debtAndPayRow.getPayYear();
                    }
                }
            }
            debtAndPayRowList.add(debtAndPayRow);
        }
        debtAndPayRow = new DebtAndPayRow();
        debtAndPayRow.setTitle("会员充值");
        debtAndPayRow.setDebtDay(vipIntegrationService.getTotalPay(timeService.getMinTime(date), timeService.getMaxTime(date)));
        debtAndPayRow.setDebtMonth(vipIntegrationService.getTotalPay(timeService.getMinMonth(date), timeService.getMaxMonth(date)));
        debtAndPayRow.setDebtYear(vipIntegrationService.getTotalPay(timeService.getMinYear(date), timeService.getMaxYear(date)));
        debtAndPayRowList.add(debtAndPayRow);
        /*计算单位结算金额*/
        DebtAndPayRow debtAndPayRow1 = new DebtAndPayRow();
        debtAndPayRow1.setTitle("单位回款");

        DebtAndPayRow debtAndPayRow2 = new DebtAndPayRow();
        debtAndPayRow2.setTitle("单位回款抵用");

        CompanyPay companyPayQuery = companyPayService.getSumPay(null, null, null, timeService.getMinTime(date), timeService.getMaxTime(date));
        if (companyPayQuery == null) {
            debtAndPayRow1.setDebtDay(0.0);
            debtAndPayRow2.setDebtDay(0.0);
        } else {
            debtAndPayRow1.setDebtDay(companyPayQuery.getPay());
            debtAndPayRow2.setDebtDay(companyPayQuery.getDebt());
        }
        companyPayQuery = companyPayService.getSumPay(null, null, null, timeService.getMinMonth(date), timeService.getMaxMonth(date));
        if (companyPayQuery == null) {
            debtAndPayRow1.setDebtMonth(0.0);
            debtAndPayRow2.setDebtMonth(0.0);
        } else {
            debtAndPayRow1.setDebtMonth(companyPayQuery.getPay());
            debtAndPayRow2.setDebtMonth(companyPayQuery.getDebt());
        }
        companyPayQuery = companyPayService.getSumPay(null, null, null, timeService.getMinYear(date), timeService.getMaxYear(date));
        if (companyPayQuery == null) {
            debtAndPayRow1.setDebtYear(0.0);
            debtAndPayRow2.setDebtYear(0.0);
        } else {
            debtAndPayRow1.setDebtYear(companyPayQuery.getPay());
            debtAndPayRow2.setDebtYear(companyPayQuery.getDebt());
        }
        debtAndPayRowList.add(debtAndPayRow1);

        debtAndPayRowList.add(debtAndPayRow2);
        debtAndPayReturn.setDebtAndPayRowList(debtAndPayRowList);
        return debtAndPayReturn;
    }
}
