package com.sygdsoft.controller;

import com.sygdsoft.model.Company;
import com.sygdsoft.model.SaleManQuery;
import com.sygdsoft.service.CompanyDebtService;
import com.sygdsoft.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-01-05.
 */
@RestController
public class SaleManReportController {
    @Autowired
    CompanyDebtService companyDebtService;
    @RequestMapping(value = "saleManReport")
    public List<Company> saleManReport(@RequestBody SaleManQuery saleManQuery){
        String saleMan = saleManQuery.getSaleMan();
        Date beginTime = saleManQuery.getBeginTime();
        Date endTime = saleManQuery.getEndTime();
        return companyDebtService.getTotalDebtBySaleManDate(saleMan, beginTime, endTime);
    }
}
