package com.sygdsoft.controller.room;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.SaleManRich;
import com.sygdsoft.mapper.SaleManMapper;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.SaleManQuery;
import com.sygdsoft.service.CompanyDebtService;
import com.sygdsoft.service.CompanyService;
import com.sygdsoft.service.DebtPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by 舒展 on 2017-01-05.
 */
@RestController
public class SaleManReportController {
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    CompanyService companyService;
    @Autowired
    SaleManMapper saleManMapper;

    @RequestMapping(value = "saleManReport")
    public List<Company> saleManReport(@RequestBody SaleManQuery saleManQuery) {
        String saleMan = saleManQuery.getSaleMan();
        Date beginTime = saleManQuery.getBeginTime();
        Date endTime = saleManQuery.getEndTime();
        return companyDebtService.getTotalDebtBySaleManDate(saleMan, beginTime, endTime);
    }

    @RequestMapping(value = "saleManRichReport")
    public List<JSONObject> saleManRichReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        List<JSONObject> result = new ArrayList<>();
        List<SaleManRich> saleManList = saleManMapper.getSaleManGroup(beginTime, endTime);
        Map<String, Map<String, Map<String, Double>>> saleManCompanyCurrencyMoneyMap = new HashMap<>();
        for (SaleManRich saleManRich : saleManList) {
            Map<String, Map<String, Double>> companyCurrencyMoneyMap = saleManCompanyCurrencyMoneyMap.get(saleManRich.getSaleMan());
            if(companyCurrencyMoneyMap==null){
                companyCurrencyMoneyMap=new HashMap<>();
            }
            if (saleManRich.getCompany() == null) {
                saleManCompanyCurrencyMoneyMap.put(saleManRich.getSaleMan(), null);
            } else {
                Map<String, Double> currencyMoneyMap = companyCurrencyMoneyMap.get(saleManRich.getCompany());
                if(currencyMoneyMap==null){
                    currencyMoneyMap=new HashMap<>();
                }
                String currency;
                if (saleManRich.getCurrency().equals("转单位") || saleManRich.getCurrency().equals("转哑房") || saleManRich.getCurrency().equals("转房客")) {
                    currency = "挂账";
                } else {
                    currency = "付现";
                }
                Double money=currencyMoneyMap.get(currency);
                if(money==null){
                    money=0.0;
                }
                currencyMoneyMap.put(currency, money + saleManRich.getMoney());
            }
        }
        for (String saleMan : saleManCompanyCurrencyMoneyMap.keySet()) {
            Map<String, Map<String, Double>> companyCurrencyMoneyMap = saleManCompanyCurrencyMoneyMap.get(saleMan);
            /*建个空行*/
            if (companyCurrencyMoneyMap == null) {
                JSONObject row = new JSONObject();
                row.put("saleMan", saleMan);
                row.put("payNow", 0);
                row.put("payNextTime", 0);
                result.add(row);
            } else {
                JSONObject sumRow = new JSONObject();
                sumRow.put("saleMan", saleMan);
                double totalPayNow = 0.0;
                double totalPayNextTime = 0.0;
                /*两行以上增加汇总*/
                if(companyCurrencyMoneyMap.size()>1) {
                    result.add(sumRow);
                }
                for (String company : companyCurrencyMoneyMap.keySet()) {
                    Map<String, Double> currencyMap = companyCurrencyMoneyMap.get(company);
                    /*一个单位一行*/
                    JSONObject row = new JSONObject();
                    row.put("company", company);
                    if(companyCurrencyMoneyMap.size()==1){//只有一个人的话增加销售员名称
                        row.put("saleMan", saleMan);
                    }
                    Double payNow=currencyMap.get("付现");
                    if(payNow==null){
                        payNow=0.0;
                    }
                    Double payNextTime=currencyMap.get("挂账");
                    if(payNextTime==null){
                        payNextTime=0.0;
                    }
                    row.put("payNow", payNow);
                    row.put("payNextTime", payNextTime);
                    row.put("payTotal", payNow+payNextTime);
                    result.add(row);
                    totalPayNow += payNow;
                    totalPayNextTime += payNextTime;
                }
                sumRow.put("payNow", totalPayNow);
                sumRow.put("payNextTime", totalPayNextTime);
                sumRow.put("payTotal", totalPayNow+totalPayNextTime);
            }
        }
        return result;
    }
}
