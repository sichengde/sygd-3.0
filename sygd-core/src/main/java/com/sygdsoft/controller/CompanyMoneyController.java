package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CompanyMoney;
import com.sygdsoft.service.CompanyMoneyService;
import com.sygdsoft.service.ReportService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CompanyMoneyController {
    @Autowired
    CompanyMoneyService companyMoneyService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    TimeService timeService;
    @Autowired
    ReportService reportService;
    @Autowired
    Util util;

    @RequestMapping(value = "companyMoneyGet")
    public List<CompanyMoney> getCompanyMoneyDetail(@RequestBody Query query)throws Exception{
        return companyMoneyService.get(query);
    }

    @RequestMapping(value = "companyMoneyIn")
    @Transactional(rollbackFor = Exception.class)
    public Integer companyMoneyIn(@RequestBody CompanyMoney companyMoney)throws Exception{
        /*服务器端设置一下时间*/
        timeService.setNow();
        companyMoney.setDoTime(timeService.getNow());
        companyMoneyService.add(companyMoney);
        userLogService.addUserLog("单位充值:"+companyMoney.getCompany()+",金额:"+companyMoney.getMoney(),userLogService.reception,userLogService.companyDeposit,null);
        /*打印账单
        * 1.单位名称
        * 2.充值金额
        * 3.币种
        * 4.时间
        * 5.操作员
        * 6.金额大写
        * */
        String[] param=new String[]{companyMoney.getCompany(), String.valueOf(companyMoney.getMoney()),companyMoney.getCurrency(),timeService.dateToStringLong(timeService.getNow()),companyMoney.getUserId(),util.number2CNMontrayUnit(BigDecimal.valueOf(companyMoney.getMoney()))};
        return reportService.generateReport(null,param, "companyMoneyIn","pdf");
    }
}
