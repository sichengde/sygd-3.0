package com.sygdsoft.controller.common;

import com.sygdsoft.model.*;
import com.sygdsoft.model.room.ExchangeUserSmallJQReturn;
import com.sygdsoft.model.room.ExchangeUserSmallJQRow;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

/**
 * Created by 舒展 on 2016-06-27.
 * 交班审核表
 */
@RestController
public class ExchangeUserReport {
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    BookMoneyService bookMoneyService;
    @Autowired
    VipService vipService;
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    ReportService reportService;
    @Autowired
    DebtService debtService;
    @Autowired
    VipIntegrationService vipIntegrationService;
    @Autowired
    RoomShopDetailService roomShopDetailService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    CompanyPayService companyPayService;
    @Autowired
    SzMath szMath;

    /**
     * 接待交班审核表
     */
    @RequestMapping(value = "exchangeUserReport",method = RequestMethod.POST)
    public ExchangeUserJQ exchangeUserReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        String userId=reportJson.getUserId();
        if("".equals(userId)){
            userId=null;
        }
        String format=reportJson.getFormat();
        timeService.setNow();
        List<FieldTemplate> templateList = new ArrayList<>();
        List<Currency> currencyList=currencyService.get(null);
        FieldTemplate fieldTemplate;
        for (Currency currency : currencyList) {
            fieldTemplate = new FieldTemplate();
            String currencyString=currency.getCurrency();
            fieldTemplate.setField1(currency.getCurrency());//币种
            fieldTemplate.setField2(szMath.ifNotNullGetString(debtPayService.getDebtMoney(userId,currencyString,false,beginTime, endTime)));//结算款
            fieldTemplate.setField3(szMath.ifNotNullGetString(debtHistoryService.getTotalDepositByUserCurrencyDate(userId,currencyString,beginTime,endTime)));//预付
            fieldTemplate.setField4(szMath.ifNotNullGetString(debtHistoryService.getTotalCancelDepositByUserCurrencyDate(userId, currencyString, beginTime, endTime)));//退预付
            fieldTemplate.setField5(szMath.ifNotNullGetString(bookMoneyService.getTotalBookSubscription(userId, currencyString, beginTime, endTime)));//订金
            fieldTemplate.setField6(szMath.ifNotNullGetString(bookMoneyService.getTotalCancelBookSubscription(userId, currencyString, beginTime, endTime)));//退订金
            fieldTemplate.setField7(szMath.ifNotNullGetString(vipIntegrationService.getTotalPayTimeZone(userId, currencyString, beginTime, endTime)));//会员充值
            CompanyPay companyPayQuery=companyPayService.getSumPay(null,userId, currency.getCurrency(),beginTime, endTime);
            if(companyPayQuery!=null) {
                fieldTemplate.setField8(ifNotNullGetString(companyPayQuery.getPay()));//单位付款
            }
            templateList.add(fieldTemplate);
        }
        /*生成水晶报表字段*/
        ExchangeUserJQ exchangeUserJQ=new ExchangeUserJQ();
        List<ExchangeUserRow> exchangeUserRowList=new ArrayList<>();
        for (FieldTemplate template : templateList) {
            exchangeUserRowList.add(new ExchangeUserRow(template));
        }
        Double payTotal=debtPayService.getDebtMoney(userId,null,true, beginTime, endTime);
        Double moneyIn=debtHistoryService.getTotalAddByUserTimeZone(userId, beginTime, endTime);//杂单
        Double moneyOut=debtHistoryService.getTotalDiscountByUserTimeZone(userId, beginTime, endTime);//冲账
        Double depositAll=debtService.getDepositMoneyAll();//在店押金
        List<String> paramList=new ArrayList<>();
        paramList.add(timeService.getNowLong());
        paramList.add(timeService.dateToStringLong(beginTime));
        paramList.add(timeService.dateToStringLong(endTime));
        paramList.add(userId);
        paramList.add(ifNotNullGetString(moneyIn));
        paramList.add(ifNotNullGetString(moneyOut));
        paramList.add(ifNotNullGetString(depositAll));//param7
        paramList.add(ifNotNullGetString(payTotal));//param7
        String[] param=new String[paramList.size()];
        paramList.toArray(param);
        reportJson.setReportIndex(reportService.generateReport(templateList, param,"exchangeUser",format ));
        exchangeUserJQ.setDepositAll(depositAll);
        exchangeUserJQ.setExchangeUserRowList(exchangeUserRowList);
        exchangeUserJQ.setMoneyIn(moneyIn);
        exchangeUserJQ.setMoneyOut(moneyOut);
        exchangeUserJQ.setReportJson(reportJson);
        exchangeUserJQ.setPayTotal(payTotal);
        return exchangeUserJQ;
    }

    /**
     * 手机端，接待交班审核表(小表，只有结算款，附带房吧明细)
     */
    @RequestMapping(value = "exchangeUserReportSmallMobile")
    public ExchangeUserSmallJQReturn exchangeUserReportSmallMobile(@RequestBody ReportJson reportJson)throws Exception{
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        timeService.setNow();
        List<ExchangeUserSmallJQRow> exchangeUserSmallJQRowList = new ArrayList<>();
        List<Currency> currencyList=currencyService.get(null);
        ExchangeUserSmallJQRow exchangeUserSmallJQRow;
        exchangeUserSmallJQRow=new ExchangeUserSmallJQRow();
        exchangeUserSmallJQRow.setField1("前台款列印:");
        exchangeUserSmallJQRowList.add(exchangeUserSmallJQRow);
        /*统计结算款*/
        Double consumeTotal=0.0;
        for (Currency currency : currencyList) {
            exchangeUserSmallJQRow = new ExchangeUserSmallJQRow();
            String currencyString=currency.getCurrency();
            exchangeUserSmallJQRow.setField2(currency.getCurrency());//币种
            Double roomPay=debtPayService.getDebtMoney(null,currencyString,false,beginTime, endTime);
            exchangeUserSmallJQRow.setField3(ifNotNullGetString(roomPay));//结算款
            exchangeUserSmallJQRowList.add(exchangeUserSmallJQRow);
            consumeTotal+=roomPay;
        }
        Double payTotal=debtPayService.getDebtMoney(null,null,true, beginTime, endTime);
        /*在店押金*/
        Double depositAll=debtService.getDepositMoneyAll();//在店押金
        /*统计房吧零售*/
        List<RoomShopDetail> roomShopDetailList=roomShopDetailService.getRetailByDoneTimeUser(null,beginTime, endTime);//商品零售明细
        ExchangeUserSmallJQRow exchangeUserSmallJQRowWait = new ExchangeUserSmallJQRow();
        exchangeUserSmallJQRowWait.setField1("商品零售");
        exchangeUserSmallJQRowList.add(exchangeUserSmallJQRowWait);
        Double tempTotalConsume=0.0;//准备回记消费合计
        for (RoomShopDetail roomShopDetail : roomShopDetailList) {
            exchangeUserSmallJQRow = new ExchangeUserSmallJQRow();
            exchangeUserSmallJQRow.setField2(roomShopDetail.getItem());
            exchangeUserSmallJQRow.setField3(String.valueOf(roomShopDetail.getNum()+" "+roomShopDetail.getUnit()));
            exchangeUserSmallJQRow.setField4(String.valueOf(roomShopDetail.getTotalMoney()));
            tempTotalConsume+=roomShopDetail.getTotalMoney();
            exchangeUserSmallJQRowList.add(exchangeUserSmallJQRow);
        }
        exchangeUserSmallJQRowWait.setField2(String.valueOf(tempTotalConsume));

        exchangeUserSmallJQRowWait = new ExchangeUserSmallJQRow();
        exchangeUserSmallJQRowWait.setField1("房吧销售");
        exchangeUserSmallJQRowList.add(exchangeUserSmallJQRowWait);
        tempTotalConsume=0.0;//准备回记消费合计
        roomShopDetailList=roomShopDetailService.getSumRoomShopByDoneTimeUser(null, beginTime, endTime);//房吧明细
        for (RoomShopDetail roomShopDetail : roomShopDetailList) {
            tempTotalConsume+=roomShopDetail.getTotalMoney();
            exchangeUserSmallJQRow = new ExchangeUserSmallJQRow();
            exchangeUserSmallJQRow.setField2(roomShopDetail.getItem());
            exchangeUserSmallJQRow.setField3(String.valueOf(roomShopDetail.getNum()+" "+roomShopDetail.getUnit()));
            exchangeUserSmallJQRow.setField4(String.valueOf(roomShopDetail.getTotalMoney()));
            exchangeUserSmallJQRowList.add(exchangeUserSmallJQRow);
        }
        exchangeUserSmallJQRowWait.setField2(String.valueOf(tempTotalConsume));

        ExchangeUserSmallJQReturn exchangeUserSmallJQReturn=new ExchangeUserSmallJQReturn();
        exchangeUserSmallJQReturn.setExchangeUserSmallJQRowList(exchangeUserSmallJQRowList);
        exchangeUserSmallJQReturn.setRemark("在店押金:"+depositAll+",结算款:"+consumeTotal+"应缴结算款:"+payTotal);
        return exchangeUserSmallJQReturn;
    }

    /**
     * 接待交班审核表(小表，只有结算款，附带房吧明细)
     */
    @RequestMapping(value = "exchangeUserReportSmall")
    public Integer exchangeUserReportSmall(@RequestBody ReportJson reportJson)throws Exception{
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        String userId=reportJson.getUserId();
        if("".equals(userId)){
            userId=null;
        }
        String format=reportJson.getFormat();
        timeService.setNow();
        List<FieldTemplate> templateList = new ArrayList<>();
        List<Currency> currencyList=currencyService.get(null);
        FieldTemplate fieldTemplate;
        /*统计结算款*/
        Double consumeTotal=0.0;
        for (Currency currency : currencyList) {
            fieldTemplate = new FieldTemplate();
            String currencyString=currency.getCurrency();
            fieldTemplate.setField2(currency.getCurrency());//币种
            Double roomPay=debtPayService.getDebtMoney(userId,currencyString,false,beginTime, endTime);
            fieldTemplate.setField3(ifNotNullGetString(roomPay));//结算款
            templateList.add(fieldTemplate);
            consumeTotal+=roomPay;
        }
        Double payTotal=debtPayService.getDebtMoney(userId,null,true, beginTime, endTime);
        /*在店押金*/
        Double depositAll=debtService.getDepositMoneyAll();//在店押金
        /*统计房吧零售*/
        List<RoomShopDetail> roomShopDetailList=roomShopDetailService.getRetailByDoneTimeUser(userId,beginTime, endTime);//商品零售明细
        FieldTemplate fieldTemplateWait = new FieldTemplate();
        fieldTemplateWait.setField1("商品零售");
        templateList.add(fieldTemplateWait);
        Double tempTotalConsume=0.0;//准备回记消费合计
        for (RoomShopDetail roomShopDetail : roomShopDetailList) {
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField2(roomShopDetail.getItem());
            fieldTemplate.setField3(String.valueOf(roomShopDetail.getNum()+" "+roomShopDetail.getUnit()));
            fieldTemplate.setField4(String.valueOf(roomShopDetail.getTotalMoney()));
            tempTotalConsume+=roomShopDetail.getTotalMoney();
            templateList.add(fieldTemplate);
        }
        fieldTemplateWait.setField2(String.valueOf(tempTotalConsume));

        fieldTemplateWait = new FieldTemplate();
        fieldTemplateWait.setField1("房吧销售");
        templateList.add(fieldTemplateWait);
        tempTotalConsume=0.0;//准备回记消费合计
        roomShopDetailList=roomShopDetailService.getSumRoomShopByDoneTimeUser(userId, beginTime, endTime);//房吧明细
        for (RoomShopDetail roomShopDetail : roomShopDetailList) {
            tempTotalConsume+=roomShopDetail.getTotalMoney();
            fieldTemplate = new FieldTemplate();
            fieldTemplate.setField2(roomShopDetail.getItem());
            fieldTemplate.setField3(String.valueOf(roomShopDetail.getNum()+" "+roomShopDetail.getUnit()));
            fieldTemplate.setField4(String.valueOf(roomShopDetail.getTotalMoney()));
            templateList.add(fieldTemplate);
        }
        fieldTemplateWait.setField2(String.valueOf(tempTotalConsume));

        /*开始生成报表
        * param:
        * 1. 酒店名称 otherParamService.getValueByName("酒店名称")
        * 2.起始时间 timeService.dateToStringLong(beginTime)
        * 3.终止时间 timeService.dateToStringLong(endTime)
        * 4.操作员 userId==null?"全部":userId
        * 5.在店押金 depositAll
        * 6.结算款
        * 7.应缴结算款
        * */
        return reportService.generateReport(templateList, new String[]{otherParamService.getValueByName("酒店名称"),timeService.dateToStringLong(beginTime),timeService.dateToStringLong(endTime),userId==null?"全部":userId,ifNotNullGetString(depositAll),ifNotNullGetString(consumeTotal),ifNotNullGetString(payTotal)},"exchangeUserSmall","pdf" );
    }

    /**
     * 餐饮交班审核表
     * 第一次尝试水晶报表
     */
    @RequestMapping(value = "exchangeUserCkReport")
    public List<ExchangeUserCk> exchangeUserCkReport(@RequestBody ReportJson reportJson) throws Exception{
        String userId=reportJson.getUserId();
        if("".equals(userId)){
            userId=null;
        }
        Date beginTime= reportJson.getBeginTime();
        Date endTime= reportJson.getEndTime();
        timeService.setNow();
        List<FieldTemplate> templateList = new ArrayList<>();
        List<ExchangeUserCk> exchangeUserCkList=new ArrayList<>();
        List<Currency> currencyList=currencyService.get(null);
        FieldTemplate fieldTemplate;
        for (Currency currency : currencyList) {
            /*打印报表赋值*/
            fieldTemplate = new FieldTemplate();
            String currencyString=currency.getCurrency();
            fieldTemplate.setField1(currency.getCurrency());//币种
            fieldTemplate.setField2(ifNotNullGetString(deskPayService.getDeskMoneyByCurrencyDateUser(userId,currencyString,beginTime,endTime)));//结算款
            fieldTemplate.setField5(ifNotNullGetString(bookMoneyService.getTotalBookSubscription(userId, currencyString, beginTime, endTime)));//订金
            fieldTemplate.setField6(ifNotNullGetString(bookMoneyService.getTotalCancelBookSubscription(userId, currencyString, beginTime, endTime)));//退订金
            fieldTemplate.setField7(ifNotNullGetString(vipIntegrationService.getTotalPayTimeZone(userId, currencyString, beginTime, endTime)));//会员充值
            fieldTemplate.setField8(ifNotNullGetString(vipIntegrationService.getTotalDeserveTimeZone(userId, currencyString, beginTime, endTime)));//会员抵用
            templateList.add(fieldTemplate);
            /*前端显示json数据赋值*/
            ExchangeUserCk exchangeUserCk=new ExchangeUserCk(fieldTemplate);
            exchangeUserCkList.add(exchangeUserCk);
        }
        exchangeUserCkList.get(0).setReportJson(new ReportJson(userId, beginTime, endTime));//为第一条信息设置查询条件，适用于水晶报表
        String[] params=new String[]{timeService.getNowLong(),timeService.dateToStringLong(beginTime),timeService.dateToStringLong(endTime),userId};
        reportService.generateReport(templateList,params, "exchangeUserCk","pdf");
        return exchangeUserCkList;
    }
}
