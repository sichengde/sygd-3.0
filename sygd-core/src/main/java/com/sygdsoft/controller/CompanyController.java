package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.CompanyPost;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

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
    @Autowired
    SerialService serialService;
    @Autowired
    CompanyPayService companyPayService;
    @Autowired
    CompanyDebtHistoryService companyDebtHistoryService;
    @Autowired
    SzMath szMath;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    CompanyMoneyService companyMoneyService;
    @Autowired
    PayPointOfSaleService payPointOfSaleService;

    @RequestMapping(value = "companyGet")
    public List<Company> companyGet(@RequestBody Query query) throws Exception {
        List<Company> companyList = companyService.get(query);
        /*设置一下接待和餐厅的挂账款*/
        for (Company company : companyList) {
            company.setRoomDebt(companyService.getModuleDebt("接待", company.getName(), null));
            company.setEatDebt(companyService.getModuleDebt("餐饮", company.getName(), null));
            company.setCompanyMoneyList(companyMoneyService.getSumList(company.getName()));
        }
        return companyList;
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
        userLogService.addUserLog("删除单位:" + s, userLogService.company, userLogService.delete, s);
        companyService.delete(companyList);
    }

    @RequestMapping(value = "companyUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void companyUpdate(@RequestBody List<Company> companyList) throws Exception {
        if (companyList.size() > 1) {
            if (companyList.get(0).getId().equals(companyList.get(companyList.size() / 2).getId())) {
                timeService.setNow();
                String s = userLogService.parseListDeference(companyList);
                userLogService.addUserLog(s, userLogService.company, userLogService.update, s);
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
        userLogService.addUserLog("单位:" + company.getName() + "金额:" + company.getDeposit(), userLogService.company, userLogService.companyCreate, company.getName());
        companyService.add(company);
    }

    /**
     * 结算,定额和挂账记录结算都在这里
     * <p>
     * param 1：单位名。2：金额。3.使用多少余额。4.币种
     *
     * @return
     */
    @RequestMapping(value = "companyPay")
    @Transactional(rollbackFor = Exception.class)
    public Integer companyPay(@RequestBody CompanyPost companyPost) throws Exception {
        timeService.setNow();
        serialService.setCompanyPaySerial();
        String companyName = companyPost.getCompanyName();
        String remark = companyPost.getRemark();
        Double debt = companyPost.getDebt();
        Double pay = companyPost.getPay();
        List<CompanyDebt> companyDebtList = companyPost.getCompanyDebtList();
        String currency = companyPost.getCurrencyPost().getCurrency();
        String currencyAdd = companyPost.getCurrencyPost().getCurrencyAdd();
        /*更新单位金额*/
        Company company = companyService.getByName(companyName);
        if (company == null) {
            throw new Exception("该单位不存在");
        }
        company.setDebt(company.getDebt() - debt);
        companyService.update(company);
        /*生成结账记录*/
        CompanyPay companyPay = new CompanyPay();
        companyPay.setCompany(companyName);
        companyPay.setRemark(remark);
        companyPay.setCurrencyAdd(currencyAdd);
        companyPay.setCurrency(currency);
        companyPay.setCompanyPaySerial(serialService.getCompanyPaySerial());
        companyPay.setDebt(debt);
        companyPay.setPay(pay);
        companyPay.setDoneTime(timeService.getNow());
        companyPay.setUserId(userService.getCurrentUser());
        companyPayService.add(companyPay);
        /*删除之前的单位账*/
        companyDebtService.delete(companyDebtList);
        /*添加进历史*/
        List<CompanyDebtHistory> companyDebtHistoryList = new ArrayList<>();
        CompanyDebtHistory companyDebtHistory;
        List<FieldTemplate> templateList = new ArrayList<>();
        StringBuilder paySerial= new StringBuilder();
        for (CompanyDebt companyDebt : companyDebtList) {
            companyDebtHistory = new CompanyDebtHistory(companyDebt);
            companyDebtHistory.setDoneTime(timeService.getNow());
            companyDebtHistory.setCompanyPaySerial(serialService.getCompanyPaySerial());
            companyDebtHistoryList.add(companyDebtHistory);
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(timeService.dateToStringLong(companyDebt.getDoTime()));
            fieldTemplate.setField2(companyDebt.getPaySerial());
            fieldTemplate.setField3(szMath.ifNotNullGetString(companyDebt.getDebt()));
            fieldTemplate.setField4(companyDebt.getPointOfSale());
            fieldTemplate.setField5(companyDebt.getDescription());
            fieldTemplate.setField6(companyDebt.getUserId());
            templateList.add(fieldTemplate);
            if("接待".equals(companyDebt.getPointOfSale())) {
                if(companyDebtHistory.getPaySerial()!=null && !"".equals(companyDebtHistory.getPaySerial())) {
                    paySerial.append("\'").append(companyDebtHistory.getPaySerial()).append("\',");
                }
            }
            if(companyDebt.getNotNullOtherConsume()){
                /*设置ppos*/
                PayPointOfSale payPointOfSale = new PayPointOfSale();
                payPointOfSale.setCurrency(companyPay.getCurrency());
                payPointOfSale.setCompanyPayId(companyPay.getId());
                payPointOfSale.setDoTime(timeService.getNow());
                payPointOfSale.setPointOfSale(companyDebt.getSecondPointOfSale());
                payPointOfSale.setMoney(companyDebt.getDebt());
                payPointOfSaleService.add(payPointOfSale);
            }else if("餐饮".equals(companyDebt.getPointOfSale())){
                /*设置ppos*/
                PayPointOfSale payPointOfSale = new PayPointOfSale();
                payPointOfSale.setCurrency(companyPay.getCurrency());
                payPointOfSale.setCompanyPayId(companyPay.getId());
                payPointOfSale.setDoTime(timeService.getNow());
                payPointOfSale.setPointOfSale(companyDebt.getPointOfSale());
                payPointOfSale.setMoney(companyDebt.getDebt());
                payPointOfSaleService.add(payPointOfSale);
            }
        }
        /*如果杂单有冲账，最后一条需要进行杂单冲账，针对定额结算修补产生的*/
        CompanyDebt companyDebtLast = companyDebtList.get(companyDebtList.size() - 1);
        if (companyDebtLast.getNotNullTmp()) {
            CompanyDebt companyDebt = new CompanyDebt(companyDebtLast);
            companyDebt.setDebt(-companyDebt.getDebt());
            if (companyDebt.getDebt() > 0) {
                companyDebt.setDescription("定额结算杂单");
            } else {
                companyDebt.setDescription("定额结算冲账");
            }
            /*设置营业部门消费，杂单都设置为房费，偷懒的做法，最好的做法是筛选出最大大于他的营业部门，然后取这个，但是有一定概率没有，又要重新查找companyDebt，太他妈麻烦了*/
            companyDebt.setOtherConsume(true);
            /*根据上一条生成ppos*/
            PayPointOfSale payPointOfSale = new PayPointOfSale();
            payPointOfSale.setCurrency(companyPay.getCurrency());
            payPointOfSale.setCompanyPayId(companyPay.getId());
            payPointOfSale.setDoTime(timeService.getNow());
            if(companyDebtLast.getPointOfSale().equals("餐饮")){
                payPointOfSale.setPointOfSale("餐饮");
                companyDebt.setSecondPointOfSale("餐饮");
                companyDebtLast.setSecondPointOfSale("餐饮");
            }else {
                payPointOfSale.setPointOfSale("房费");
                companyDebt.setSecondPointOfSale("房费");
                companyDebtLast.setSecondPointOfSale("房费");
            }
            payPointOfSale.setMoney(companyDebtLast.getDebt());
            if(!companyDebtLast.getPointOfSale().equals("餐饮")) {//最后一条如果是餐饮的话就在上边的遍历里加上了，这里不用再加了

                payPointOfSaleService.add(payPointOfSale);
            }
            companyDebtService.add(companyDebt);
        }
        companyDebtHistoryService.add(companyDebtHistoryList);
        /*判断一下是不是转单位*/
        debtPayService.parseCurrency(currency, currencyAdd, pay, null, null, companyName + "单位结算转入", serialService.getCompanyPaySerial(), null, null);
        /*如果是用余额结算的，还要在余额表里记录一下*/
        if (companyPost.getNotNullRemainPay()) {
            CompanyMoney companyMoney = new CompanyMoney();
            companyMoney.setDoTime(timeService.getNow());
            companyMoney.setCategory("支付");
            companyMoney.setCompany(companyName);
            companyMoney.setCompanyPaySerial(serialService.getCompanyPaySerial());
            companyMoney.setCurrency(currency);
            companyMoney.setMoney(-pay);
            companyMoney.setUserId(userService.getCurrentUser());
            companyMoneyService.add(companyMoney);
        }
        /*生成payPointOfSale*/
        if(!paySerial.toString().equals("")) {
            List<DebtHistory> debtHistoryList = debtHistoryService.getListByCompanyPaid(paySerial.substring(0, paySerial.length() - 1));
            for (DebtHistory debtHistory : debtHistoryList) {
                if (debtHistory.getNotNullConsume() != 0.0) {
                    PayPointOfSale payPointOfSale = new PayPointOfSale();
                    payPointOfSale.setCurrency(companyPay.getCurrency());
                    payPointOfSale.setCompanyPayId(companyPay.getId());
                    payPointOfSale.setDoTime(timeService.getNow());
                    if(debtHistory.getPaySerial().contains("ck")){
                        payPointOfSale.setPointOfSale("餐饮");
                    }else {
                        payPointOfSale.setPointOfSale(debtHistory.getPointOfSale());
                    }
                    payPointOfSale.setMoney(debtHistory.getNotNullConsume());
                    payPointOfSaleService.add(payPointOfSale);
                }
            }
        }
        userLogService.addUserLog("单位名称:" + companyName + " 结算:" + debt, "实付：" + pay, userLogService.company, userLogService.companyPay, companyName);
        /*生成结算报表
        * 1.单位名称
        * 2.结算金额
        * 3.操作员
        * 4.币种信息
        * 5.实付金额
        * fields
        * 1.结账序列号
        * 2.挂账金额
        * 3.挂账时间
        * 4.挂账部门
        * 5.备注
        * 6，操作员
        * */

        return reportService.generateReport(templateList, new String[]{companyName, ifNotNullGetString(debt), ifNotNullGetString(userService.getCurrentUser()), currency + "/" + ifNotNullGetString(currencyAdd), ifNotNullGetString(pay)}, "companyPay", "pdf");
    }

    /**
     * 精确结算
     *
     * @return
     */
    @RequestMapping(value = "companyTargetPay")
    @Transactional(rollbackFor = Exception.class)
    public Integer companyTargetPay(@RequestBody CompanyPost companyPost) throws Exception {
        timeService.setNow();
        serialService.setCompanyPaySerial();
        String companyName = companyPost.getCompanyName();
        String remark = companyPost.getRemark();
        Double debt = companyPost.getDebt();
        Double pay = companyPost.getPay();
        List<DebtHistory> debtHistoryList = companyPost.getDebtHistoryList();
        Map<String, Double> paySerialMap = companyPost.getPaySerialMap();
        String currency = companyPost.getCurrencyPost().getCurrency();
        String currencyAdd = companyPost.getCurrencyPost().getCurrencyAdd();
        /*更新单位金额*/
        Company company = companyService.getByName(companyName);
        if (company == null) {
            throw new Exception("该单位不存在");
        }
        company.setDebt(company.getDebt() - debt);
        companyService.update(company);
        /*生成结账记录*/
        CompanyPay companyPay = new CompanyPay();
        companyPay.setCompany(companyName);
        companyPay.setRemark(remark);
        companyPay.setCurrencyAdd(currencyAdd);
        companyPay.setCurrency(currency);
        companyPay.setCompanyPaySerial(serialService.getCompanyPaySerial());
        companyPay.setDebt(debt);
        companyPay.setPay(pay);
        companyPay.setDoneTime(timeService.getNow());
        companyPay.setUserId(userService.getCurrentUser());
        companyPayService.add(companyPay);
        List<FieldTemplate> templateList = new ArrayList<>();
        CompanyDebtHistory companyDebtHistory;
        List<CompanyDebtHistory> companyDebtHistoryList = new ArrayList<>();
        /*历史账务全部设置为已结*/
        for (DebtHistory debtHistory : debtHistoryList) {
            if (debtHistory.getPaySerial() != null) {//有账务历史的说明也有结账序列号，直接更新
                debtHistoryService.setPaidById(debtHistory.getId());
            } else {//老系统转进来的没有结账序列号，需要根据id删除一下账务历史
                CompanyDebt companyDebt = companyDebtService.getById(debtHistory.getId());
                companyDebtHistory = new CompanyDebtHistory(companyDebt);
                companyDebtHistory.setDoneTime(timeService.getNow());
                companyDebtHistory.setCompanyPaySerial(serialService.getCompanyPaySerial());
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(timeService.dateToStringLong(companyDebt.getDoTime()));
                fieldTemplate.setField2(companyDebt.getPaySerial());
                fieldTemplate.setField3(szMath.ifNotNullGetString(companyDebt.getDebt()));
                fieldTemplate.setField4(companyDebt.getPointOfSale());
                fieldTemplate.setField5(companyDebt.getDescription());
                fieldTemplate.setField6(companyDebt.getUserId());
                templateList.add(fieldTemplate);
                companyDebtService.delete(companyDebt);
                companyDebtHistoryList.add(companyDebtHistory);
            }
            if (debtHistory.getNotNullConsume() != 0.0) {//没消费就继续
                PayPointOfSale payPointOfSale = new PayPointOfSale();
                payPointOfSale.setCompanyPayId(companyPay.getId());
                payPointOfSale.setCurrency(currency);
                payPointOfSale.setDoTime(timeService.getNow());
                if(debtHistory.getPaySerial().contains("ck")){
                    payPointOfSale.setPointOfSale("餐饮");
                }else {
                    payPointOfSale.setPointOfSale(debtHistory.getPointOfSale());
                }
                payPointOfSale.setMoney(debtHistory.getNotNullConsume());
                payPointOfSaleService.add(payPointOfSale);
            }
        }
        /*循环map*/
        for (String paySerial : paySerialMap.keySet()) {
            Double sign = 0.0;//标志，看看最后减完要是小于零就说明不对了
            List<CompanyDebt> companyDebtList = companyDebtService.getByNameSerial(companyName, paySerial);
            for (CompanyDebt companyDebt : companyDebtList) {
                sign += companyDebt.getDebt();
                /*大不大于都要进历史*/
                companyDebtHistory = new CompanyDebtHistory(companyDebt);
                companyDebtHistory.setDoneTime(timeService.getNow());
                companyDebtHistory.setCompanyPaySerial(serialService.getCompanyPaySerial());
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(timeService.dateToStringLong(companyDebt.getDoTime()));
                fieldTemplate.setField2(companyDebt.getPaySerial());
                fieldTemplate.setField3(szMath.ifNotNullGetString(companyDebt.getDebt()));
                fieldTemplate.setField4(companyDebt.getPointOfSale());
                fieldTemplate.setField5(companyDebt.getDescription());
                fieldTemplate.setField6(companyDebt.getUserId());
                templateList.add(fieldTemplate);
                /*小于等于就删了，大于就更新*/
                if (companyDebt.getDebt() <= paySerialMap.get(paySerial)) {
                    /*删除之前的单位账*/
                    companyDebtService.delete(companyDebt);
                } else {
                    /*更新余额*/
                    companyDebt.setDebt(companyDebt.getDebt() - paySerialMap.get(paySerial));
                    companyDebtHistory.setDebt(paySerialMap.get(paySerial));//进历史的数据略作修改
                    companyDebtService.update(companyDebt);
                }
                companyDebtHistoryList.add(companyDebtHistory);
            }
            if (sign < paySerialMap.get(paySerial)) {
                throw new Exception("结账序列号:" + paySerial + "可能存在分单，选中明细超过该笔分单金额，无法结算，请在挂账记录里结算");
            }
        }
        companyDebtHistoryService.add(companyDebtHistoryList);
        /*如果是用余额结算的，还要在余额表里记录一下*/
        if (companyPost.getNotNullRemainPay()) {
            CompanyMoney companyMoney = new CompanyMoney();
            companyMoney.setDoTime(timeService.getNow());
            companyMoney.setCategory("支付");
            companyMoney.setCompany(companyName);
            companyMoney.setCompanyPaySerial(serialService.getCompanyPaySerial());
            companyMoney.setCurrency(currency);
            companyMoney.setMoney(-pay);
            companyMoney.setUserId(userService.getCurrentUser());
            companyMoneyService.add(companyMoney);
        }
        /*判断一下是不是转单位*/
        debtPayService.parseCurrency(currency, currencyAdd, pay, null, null, companyName + "单位结算转入", serialService.getCompanyPaySerial(), null, null);
        userLogService.addUserLog("单位名称:" + companyName + " 结算:" + debt, "实付：" + pay, userLogService.company, userLogService.companyPay, companyName);
        /*生成结算报表
        * 1.单位名称
        * 2.结算金额
        * 3.操作员
        * 4.币种信息
        * 5.实付金额
        * fields
        * 1.结账序列号
        * 2.挂账金额
        * 3.挂账时间
        * 4.挂账部门
        * 5.备注
        * 6，操作员
        * */

        return reportService.generateReport(templateList, new String[]{companyName, ifNotNullGetString(debt), ifNotNullGetString(userService.getCurrentUser()), currency + "/" + ifNotNullGetString(currencyAdd), ifNotNullGetString(pay)}, "companyPay", "pdf");
    }
}
