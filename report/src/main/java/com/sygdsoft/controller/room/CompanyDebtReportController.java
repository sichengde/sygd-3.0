package com.sygdsoft.controller.room;

import com.sygdsoft.jsonModel.report.CompanyDebtReportRow;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebtReportData;
import com.sygdsoft.model.CompanyPay;
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

/**
 * Created by 舒展 on 2017-02-03.
 * 单位欠款分析
 */
@RestController
public class CompanyDebtReportController {
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    TimeService timeService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyDebtIntegrationService companyDebtIntegrationService;
    @Autowired
    CompanyPayService companyPayService;
    @Autowired
    CompanyDebtHistoryService companyDebtHistoryService;
    @Autowired
    SzMath szMath;

    @RequestMapping("companyDebtReport")
    public CompanyDebtReportData companyDebtReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        CompanyDebtReportData companyDebtReportData = new CompanyDebtReportData();
        companyDebtReportData.setReportJson(reportJson);
        /*开始*/
        List<Company> companyList=companyService.get(null);
        List<CompanyDebtReportRow> companyDebtReportRowList=new ArrayList<>();
        Date beginTimeHistory = timeService.stringToDateShort("1990-01-31");
        Date endTimeHistory = beginTime;
        for (Company company : companyList) {
            CompanyDebtReportRow companyDebtReportRow=new CompanyDebtReportRow();
            String companyName=company.getName();
            companyDebtReportRow.setCompany(companyName);
            /*期初余额=期初到以前的总账务-结算的*/
            Double totalDebt=szMath.nullToZero(companyDebtIntegrationService.getDebtByCompanyDate(companyName, beginTimeHistory, endTimeHistory));
            CompanyPay companyPay=companyPayService.getSumPay(companyName, null,null,beginTimeHistory, endTimeHistory);
            Double totalPayDebt;
            if(companyPay==null){
                totalPayDebt=0.0;
            }else {
                totalPayDebt=companyPay.getDebt();
            }
            companyDebtReportRow.setRemain(szMath.formatTwoDecimalReturnDouble(totalDebt-totalPayDebt));
            companyDebtReportRow.setDebtGenerate(szMath.nullToZero(companyDebtIntegrationService.getDebtByCompanyDate(companyName, beginTime, endTime)));
            companyDebtReportRow.setBack(szMath.nullToZero(companyDebtHistoryService.getBackMoney(companyName, beginTime, endTime)));
            /*同理期初余额*/
            totalDebt=szMath.nullToZero(companyDebtIntegrationService.getDebtByCompanyDate(companyName, beginTimeHistory, endTime));
            companyPay=companyPayService.getSumPay(companyName, null,null,beginTimeHistory, endTime);
            if(companyPay==null){
                totalPayDebt=0.0;
            }else {
                totalPayDebt=companyPay.getDebt();
            }
            companyDebtReportRow.setDebt(szMath.formatTwoDecimalReturnDouble(totalDebt-totalPayDebt));
            companyDebtReportRowList.add(companyDebtReportRow);
        }
        companyDebtReportData.setCompanyDebtReportRowList(companyDebtReportRowList);
        return companyDebtReportData;
    }
}
