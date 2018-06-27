package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.CompanyPost;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.CompanyDebtMapper;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.service.CompanyDebtService;
import com.sygdsoft.service.CompanyService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class CompanyDebtController {
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    CompanyService companyService;
    @Autowired
    TimeService timeService;

    @RequestMapping(value = "companyDebtGet")
    public List<CompanyDebt> companyDebtGet(@RequestBody Query query) throws Exception {
        return companyDebtService.get(query);
    }

    @RequestMapping(value = "getNotPaidDebt")
    public List<CompanyDebt> getNotPaidDebt(@RequestBody CompanyPost companyPost) throws Exception {
        String company = companyPost.getCompanyName();
        Double pay = companyPost.getPay();
        Query query = new Query("company=\'" + company + "\'");
        query.setOrderByList(new String[]{"doTime"});
        List<CompanyDebt> companyDebtList = companyDebtService.get(query);
        Double totalDebt = 0.0;
        List<CompanyDebt> companyDebtListNew = new ArrayList<>();
        for (CompanyDebt companyDebt : companyDebtList) {
            totalDebt += companyDebt.getDebt();
            if (totalDebt > pay) {
                /*判断是加上合适还是不加合适*/
                if (totalDebt - pay < companyDebt.getDebt() / 2) {
                    companyDebtListNew.add(companyDebt);
                }else {
                    totalDebt-=companyDebt.getDebt();
                }
                break;
            }
            companyDebtListNew.add(companyDebt);
        }
        if(companyDebtListNew.size()>0){
            companyDebtListNew.get(0).setTotal(totalDebt);
        }
        return companyDebtListNew;
    }

    @RequestMapping(value = "companyDebtAgeReport")
    public List<JSONObject> companyDebtAgeReport()throws Exception{
        List<JSONObject> jsonObjectList=new ArrayList<>();
        Date endTime1=new Date();
        Date beginTime1=timeService.addMonth(endTime1,-3);
        Date endTime2=beginTime1;
        Date beginTime2=timeService.addMonth(endTime2,-3);
        Date endTime3=beginTime2;
        Date beginTime3=timeService.addMonth(endTime3,-6);
        Date endTime4=beginTime3;
        Date beginTime4=timeService.addYear(endTime4,-1);
        Date endTime5=beginTime4;
        Date beginTime5=timeService.addYear(endTime5,-1);
        Date endTime6=beginTime5;
        Date beginTime6=timeService.addYear(endTime6,-100);
        Query query=new Query();
        query.setOrderByListDesc(new String[]{"debt"});
        List<Company> companyList=companyService.get(query);
        for (Company company : companyList) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("company",company.getName());
            jsonObject.put("period1",companyDebtService.getDebtByCompanyDate(company.getName(),beginTime1, endTime1));
            jsonObject.put("period2",companyDebtService.getDebtByCompanyDate(company.getName(),beginTime2, endTime2));
            jsonObject.put("period3",companyDebtService.getDebtByCompanyDate(company.getName(),beginTime3, endTime3));
            jsonObject.put("period4",companyDebtService.getDebtByCompanyDate(company.getName(),beginTime4, endTime4));
            jsonObject.put("period5",companyDebtService.getDebtByCompanyDate(company.getName(),beginTime5, endTime5));
            jsonObject.put("period6",companyDebtService.getDebtByCompanyDate(company.getName(),beginTime6, endTime6));
            jsonObjectList.add(jsonObject);
        }
        return jsonObjectList;
    }
}
