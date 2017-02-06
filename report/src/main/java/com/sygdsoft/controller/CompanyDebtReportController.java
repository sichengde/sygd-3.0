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
 * 单位欠款分析
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
        Date beginTimeHistory = timeService.stringToDateShort("1990-01-31");
        Date endTimeHistory = beginTime;
        for (CompanyDebtReportRow companyDebtReportRow : companyDebtReportRowList) {
            String companyName = companyDebtReportRow.getCompany();
            /*设置期初挂账金额*/
            companyDebtReportRow.setRemain(companyDebtService.getDebtByCompanyDate(companyName, beginTimeHistory, endTimeHistory));
            /*设置本期的发生额*/
            companyDebtReportRow.setDebtGenerate(companyDebtService.getDebtGenerateByCompanyDate(companyName, beginTime, endTime));
            /*设置本期回款*/
            companyDebtReportRow.setBack(companyDebtService.getDebtBackByCompanyDate(companyName, beginTime, endTime));
            /*设置本期房费*/
            /*设置本期其他*/
            CompanyDebtReportRow companyDebtReportRow1 = companyDebtService.getRoomConsumeByCompanyDate(companyName, beginTime, endTime);
            if (companyDebtReportRow1 != null) {
                companyDebtReportRow.setRoomConsume(companyDebtReportRow1.getRoomConsume());
                companyDebtReportRow.setOtherConsume(companyDebtReportRow.getNotNullDebtGenerate() - companyDebtReportRow1.getNotNullRoomConsume());
            }
            /*设置期末余额*/
            companyDebtReportRow.setDebt(companyDebtReportRow.getNotNullDebt()+companyDebtReportRow.getNotNullRemain());
        }
        companyDebtReportData.setCompanyDebtReportRowList(companyDebtReportRowList);
        return companyDebtReportData;
    }
}
