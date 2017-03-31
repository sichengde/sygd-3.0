package com.sygdsoft.controller;

import com.sygdsoft.controller.common.ExchangeUserReport;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.ShenBeiTiChengMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.model.room.ExchangeUserSmallJQReturn;
import com.sygdsoft.model.room.ExchangeUserSmallJQRow;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import org.joda.time.chrono.LimitChronology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by 舒展 on 2017-03-23.
 */
@RestController
public class ShenBeiTiChengController {
    @Autowired
    ReportService reportService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    TimeService timeService;
    @Autowired
    Util util;
    @Autowired
    SzMath szMath;
    @Autowired
    ExchangeUserReport exchangeUserReport;
    @Autowired
    RoomShopDetailService roomShopDetailService;
    @Autowired
    DebtService debtService;
    @Autowired
    ShenBeiTiChengMapper shenBeiTiChengMapper;

    /**
     * 报表说明
     * field1:房号
     * field2:时间
     * field3:房价
     * field4:押金
     * field5:币种
     *
     * @param reportJson
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getShenBeiTiCheng")
    public Integer getTiCheng(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        String user = reportJson.getUserId();
        List<FieldTemplate> fieldTemplateList = new ArrayList<>();
        FieldTemplate fieldTemplate;
        /*分割线*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField8("true");
        fieldTemplateList.add(fieldTemplate);
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField6("开房情况：");//第一行
        fieldTemplate.setField7("开房时间");//第一行
        fieldTemplateList.add(fieldTemplate);
        Query query = new Query();
        String condition = "reach_time>" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime)) + " and reach_time<" + util.wrapWithBrackets(timeService.dateToStringLong(endTime));
        if (user != null) {
            condition += " and user_id=" + util.wrapWithBrackets(user);
        }
        query.setCondition(condition);
        query.setOrderByList(new String[]{"reachTime"});
        List<CheckInIntegration> checkInIntegrationList = checkInIntegrationService.get(query);
        Map<String, Double> currencyMap = new HashMap<>();
        Double totalDeposit = 0.0;
        Double totalRoomPrice = 0.0;
        Double totalFinal = 0.0;
        for (CheckInIntegration checkInIntegration : checkInIntegrationList) {
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(checkInIntegration.getRoomId());
            fieldTemplate.setField2(timeService.dateToStringLong(checkInIntegration.getReachTime()));
            fieldTemplate.setField3(szMath.formatTwoDecimal(checkInIntegration.getFinalRoomPrice()));
            fieldTemplateList.add(fieldTemplate);
            totalRoomPrice += checkInIntegration.getFinalRoomPrice();
            query = new Query();
            condition = "description=\'开房押金录入\' and self_account=" + util.wrapWithBrackets(checkInIntegration.getSelfAccount()) + " and ifNull(deposit,0)>0";
            query.setCondition(condition);
            List<DebtIntegration> debtIntegrationList = debtIntegrationService.get(query);

            for (DebtIntegration debtIntegration : debtIntegrationList) {
                if ("押金".equals(debtIntegration.getCurrency())) {
                    totalDeposit += debtIntegration.getDeposit();
                } else {
                    String currency = debtIntegration.getCurrency();
                    Double deposit = debtIntegration.getDeposit();
                    fieldTemplate = new FieldTemplate();
                    fieldTemplate.setField1("实付:");
                    fieldTemplate.setField4(szMath.formatTwoDecimal(deposit));
                    fieldTemplate.setField5(currency);
                    fieldTemplateList.add(fieldTemplate);
                    if (currencyMap.containsKey(currency)) {
                        currencyMap.put(currency, currencyMap.get(currency) + deposit);
                    } else {
                        currencyMap.put(currency, deposit);
                    }
                }
            }
        }
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField6("续住情况：");//第一行
        fieldTemplateList.add(fieldTemplate);
        /*续住情况*/
        query = new Query();
        condition = "ifNull(deposit,0)>0 and description=\'押金单独补录\' and do_time>" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime)) + " and do_time<" + util.wrapWithBrackets(timeService.dateToStringLong(endTime));
        query.setCondition(condition);
        query.setOrderByList(new String[]{"roomId"});
        List<DebtIntegration> debtIntegrationList1 = debtIntegrationService.get(query);
        String lastRoomId = "";
        for (DebtIntegration debtIntegration : debtIntegrationList1) {
            if (lastRoomId.equals(debtIntegration.getRoomId())) {
/*算押金*/
                if ("押金".equals(debtIntegration.getCurrency())) {
                    totalDeposit += debtIntegration.getDeposit();
                } else {
                    String currency = debtIntegration.getCurrency();
                    Double deposit = debtIntegration.getDeposit();
                    fieldTemplate = new FieldTemplate();
                    fieldTemplate.setField1("实付:");
                    fieldTemplate.setField4(szMath.formatTwoDecimal(deposit));
                    fieldTemplate.setField5(currency);
                    fieldTemplateList.add(fieldTemplate);
                    if (currencyMap.containsKey(currency)) {
                        currencyMap.put(currency, currencyMap.get(currency) + deposit);
                    } else {
                        currencyMap.put(currency, deposit);
                    }
                }
            }else {
                /*算房费*/
                CheckInIntegration checkInIntegration = checkInIntegrationService.getBySelfAccount(debtIntegration.getSelfAccount());
                fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(checkInIntegration.getRoomId());
                fieldTemplate.setField2(timeService.dateToStringLong(debtIntegration.getDoTime()));
                fieldTemplate.setField3(szMath.formatTwoDecimal(checkInIntegration.getFinalRoomPrice()));
                fieldTemplateList.add(fieldTemplate);
                totalRoomPrice += checkInIntegration.getFinalRoomPrice();
                /*算押金*/
                if ("押金".equals(debtIntegration.getCurrency())) {
                    totalDeposit += debtIntegration.getDeposit();
                } else {
                    String currency = debtIntegration.getCurrency();
                    Double deposit = debtIntegration.getDeposit();
                    fieldTemplate = new FieldTemplate();
                    fieldTemplate.setField1("实付:");
                    fieldTemplate.setField4(szMath.formatTwoDecimal(deposit));
                    fieldTemplate.setField5(currency);
                    fieldTemplateList.add(fieldTemplate);
                    if (currencyMap.containsKey(currency)) {
                        currencyMap.put(currency, currencyMap.get(currency) + deposit);
                    } else {
                        currencyMap.put(currency, deposit);
                    }
                }
            }
        }
        /*分割线*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField8("true");
        fieldTemplateList.add(fieldTemplate);
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField6("实付小计：");//第一行
        fieldTemplateList.add(fieldTemplate);
        for (String s : currencyMap.keySet()) {
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(s + ":");
            fieldTemplate.setField3(szMath.formatTwoDecimal(currencyMap.get(s)));
            fieldTemplateList.add(fieldTemplate);
        }
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("总房价");
        fieldTemplate.setField3(szMath.formatTwoDecimal(totalRoomPrice));
        totalFinal += totalRoomPrice;
        fieldTemplateList.add(fieldTemplate);
        /*分割线*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField8("true");
        fieldTemplateList.add(fieldTemplate);
        /*统计加收房租情况*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("加收房租：");//第一行
        fieldTemplateList.add(fieldTemplate);
        query = new Query();
        condition = "category=\'加收房租\' and description NOT LIKE \'加收房租(当日来当日走)%\' and done_time>" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime)) + " and done_time<" + util.wrapWithBrackets(timeService.dateToStringLong(endTime));
        if (user != null) {
            condition += " and user_id=" + util.wrapWithBrackets(user);
        }
        query.setCondition(condition);
        query.setOrderByList(new String[]{"doneTime"});
        List<DebtIntegration> debtIntegrationList = debtIntegrationService.get(query);
        Double total = 0.0;
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(debtIntegration.getRoomId());
            fieldTemplate.setField2(timeService.dateToStringLong(debtIntegration.getDoneTime()));
            fieldTemplate.setField3(szMath.formatTwoDecimal(debtIntegration.getConsume()));
            fieldTemplateList.add(fieldTemplate);
            total += debtIntegration.getConsume();
        }
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("总计:");
        fieldTemplate.setField3(szMath.formatTwoDecimal(total));
        totalFinal += total;
        fieldTemplateList.add(fieldTemplate);
        /*分割线*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField8("true");
        fieldTemplateList.add(fieldTemplate);
        /*统计房吧明细*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("房吧零售：");//第一行
        fieldTemplateList.add(fieldTemplate);
        List<RoomShopDetailWithCurrency> roomShopDetailWithCurrencyList=shenBeiTiChengMapper.getRoomShop(user, beginTime, endTime);
        total = 0.0;
        currencyMap = new HashMap<>();
        for (RoomShopDetailWithCurrency roomShopDetailWithCurrency : roomShopDetailWithCurrencyList) {
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(roomShopDetailWithCurrency.getItem());
            fieldTemplate.setField2(roomShopDetailWithCurrency.getNum() + roomShopDetailWithCurrency.getUnit());
            if(roomShopDetailWithCurrency.getCurrency()!=null) {
                String currency=roomShopDetailWithCurrency.getCurrency();
                Double money=roomShopDetailWithCurrency.getTotalMoney();
                fieldTemplate.setField4(szMath.formatTwoDecimal(roomShopDetailWithCurrency.getTotalMoney()));
                fieldTemplate.setField5(szMath.ifNotNullGetString(roomShopDetailWithCurrency.getCurrency()));
                if (currencyMap.containsKey(currency)) {
                    currencyMap.put(currency, currencyMap.get(currency) + money);
                } else {
                    currencyMap.put(currency, money);
                }
            }else {
                fieldTemplate.setField3(szMath.formatTwoDecimal(roomShopDetailWithCurrency.getTotalMoney()));
            }
            fieldTemplateList.add(fieldTemplate);
            total += szMath.formatTwoDecimalReturnDouble(roomShopDetailWithCurrency.getTotalMoney());
        }
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("总计:");
        fieldTemplate.setField3(szMath.formatTwoDecimal(total));
        totalFinal += total;
        fieldTemplateList.add(fieldTemplate);
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField6("零售小计：");//第一行
        fieldTemplateList.add(fieldTemplate);
        for (String s : currencyMap.keySet()) {
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(s + ":");
            fieldTemplate.setField3(szMath.formatTwoDecimal(currencyMap.get(s)));
            fieldTemplateList.add(fieldTemplate);
        }
        /*分割线*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField8("true");
        fieldTemplateList.add(fieldTemplate);
        /*统计今日不离店*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField6("预付房租未离店：");//第一行
        fieldTemplate.setField7("预离时间");//第一行
        fieldTemplateList.add(fieldTemplate);
        query = new Query();
        condition = "leave_time>" + util.wrapWithBrackets(timeService.dateToStringLong(endTime))+" and reach_time<"+util.wrapWithBrackets(timeService.dateToStringLong(timeService.getMinTime(beginTime)));
        query.setCondition(condition);
        List<CheckIn> checkInList = checkInService.get(query);
        total = 0.0;
        for (CheckIn checkIn : checkInList) {
            query = new Query();
            condition = " self_account=" + util.wrapWithBrackets(checkIn.getSelfAccount()) + " and ifNull(deposit,0)>0 and do_time>" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime))+" and do_time<"+util.wrapWithBrackets(timeService.dateToStringLong(endTime));
            query.setCondition(condition);
            List<Debt> debtList=debtService.get(query);
            if (debtList.size()==0) {
                fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(checkIn.getRoomId());
                fieldTemplate.setField2(timeService.dateToStringLong(checkIn.getLeaveTime()));
                fieldTemplate.setField3(szMath.formatTwoDecimal(checkIn.getFinalRoomPrice()));
                fieldTemplateList.add(fieldTemplate);
                total += checkIn.getFinalRoomPrice();
            }
        }
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("已收房租:");
        fieldTemplate.setField3(szMath.formatTwoDecimal(total));
        totalFinal += total;
        fieldTemplateList.add(fieldTemplate);
        /*分割线*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField8("true");
        fieldTemplateList.add(fieldTemplate);
        /*四项总计*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("四项总计:");
        fieldTemplate.setField3(szMath.formatTwoDecimal(totalFinal));
        fieldTemplateList.add(fieldTemplate);
        /*总押金*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("收取押金:");
        fieldTemplate.setField3(szMath.formatTwoDecimal(totalDeposit));
        fieldTemplateList.add(fieldTemplate);
        /*总押金*/
        fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("在店押金:");
        fieldTemplate.setField3(szMath.formatTwoDecimal(shenBeiTiChengMapper.getDepositMoneyAll()));
        fieldTemplateList.add(fieldTemplate);
        String[] parameters = new String[3];
        parameters[0] = timeService.dateToStringLong(beginTime);
        parameters[1] = timeService.dateToStringLong(endTime);
        if (user == null) {
            user = "全部";
        }
        parameters[2] = user;
        return reportService.generateReport(fieldTemplateList, parameters, "wutong", "pdf");
    }
}
