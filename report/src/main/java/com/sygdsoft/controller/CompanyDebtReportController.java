package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.report.CompanyDebtReportRow;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebtReportData;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.CompanyDebtService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-02-03.
 */
@RestController
public class CompanyDebtReportController {
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    TimeService timeService;

    @RequestMapping("companyDebtReport")
    public CompanyDebtReportData companyDebtReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        CompanyDebtReportData companyDebtReportData = new CompanyDebtReportData();
        companyDebtReportData.setReportJson(reportJson);
        List<CompanyDebtReportRow> companyDebtReportRowList = companyDebtService.getTotalDebtByDate(beginTime, endTime);
        endTime = beginTime;
        beginTime = timeService.stringToDateShort("1990-01-31");
        for (CompanyDebtReportRow companyDebtReportRow : companyDebtReportRowList) {
            /*设置期初挂账金额*/
            companyDebtReportRow.setRemain(companyDebtService.getDebtByCompanyDate(companyDebtReportRow.getCompany(), beginTime, endTime));
        }
        companyDebtReportData.setCompanyDebtReportRowList(companyDebtReportRowList);
        return companyDebtReportData;
    }
}
