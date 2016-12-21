package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.CurrencyPost;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Ejb3TransactionAnnotationParser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@RestController
public class SaunaPayController {
    @Autowired
    SaunaPayService saunaPayService;
    @Autowired
    SaunaInService saunaInService;
    @Autowired
    SaunaInHistoryService saunaInHistoryService;
    @Autowired
    SaunaDetailService saunaDetailService;
    @Autowired
    SaunaDetailHistoryService saunaDetailHistoryService;
    @Autowired
    SerialService serialService;
    @Autowired
    TimeService timeService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "saunaPayGet")
    public List<SaunaPay> saunaPayGet(@RequestBody Query query) throws Exception {
        return saunaPayService.get(query);
    }
    /**
     * 桑拿结算
     */
    @RequestMapping(value = "saunaOut")
    @Transactional(rollbackFor = Exception.class)
    public Integer saunaOut(@RequestBody SaunaOut saunaOut)throws Exception{
        serialService.setSaunaOutSerial();
        timeService.setNow();
        List<String> ringList=saunaOut.getRingList();
        List<CurrencyPost> currencyPayList=saunaOut.getCurrencyPayList();
        List<SaunaIn> saunaInList=new ArrayList<>();
        List<SaunaDetail> saunaDetailList=new ArrayList<>();
        List<SaunaInHistory> saunaInHistoryList=new ArrayList<>();
        List<SaunaDetailHistory> saunaDetailHistoryList=new ArrayList<>();
        List<FieldTemplate> fieldTemplateList=new ArrayList<>();
        String ringString="";
        /*先看看是团队还是单人*/
        for (String ring : ringList) {
            SaunaIn saunaIn=saunaInService.getByRing(ring);
            saunaInList.add(saunaIn);//要删除的入序列
            SaunaInHistory saunaInHistory=new SaunaInHistory(saunaIn);
            saunaInHistory.setDoneTime(timeService.getNow());
            saunaInHistory.setSaunaSerial(serialService.getSaunaOutSerial());
            saunaInHistory.setFinalPrice(saunaIn.getTotalPrice());
            saunaInHistoryList.add(saunaInHistory);//销售历史入序列
            List<SaunaDetail> saunaDetailRingList=saunaDetailService.getByRing(ring);//当前手牌的消费
            for (SaunaDetail saunaDetailRing : saunaDetailRingList) {
                SaunaDetailHistory saunaDetailHistory=new SaunaDetailHistory(saunaDetailRing);
                saunaDetailHistory.setSaunaSerial(serialService.getSaunaOutSerial());
                saunaDetailHistory.setDoneTime(timeService.getNow());
                saunaDetailHistory.setTotal(saunaDetailRing.getPrice()*saunaDetailRing.getNum());
                saunaDetailHistory.setAfterDiscount(saunaDetailHistory.getTotal());
                saunaDetailHistoryList.add(saunaDetailHistory);
                FieldTemplate fieldTemplate=new FieldTemplate();
                fieldTemplate.setField1(saunaDetailRing.getSaunaMenu());
                fieldTemplate.setField2(String.valueOf(saunaDetailRing.getPrice()));
                fieldTemplate.setField3(String.valueOf(saunaDetailRing.getNum()));
                fieldTemplate.setField4(String.valueOf(saunaDetailHistory.getAfterDiscount()));
                fieldTemplate.setField5(saunaDetailRing.getRing());
                fieldTemplateList.add(fieldTemplate);
            }
            saunaDetailList.addAll(saunaDetailRingList);
            ringString+=ring+",";
        }
        /*生成结账记录*/
        String changeDebt = "";
        Double totalMoney=0.0;
        /*结账记录，循环分单*/
        for (CurrencyPost currencyPost : currencyPayList) {
            String currency = currencyPost.getCurrency();
            String currencyAdd = currencyPost.getCurrencyAdd();
            Double money = currencyPost.getMoney();
            totalMoney+=money;
            SaunaPay saunaPay = new SaunaPay();
            saunaPay.setDoneTime(timeService.getNow());
            saunaPay.setPayMoney(money);
            saunaPay.setCurrency(currency);
            saunaPay.setCurrencyAdd(currencyAdd);
            saunaPay.setSaunaSerial(serialService.getSaunaOutSerial());
            saunaPay.setUserId(userService.getCurrentUser());
            saunaPayService.add(saunaPay);
            changeDebt += " 币种:" + currency + "/" + money;
            /*通过币种判断结账类型*/
            changeDebt+=debtPayService.parseCurrency(currency, currencyAdd, money, null,null,"退房",serialService.getSaunaOutSerial(),"桑拿");
        }
        saunaInService.delete(saunaInList);
        saunaInHistoryService.add(saunaInHistoryList);
        saunaDetailService.delete(saunaDetailList);
        saunaDetailHistoryService.add(saunaDetailHistoryList);
        userLogService.addUserLog("桑拿结牌:"+ringString,userLogService.sauna,userLogService.saunaPay,serialService.getSaunaOutSerial());
        /*生成报表
        * param
        * 1.酒店名称
        * 2.流水号
        * 3.结转信息
        * 4.合计金额
        * */
        String[] params=new String[]{otherParamService.getValueByName("酒店名称"),serialService.getSaunaOutSerial(),changeDebt, String.valueOf(totalMoney)};
        return reportService.generateReport(fieldTemplateList, params, "saunaOut","pdf");
    }
}
