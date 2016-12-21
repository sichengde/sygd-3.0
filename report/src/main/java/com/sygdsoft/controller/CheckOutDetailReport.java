package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.model.DebtPay;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016/6/23 0023.
 * 结账明细表，不需要点击生成子报表，所以就返回个报表索引即可
 */
@RestController
public class CheckOutDetailReport {
    @Autowired
    TimeService timeService;
    @Autowired
    Util util;
    @Autowired
    UserService userService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    ReportService reportService;
    /*
    * parameters
    * 1.结账合计
    * 2.制表人
    * 3.起始时间
    * 4.终止时间
    * 5.币种分析
    * fields
    * 1.时间（主账号）
    * 2.币种
    * 3.描述
    * 4.金额
    * 5.房号
    * 6.逻辑字段，用来判断什么时候显示主账号上边的横线
    * 7.操作员
    * */

    @RequestMapping(value = "checkOutDetailReport")
    public Integer checkOutDetailReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        String userId=reportJson.getUserId();
        if("".equals(userId)){
            userId=null;
        }
        String format=reportJson.getFormat();
        timeService.setNow();
        List<FieldTemplate> templateList = new ArrayList<>();
        Query query = new Query();
        if(userId==null){
            query.setCondition("done_time >" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime)) + " and done_time < " + util.wrapWithBrackets(timeService.dateToStringLong(endTime)));
        }else {
            query.setCondition("done_time >" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime)) + " and done_time < " + util.wrapWithBrackets(timeService.dateToStringLong(endTime)) + " and user_id=" + util.wrapWithBrackets(userId));
        }
        query.setOrderByList(new String[]{"doneTime"});
        List<DebtPay> debtPayList = debtPayService.get(query);
        FieldTemplate fieldTemplate;
        Double total = 0.0;//总的结账金额
        Map<String, Double> currencyMap = new HashMap<>();
        String lastSerial = null;//上一个的账号，每次循环判断，如果相同即是分单
        for (DebtPay debtPay : debtPayList) {
            if (!debtPay.getPaySerial().equals(lastSerial)) {
                lastSerial = debtPay.getPaySerial();
                fieldTemplate = new FieldTemplate();//第一行是主账号
                if (debtPay.getGroupAccount() != null) {
                    fieldTemplate.setField1("主账号:" + debtPay.getGroupAccount());
                    fieldTemplate.setField2("公司:" + debtPay.getCompany());
                } else {
                    fieldTemplate.setField1("主账号:" + debtPay.getSelfAccount());
                }
                fieldTemplate.setField6("true");
                templateList.add(fieldTemplate);
                query.setCondition("consume is not null and pay_serial = " + util.wrapWithBrackets(debtPay.getPaySerial()));
                query.setOrderByList(new String[]{"roomId","pointOfSale","doTime"});
                List<DebtHistory> debtHistoryList = debtHistoryService.get(query);
                debtHistoryList=debtHistoryService.mergeDebtHistory(debtHistoryList);
                for (DebtHistory debtHistory : debtHistoryList) {
                    fieldTemplate = new FieldTemplate();//循环输出结账明细
                    fieldTemplate.setField1(timeService.longFormat.format(debtHistory.getDoneTime()));
                    fieldTemplate.setField4(String.valueOf(debtHistory.getConsume()));
                    fieldTemplate.setField3(debtHistory.getDescription());
                    fieldTemplate.setField5(debtHistory.getRoomId());
                    templateList.add(fieldTemplate);
                }
            }
            fieldTemplate = new FieldTemplate();//最后一行是结账信息，俗称小计
            fieldTemplate.setField1("小计");
            fieldTemplate.setField2(debtPay.getCurrency());
            fieldTemplate.setField3(debtPay.getDebtCategory());
            fieldTemplate.setField4(String.valueOf(debtPay.getDebtMoney()));
            fieldTemplate.setField7(debtPay.getUserId());
            templateList.add(fieldTemplate);
            /*统计该账单*/
            total = total + debtPay.getDebtMoney();
            Double tempDouble;
            String currency = debtPay.getCurrency();
            if (currencyMap.containsKey(currency)) {
                tempDouble = currencyMap.get(debtPay.getCurrency());
                tempDouble += debtPay.getDebtMoney();
                currencyMap.put(currency, tempDouble);
            } else {
                currencyMap.put(currency, debtPay.getDebtMoney());
            }
        }
        List<String> paramList=new ArrayList<>();
        paramList.add(ifNotNullGetString(total));
        paramList.add(userService.getCurrentUser());
        paramList.add(timeService.dateToStringLong(beginTime));
        paramList.add(timeService.dateToStringLong(endTime));
        String out = "";
        /*币种分析*/
        for (String s : currencyMap.keySet()) {
            out += s + "/" + currencyMap.get(s) + " ";
        }
        paramList.add(out);
        String[] param=new String[paramList.size()];
        paramList.toArray(param);
        return reportService.generateReport(templateList, param, "checkOutDetail",format);
    }
}
