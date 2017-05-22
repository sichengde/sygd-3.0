package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.CompanyDebtIntegrationService;
import com.sygdsoft.service.CompanyDebtService;
import com.sygdsoft.service.CompanyService;
import com.sygdsoft.service.DebtHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-03-03.
 * 账务整合
 */
@RestController
public class CompanyDebtIntegrationController {
    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyDebtIntegrationService companyDebtIntegrationService;
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    DebtHistoryService debtHistoryService;

    @RequestMapping(value = "companyDebtIntegrationGet")
    public List<CompanyDebtIntegration> companyDebtIntegrationGet(@RequestBody Query query) throws Exception{
        return companyDebtIntegrationService.get(query);
    }

    @RequestMapping(value = "companyDebtDetailGet")
    public List<Company> companyDebtDetailGet(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        Boolean paid=reportJson.getParamBool();
        if(paid==null){
            paid=false;
        }
        List<Company> companyList=companyService.get(null);
        for (Company company : companyList) {
            if(!paid) {
                List<CompanyDebtIntegration> companyDebtIntegrationList = companyDebtIntegrationService.getByCompanyDate(company.getName(), beginTime, endTime);
                for (CompanyDebtIntegration companyDebtIntegration : companyDebtIntegrationList) {
                    String paySerial = companyDebtIntegration.getPaySerial();//只有客房离店转单位才允许精确结算
                    if (paySerial != null) {
                        List<DebtHistory> debtHistoryList = debtHistoryService.getListByPaySerial(paySerial);
                        companyDebtIntegration.setDebtHistoryList(debtHistoryList);
                    }
                }
                company.setCompanyDebtIntegrationList(companyDebtIntegrationList);
            }else {
                List<CompanyDebt> companyDebtList=companyDebtService.getByCompanyDate(company.getName(),beginTime, endTime);
                for (CompanyDebt companyDebt : companyDebtList) {
                    String paySerial = companyDebt.getPaySerial();//只有客房离店转单位才允许精确结算
                    if (paySerial != null) {
                        List<DebtHistory> debtHistoryList = debtHistoryService.getListByPaySerial(paySerial);
                        companyDebt.setDebtHistoryList(debtHistoryList);
                    }
                }
                company.setCompanyDebtList(companyDebtList);
            }
        }
        return companyList;
    }
}
