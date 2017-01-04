package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.CompanyPost;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class CompanyController {
    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "companyGet")
    public List<Company> companyGet(@RequestBody Query query) throws Exception {
        return companyService.get(query);
    }

    @RequestMapping(value = "companyDelete")
    @Transactional(rollbackFor = Exception.class)
    public void companyDelete(@RequestBody List<Company> companyList) throws Exception {
        timeService.setNow();
        String s = "";
        for (Company company : companyList) {
            s = s + company.getName() + "/";
        }
        s = s.substring(0, s.length() - 1);
        userLogService.addUserLog("删除单位:" + s, userLogService.company, userLogService.delete,s);
        companyService.delete(companyList);
    }

    @RequestMapping(value = "companyUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void companyUpdate(@RequestBody List<Company> companyList) throws Exception {
        if (companyList.size() > 1) {
            if (companyList.get(0).getId().equals(companyList.get(companyList.size() / 2).getId())) {
                timeService.setNow();
                String s = userLogService.parseListDeference(companyList);
                userLogService.addUserLog(s, userLogService.company, userLogService.update,s);
                companyService.update(companyList.subList(0, companyList.size() / 2));
                return;
            }
        }
        companyService.update(companyList);
    }

    @RequestMapping(value = "companyAdd")
    @Transactional(rollbackFor = Exception.class)
    public void companyAdd(@RequestBody Company company) throws Exception {
        timeService.setNow();
        userLogService.addUserLog("单位:" + company.getName() + "金额:" + company.getDeposit(), userLogService.company, userLogService.companyCreate,company.getName());
        companyService.add(company);
    }

    /**
     * 结算
     *
     * param 1：单位名。2：金额。3.使用多少余额。4.币种
     * @return
     */
    @RequestMapping(value = "companyPay")
    @Transactional(rollbackFor = Exception.class)
    public Integer companyPay(@RequestBody CompanyPost companyPost) throws Exception {
        timeService.setNow();
        String companyName = companyPost.getCompanyName();
        Double total = companyPost.getTotal();
        String currency = companyPost.getCurrencyPost().getCurrency();
        String currencyAdd = companyPost.getCurrencyPost().getCurrencyAdd();
        /*更新单位金额*/
        Company company=companyService.getByName(companyName);
        if(company==null){
            throw new Exception("该单位不存在");
        }
        company.setDebt(company.getDebt()-total);
        companyService.update(company);
        /*插入一条账务*/
        CompanyDebt companyDebt = new CompanyDebt();
        companyDebt.setCompany(companyName);
        companyDebt.setDebt(-total);
        companyDebt.setDoTime(timeService.getNow());
        companyDebt.setUserId(userService.getCurrentUser());
        companyDebt.setCategory("应收结算");
        companyDebt.setCurrency(currency);
        companyDebt.setCurrencyAdd(currencyAdd);
        companyDebt.setCurrentRemain(company.getDebt());
        companyDebtService.add(companyDebt);
        userLogService.addUserLog("单位名称:" + companyName + " 结算:" + total, userLogService.company, userLogService.companyPay,companyName);
        /*生成结算报表
        * 1.单位名称
        * 2.结算金额
        * 3.操作员
        * 4.币种信息
        * */
        return reportService.generateReport(null, new String[]{companyName, ifNotNullGetString(total), userService.getCurrentUser(),currency+"/"+currencyAdd}, "companyPay", "pdf");
    }

    /**
     * 预付
     *
     * @param params 1：单位名。2：金额。3.币种
     * @return
     */
    @RequestMapping(value = "companyDeposit")
    @Transactional(rollbackFor = Exception.class)
    public void companyDeposit(@RequestBody List<String> params) throws Exception {
        timeService.setNow();
        String companyName = params.get(0);
        Double deposit = Double.valueOf(params.get(1));
        String currency = params.get(2);
        /*更新单位充值金额*/
        companyService.addDeposit(companyName, deposit);
        /*单位账务中插入一条记录*/
        CompanyDebt companyDebt = new CompanyDebt();
        companyDebt.setCompany(companyName);
        companyDebt.setDeposit(deposit);
        companyDebt.setDoTime(timeService.getNow());
        companyDebt.setUserId(userService.getCurrentUser());
        companyDebt.setCategory(companyDebtService.deposit);
        companyDebt.setCurrency(currency);
        companyDebtService.add(companyDebt);
        userLogService.addUserLog("单位名称:" + companyName + " 支付:" + deposit, userLogService.company, userLogService.companyDeposit,companyName);
    }
}
