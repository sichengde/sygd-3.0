package com.sygdsoft.controller.common;

import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*今日累计挂账余额=昨日累计挂账余额+本日新增-结挂账款*/
@RestController
public class OldSystemAllHotelReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    TimeService timeService;
    @Autowired
    SzMath szMath;
    @Autowired
    PayPointOfSaleService payPointOfSaleService;
    @Autowired
    DebtService debtService;
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    CheckInSnapshotService checkInSnapshotService;
    @Autowired
    ReportStoreService reportStoreService;

    @RequestMapping(value = "oldSystemAllHotelReport")
    public int oldSystemAllHotelReport(@RequestBody ReportJson reportJson) throws Exception {
        Date date = reportJson.getBeginTime();//当日时间
        Date beginTime1 = timeService.getMinTime(date);
        Date beginTime2 = timeService.getMinMonth(date);
        Date beginTime3 = timeService.getMinYear(date);
        Date endTime1 = timeService.getMaxTime(date);
        //Date endTime2 = timeService.getMaxMonth(date);//按当前时间为最后一天计算
        Date endTime2 = timeService.getMaxMonth(date);
        //Date endTime3 = timeService.getMaxYear(date);//按当前时间为最后一天计算
        Date endTime3 = timeService.getMaxYear(date);
        List<FieldTemplate> fieldTemplateList = new ArrayList<>();
        /*先计算客房合计*/
        FieldTemplate fieldTemplateRoomTotal = new FieldTemplate();
        fieldTemplateRoomTotal.setField1("客房合计");
        fieldTemplateRoomTotal.setField2(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime1, endTime1, null, null, true)));
        fieldTemplateRoomTotal.setField3(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime2, endTime2, null, null, true)));
        fieldTemplateRoomTotal.setField4(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime3, endTime3, null, null, true)));
        fieldTemplateRoomTotal.setField5("0.00");//现金都设置为0
        fieldTemplateRoomTotal.setField6(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime1, endTime1, null,"接待")));
        fieldTemplateRoomTotal.setField7(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime2, endTime2, null,"接待")));
        fieldTemplateRoomTotal.setField8(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime3, endTime3, null,"接待")));
        fieldTemplateRoomTotal.setField9(fieldTemplateRoomTotal.getField2());//因为当日发生额是算的di表，所以必须要排除转账数据,但未结没必要排除，一个账转100次房费未结还是只有一条
        fieldTemplateRoomTotal.setField10(szMath.formatTwoDecimal(debtService.getConsumeByPointOfSale(beginTime1,endTime1,null,false)+debtHistoryService.getConsumeNotCompanyPaid(beginTime1,endTime1)));
        /*挂账未结=debt里的和companyDebt里的正常账务和杂单,*/
        fieldTemplateRoomTotal.setField11(szMath.formatTwoDecimal(debtService.getConsumeByPointOfSale(null,null,null,false)+debtHistoryService.getConsumeNotCompanyPaid(null,null)+companyDebtService.getSumOtherConsume()));
        fieldTemplateList.add(fieldTemplateRoomTotal);
        /*------------------------------------------再计算各自的明细------------------------------------------*/
        List<String> pointOfSaleList = pointOfSaleService.getRoomPointOfSaleList();
        List<CompanyDebt> companyDebtList = companyDebtService.get();
        StringBuilder paySerial = new StringBuilder();
        for (CompanyDebt companyDebt : companyDebtList) {
            if ("接待".equals(companyDebt.getPointOfSale()) && companyDebt.getPaySerial() != null && !"".equals(companyDebt.getPaySerial())) {
                paySerial.append("\'").append(companyDebt.getPaySerial()).append("\',");
            }
        }
        List<DebtHistory> debtHistoryList = new ArrayList<>();
        if (!paySerial.toString().equals("")) {
            debtHistoryList = debtHistoryService.getListByCompanyPaid(paySerial.substring(0, paySerial.length() - 1));
        }
        FieldTemplate fieldTemplateDeskHang=new FieldTemplate();
        for (String pointOfSale : pointOfSaleList) {
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(pointOfSale);
            fieldTemplate.setField2(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime1, endTime1, pointOfSale,null, true)));//发生额
            fieldTemplate.setField3(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime2, endTime2, pointOfSale,null, true)));//发生额
            fieldTemplate.setField4(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime3, endTime3, pointOfSale,null, true)));//发生额
            fieldTemplate.setField5("0.00");//现金都设置为0
            fieldTemplate.setField6(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime1, endTime1, pointOfSale,"接待")));//结挂账款
            fieldTemplate.setField7(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime2, endTime2, pointOfSale,"接待")));//结挂账款
            fieldTemplate.setField8(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime3, endTime3, pointOfSale,"接待")));//结挂账款
            fieldTemplate.setField9(fieldTemplate.getField2());//当日挂账=当日发生
            /*挂账未结=debt里的和companyDebt里的*/
            Double debtConsumeToday = debtService.getConsumeByPointOfSale(beginTime1, endTime1,pointOfSale,false);//今日未结
            Double debtConsume = debtService.getConsumeByPointOfSale(null,null,pointOfSale,false);
            for (DebtHistory debtHistory : debtHistoryList) {
                if (pointOfSale.equals(debtHistory.getPointOfSale())) {
                    debtConsume += debtHistory.getNotNullConsume();
                    if(debtHistory.getDoTime().getTime()>beginTime1.getTime()&&debtHistory.getDoTime().getTime()<endTime1.getTime()){
                        debtConsumeToday+=debtHistory.getNotNullConsume();
                    }
                }
            }
            for (CompanyDebt companyDebt : companyDebtList) {
                /*定额结算产生的差价直接加*/
                if (companyDebt.getNotNullOtherConsume() && pointOfSale.equals(companyDebt.getSecondPointOfSale())) {
                    debtConsume += companyDebt.getDebt();
                    /*定额结算的账务不会出现在今日未结里*/
                }
            }
            fieldTemplate.setField10(szMath.formatTwoDecimal(debtConsumeToday));
            fieldTemplate.setField11(szMath.formatTwoDecimal(debtConsume));
            fieldTemplateList.add(fieldTemplate);
            if("餐饮挂账".equals(pointOfSale)){
                fieldTemplateDeskHang=fieldTemplate;
            }
        }
        /*------------------------------------------计算餐饮的合计------------------------------------------*/
        FieldTemplate fieldTemplate = new FieldTemplate();
        fieldTemplate.setField1("餐饮");
        fieldTemplate.setField2(szMath.formatTwoDecimal(deskPayService.getPay(null,null,null,beginTime1, endTime1)));
        fieldTemplate.setField3(szMath.formatTwoDecimal(deskPayService.getPay(null,null,null,beginTime2, endTime2)));
        fieldTemplate.setField4(szMath.formatTwoDecimal(deskPayService.getPay(null,null,null,beginTime3, endTime3)));
        /*设置客房里的餐饮挂账金额，就是餐饮转房客的金额*/
        fieldTemplateDeskHang.setField2(szMath.formatTwoDecimal(deskPayService.getPay(null,"转房客",null,beginTime1, endTime1)));
        fieldTemplateDeskHang.setField3(szMath.formatTwoDecimal(deskPayService.getPay(null,"转房客",null,beginTime2, endTime2)));
        fieldTemplateDeskHang.setField4(szMath.formatTwoDecimal(deskPayService.getPay(null,"转房客",null,beginTime3, endTime3)));
        fieldTemplateDeskHang.setField9(fieldTemplateDeskHang.getField2());
        /*客房当日挂账合计*/
        fieldTemplateRoomTotal.setField9(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField9())+szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField9())));
        fieldTemplate.setField5("0.00");//现金都设置为0
        /*餐饮的结挂账款要加上客房"餐饮挂账"的结挂账款*/
        fieldTemplate.setField6(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime1, endTime1, null,"餐饮")+szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField6())));
        fieldTemplate.setField7(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime2, endTime2, null,"餐饮")+szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField7())));
        fieldTemplate.setField8(szMath.formatTwoDecimal(payPointOfSaleService.getDebtMoney(beginTime3, endTime3, null,"餐饮")+szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField8())));
        fieldTemplate.setField9(fieldTemplate.getField2());
        fieldTemplate.setField10(szMath.formatTwoDecimal(companyDebtService.getSumDebtMoney(beginTime1,endTime1,"餐饮")+szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField10())));
        /*挂账未结=单位和转房客未结的*/
        fieldTemplate.setField11(szMath.formatTwoDecimal(companyDebtService.getSumDebtMoney(null,null,"餐饮")+szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField11())));
        fieldTemplateList.add(fieldTemplate);
        /*计算一波总和*/
        FieldTemplate fieldTemplateTotal = new FieldTemplate();
        fieldTemplateTotal.setField1("合计");
        fieldTemplateTotal.setField2(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField2())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField2())));
        fieldTemplateTotal.setField3(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField3())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField3())));
        fieldTemplateTotal.setField4(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField4())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField4())));
        fieldTemplateTotal.setField5(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField5())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField5())));
        fieldTemplateTotal.setField6(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField6())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField6())-szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField6())));
        fieldTemplateTotal.setField7(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField7())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField7())-szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField7())));
        fieldTemplateTotal.setField8(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField8())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField8())-szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField8())));
        fieldTemplateTotal.setField9(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField9())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField9())-szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField9())));
        fieldTemplateTotal.setField10(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField10())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField10())-szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField10())));
        fieldTemplateTotal.setField11(szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(fieldTemplateRoomTotal.getField11())+szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField11())-szMath.formatTwoDecimalReturnDouble(fieldTemplateDeskHang.getField11())));
        fieldTemplateList.add(fieldTemplateTotal);
        /*最后统一赋值，因为之前还有类型转换，不能直接赋值为带逗号的*/
        for (FieldTemplate template : fieldTemplateList) {
            template.setField2(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField2())));
            template.setField3(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField3())));
            template.setField4(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField4())));
            template.setField5(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField5())));
            template.setField6(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField6())));
            template.setField7(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField7())));
            template.setField8(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField8())));
            template.setField9(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField9())));
            template.setField10(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField10())));
            template.setField11(szMath.formatBigDecimal(szMath.formatTwoDecimalReturnDouble(template.getField11())));
        }
        /*开始输出其他参数
        * param1 上日预付(上日在店押金)
        * param2 本日收取(交班审核表不选操作员收预付)
        * param3 单退预付(手动退)
        * param4 结算退预付(结算退)
        * param5 当日预付变化(收取-单退-结算)
        * param6 预付余额(在店预付)
        * param7 上日消费(消费余额+结挂帐款-本日新增)
        * param8 本日新增(当日挂账)
        * param9 结挂账款(当日结挂帐款)
        * param10 消费纯变化(本日新增-结挂帐款)
        * param11 消费余额
        * */
        String param1=szMath.formatTwoDecimal(checkInSnapshotService.getSum("deposit",beginTime1));
        String param2=szMath.formatTwoDecimal(debtIntegrationService.getSum("deposit",beginTime1,endTime1));
        String param3=szMath.formatTwoDecimal(debtIntegrationService.getSumCancelDeposit(null, null, beginTime1, endTime1));
        String param4=szMath.formatTwoDecimal(debtHistoryService.getTotalCancelDeposit(null, null, beginTime1, endTime1));
        String param5=szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(param2)-szMath.formatTwoDecimalReturnDouble(param3)-szMath.formatTwoDecimalReturnDouble(param4));
        String param6=szMath.formatTwoDecimal(debtService.getDepositMoneyAll(null));
        String param8=fieldTemplateTotal.getField9();
        String param9=fieldTemplateTotal.getField6();
        String param10=szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(param8)-szMath.formatTwoDecimalReturnDouble(param9));
        String param11=fieldTemplateTotal.getField11();
        String param7=szMath.formatTwoDecimal(szMath.formatTwoDecimalReturnDouble(param11)-szMath.formatTwoDecimalReturnDouble(param10));

        return reportService.generateReport(fieldTemplateList, new String[]{param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11}, "oldSystemAllHotel", "pdf");
    }

    @RequestMapping(value = "oldSystemAllHotelReportGet")
    public int oldSystemAllHotelReportGet(@RequestBody ReportJson reportJson) throws Exception {
        String identify=timeService.dateToStringShort(reportJson.getBeginTime());
        return reportStoreService.print("全店收入表",identify,"oldSystemAllHotel");
    }
}
