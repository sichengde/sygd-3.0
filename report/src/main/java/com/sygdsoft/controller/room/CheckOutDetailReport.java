package com.sygdsoft.controller.room;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.model.room.CheckOutDetailReturn;
import com.sygdsoft.model.room.CheckOutDetailRow;
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
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    CheckInHistoryService checkInHistoryService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    CheckOutGroupService checkOutGroupService;
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
    /*TODO:哑房结算不参与*/
    @RequestMapping(value = "checkOutDetailReport")
    public Integer checkOutDetailReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        String userId = reportJson.getUserId();
        String currencyQuery=reportJson.getCurrency();
        if ("".equals(userId)) {
            userId = null;
        }
        String format = reportJson.getFormat();
        timeService.setNow();
        List<FieldTemplate> templateList = new ArrayList<>();
        List<DebtPay> debtPayList = debtPayService.getList(userId, currencyQuery,beginTime, endTime, "done_time");
        FieldTemplate fieldTemplate;
        Double total = 0.0;//总的结账金额
        Map<String, Double> currencyMap = new HashMap<>();
        String lastSerial = null;//上一个的账号，每次循环判断，如果相同即是分单
        for (DebtPay debtPay : debtPayList) {
            if (!debtPay.getPaySerial().equals(lastSerial)) {
                lastSerial = debtPay.getPaySerial();
                fieldTemplate = new FieldTemplate();//第一行是主账号(现在改成结账时间了)
                //先判断是在店的还是离店的
                if(debtPay.getCheckOutSerial()==null){//没离店
                    if (debtPay.getGroupAccount() != null) {
                        CheckInGroup checkInGroup=checkInGroupService.getByGroupAccount(debtPay.getGroupAccount());
                        fieldTemplate.setField1(timeService.dateToStringLong(checkInGroup.getReachTime()));
                        fieldTemplate.setField2(timeService.dateToStringLong(checkInGroup.getLeaveTime()));
                        fieldTemplate.setField3(checkInGroup.getName());
                    } else if(debtPay.getSelfAccount()!=null){//如果都是空的话就是零售，不参与客房结账统计表
                        CheckIn checkIn=checkInService.getBySelfAccount(debtPay.getSelfAccount());
                        List<CheckInGuest> checkInGuestList=checkInGuestService.getListByRoomId(debtPay.getRoomId());
                        fieldTemplate.setField1(timeService.dateToStringLong(checkIn.getReachTime()));
                        fieldTemplate.setField2(timeService.dateToStringLong(checkIn.getLeaveTime()));
                        fieldTemplate.setField3(checkInGuestService.listToStringName(checkInGuestList));
                    }else {
                        continue;
                    }
                }else {
                    if (debtPay.getGroupAccount() != null) {
                        CheckOutGroup checkOutGroup=checkOutGroupService.getByCheckOutSerial(debtPay.getCheckOutSerial());
                        fieldTemplate.setField1(timeService.dateToStringLong(checkOutGroup.getReachTime()));
                        fieldTemplate.setField2(timeService.dateToStringLong(checkOutGroup.getLeaveTime()));
                        fieldTemplate.setField3(checkOutGroup.getName());
                    } else {
                        CheckInHistoryLog checkInHistoryLog=checkInHistoryLogService.getBySelfAccountAndCheckOutSerial(debtPay.getSelfAccount(),debtPay.getCheckOutSerial());
                        List<CheckInHistory> checkInHistoryList=checkInHistoryService.getListBySelfAccount(checkInHistoryLog.getSelfAccount());
                        fieldTemplate.setField1(timeService.dateToStringLong(checkInHistoryLog.getReachTime()));
                        fieldTemplate.setField2(timeService.dateToStringLong(checkInHistoryLog.getLeaveTime()));
                        fieldTemplate.setField3(checkInHistoryService.listToStringName(checkInHistoryList));
                    }
                }
                fieldTemplate.setField6("true");
                templateList.add(fieldTemplate);
                fieldTemplate = new FieldTemplate();//第二行是来店时间，预离时间，姓名
                fieldTemplate.setField1(timeService.dateToStringLong(debtPay.getDoneTime()));
                if (debtPay.getGroupAccount() != null) {
                    fieldTemplate.setField2(ifNotNullGetString(debtPay.getGroupAccount()));
                    fieldTemplate.setField3("公司:" + debtPay.getCompany());
                } else {
                    fieldTemplate.setField2(ifNotNullGetString(debtPay.getSelfAccount()));
                }
                templateList.add(fieldTemplate);
                Query query=new Query();
                query.setCondition("consume is not null and pay_serial = " + util.wrapWithBrackets(debtPay.getPaySerial()));
                query.setOrderByList(new String[]{"roomId", "pointOfSale", "doTime"});
                List<DebtHistory> debtHistoryList = debtHistoryService.get(query);
                debtHistoryList = debtHistoryService.mergeDebtHistory(debtHistoryList);
                for (DebtHistory debtHistory : debtHistoryList) {
                    fieldTemplate = new FieldTemplate();//循环输出结账明细
                    fieldTemplate.setField1(timeService.longFormat.format(debtHistory.getDoTime()));
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
        List<String> paramList = new ArrayList<>();
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
        String[] param = new String[paramList.size()];
        paramList.toArray(param);
        return reportService.generateReport(templateList, param, "checkOutDetail", format);
    }

    /**
     * 手机端显示结账明细表
     */
    @RequestMapping(value = "checkOutDetailMobile")
    public CheckOutDetailReturn checkOutDetailMobile(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        String userId = reportJson.getUserId();
        if ("".equals(userId)) {
            userId = null;
        }
        timeService.setNow();
        List<CheckOutDetailRow> checkOutDetailRowList = new ArrayList<>();
        Query query = new Query();
        if (userId == null) {
            query.setCondition("done_time >" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime)) + " and done_time < " + util.wrapWithBrackets(timeService.dateToStringLong(endTime)));
        } else {
            query.setCondition("done_time >" + util.wrapWithBrackets(timeService.dateToStringLong(beginTime)) + " and done_time < " + util.wrapWithBrackets(timeService.dateToStringLong(endTime)) + " and user_id=" + util.wrapWithBrackets(userId));
        }
        query.setOrderByList(new String[]{"doneTime"});
        List<DebtPay> debtPayList = debtPayService.get(query);
        CheckOutDetailRow checkOutDetailRow;
        Double total = 0.0;//总的结账金额
        Map<String, Double> currencyMap = new HashMap<>();
        String lastSerial = null;//上一个的账号，每次循环判断，如果相同即是分单
        for (DebtPay debtPay : debtPayList) {
            if (!debtPay.getPaySerial().equals(lastSerial)) {
                lastSerial = debtPay.getPaySerial();
                checkOutDetailRow = new CheckOutDetailRow();//第一行是主账号(现在改成结账时间了)
                checkOutDetailRow.setDoTime(timeService.dateToStringLong(debtPay.getDoneTime()));
                if (debtPay.getGroupAccount() != null) {
                    checkOutDetailRow.setCurrency(debtPay.getGroupAccount());
                    checkOutDetailRow.setDescription("公司:" + debtPay.getCompany());
                } else {
                    checkOutDetailRow.setCurrency(debtPay.getSelfAccount());
                }
                checkOutDetailRowList.add(checkOutDetailRow);
                query.setCondition("consume is not null and pay_serial = " + util.wrapWithBrackets(debtPay.getPaySerial()));
                query.setOrderByList(new String[]{"roomId", "pointOfSale", "doTime"});
                List<DebtHistory> debtHistoryList = debtHistoryService.get(query);
                debtHistoryList = debtHistoryService.mergeDebtHistory(debtHistoryList);
                for (DebtHistory debtHistory : debtHistoryList) {
                    checkOutDetailRow = new CheckOutDetailRow();//循环输出结账明细
                    checkOutDetailRow.setDoTime(timeService.dateToStringLong(debtHistory.getDoTime()));
                    checkOutDetailRow.setMoney(ifNotNullGetString(debtHistory.getConsume()));
                    checkOutDetailRow.setDescription(debtHistory.getDescription());
                    checkOutDetailRow.setRoomId(debtHistory.getRoomId());
                    checkOutDetailRowList.add(checkOutDetailRow);
                }
            }
            checkOutDetailRow = new CheckOutDetailRow();//最后一行是结账信息，俗称小计
            checkOutDetailRow.setDoTime("小计");
            checkOutDetailRow.setCurrency(debtPay.getCurrency());
            checkOutDetailRow.setDescription(debtPay.getDebtCategory());
            checkOutDetailRow.setMoney(String.valueOf(debtPay.getDebtMoney()));
            checkOutDetailRow.setUserId(debtPay.getUserId());
            checkOutDetailRowList.add(checkOutDetailRow);
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
        String out = "";
        /*币种分析*/
        for (String s : currencyMap.keySet()) {
            out += s + "/" + currencyMap.get(s) + " ";
        }
        CheckOutDetailReturn checkOutDetailReturn = new CheckOutDetailReturn();
        checkOutDetailReturn.setCheckOutDetailRowList(checkOutDetailRowList);
        checkOutDetailReturn.setRemark("结账合计" + ifNotNullGetString(total) + ",币种分析:" + out);
        return checkOutDetailReturn;
    }
}
