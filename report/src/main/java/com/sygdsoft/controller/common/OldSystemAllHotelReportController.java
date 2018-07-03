package com.sygdsoft.controller.common;

import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OldSystemAllHotelReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    TimeService timeService;
    @Autowired
    SzMath szMath;
    @Autowired
    PayPointOfSaleService payPointOfSaleService;
    @Autowired
    DebtService debtService;
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    DebtHistoryService debtHistoryService;

    @RequestMapping(value = "oldSystemAllHotelReport")
    public int oldSystemAllHotelReport(@RequestBody ReportJson reportJson) throws Exception {
        Date date = reportJson.getBeginTime();//当日时间
        Date beginTime1 = timeService.getMinTime(date);
        Date beginTime2 = timeService.getMinMonth(date);
        Date beginTime3 = timeService.getMinYear(date);
        Date endTime1 = timeService.getMaxTime(date);
        Date endTime2 = timeService.getMaxMonth(date);
        Date endTime3 = timeService.getMaxYear(date);
        List<FieldTemplate> fieldTemplateList = new ArrayList<>();
        /*先计算客房合计*/
        FieldTemplate fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("合计");
        fieldTemplate.setField2(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime1, endTime1, null, true)));
        fieldTemplate.setField3(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime2, endTime2, null, true)));
        fieldTemplate.setField4(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime3, endTime3, null, true)));
        fieldTemplate.setField5("0.00");//现金都设置为0
        fieldTemplate.setField6(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime1, endTime1, null)));
        fieldTemplate.setField7(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime2, endTime2, null)));
        fieldTemplate.setField8(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime3, endTime3, null)));
        fieldTemplate.setField9(fieldTemplate.getField2());
        /*挂账未结=debt里的和companyDebt里的*/
        fieldTemplate.setField10(szMath.formatTwoDecimal(debtHistoryService.getConsumeNotCompanyPaid()));
        fieldTemplate.setField11(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(null, null, null, true)));
        fieldTemplateList.add(fieldTemplate);
        /*------------------------------------------再计算各自的明细------------------------------------------*/
        List<String> pointOfSaleList = pointOfSaleService.getRoomPointOfSaleList();
        List<CompanyDebt> companyDebtList = companyDebtService.get();
        StringBuilder paySerial = new StringBuilder();
        for (CompanyDebt companyDebt : companyDebtList) {
            if ("接待".equals(companyDebt.getPointOfSale()) && companyDebt.getPaySerial() != null && !"".equals(companyDebt.getPaySerial())) {
                paySerial.append("\'").append(companyDebt.getPaySerial()).append("\',");
            }
        }
        List<DebtHistory> debtHistoryList = new ArrayList<>();
        if (!paySerial.toString().equals("")) {
            debtHistoryList = debtHistoryService.getListByCompanyPaid(paySerial.substring(0, paySerial.length() - 1));
        }
        for (String pointOfSale : pointOfSaleList) {
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(pointOfSale);
            fieldTemplate.setField2(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime1, endTime1, pointOfSale, true)));
            fieldTemplate.setField3(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime2, endTime2, pointOfSale, true)));
            fieldTemplate.setField4(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime3, endTime3, pointOfSale, true)));
            fieldTemplate.setField5("0.00");//现金都设置为0
            fieldTemplate.setField6(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime1, endTime1, pointOfSale)));
            fieldTemplate.setField7(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime2, endTime2, pointOfSale)));
            fieldTemplate.setField8(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime3, endTime3, pointOfSale)));
            fieldTemplate.setField9(fieldTemplate.getField2());
            /*挂账未结=debt里的和companyDebt里的*/
            Double debtConsume = debtService.getConsumeByPointOfSale(pointOfSale);
            for (DebtHistory debtHistory : debtHistoryList) {
                if (pointOfSale.equals(debtHistory.getPointOfSale())) {
                    debtConsume += debtHistory.getNotNullConsume();
                }
            }
            for (CompanyDebt companyDebt : companyDebtList) {
                /*定额结算产生的差价直接加*/
                if (companyDebt.getNotNullOtherConsume() && pointOfSale.equals(companyDebt.getSecondPointOfSale())) {
                    debtConsume += companyDebt.getDebt();
                }
            }
            fieldTemplate.setField10(szMath.formatTwoDecimal(debtConsume));
            /*全部挂账*/
            fieldTemplate.setField11(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(null, null, pointOfSale, true)));
            fieldTemplateList.add(fieldTemplate);
        }
        return reportService.generateReport(fieldTemplateList, new String[]{}, "oldSystemAllHotel", "pdf");
    }
}
