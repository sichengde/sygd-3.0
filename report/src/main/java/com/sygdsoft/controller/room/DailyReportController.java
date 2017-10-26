package com.sygdsoft.controller.room;

import com.sygdsoft.model.*;
import com.sygdsoft.model.room.DailyReportReturn;
import com.sygdsoft.service.*;
import com.sygdsoft.util.NullJudgement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-07-11.
 * 借贷平衡表
 */
@RestController
public class DailyReportController {
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    VipService vipService;
    @Autowired
    BookService bookService;
    @Autowired
    CompanyService companyService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    ReportService reportService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    VipIntegrationService vipIntegrationService;
    @Autowired
    CompanyPayService companyPayService;
    @Autowired
    DeskInHistoryService deskInHistoryService;
    @Autowired
    DeskPayRichService deskPayRichService;

    @RequestMapping(value = "dailyReport", method = RequestMethod.POST)
    public DailyReportReturn dailyReport(@RequestBody ReportJson reportJson) throws Exception {
        /*获取传递进来的查询参数*/
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        String format = reportJson.getFormat();
        List<PointOfSale> pointOfSaleList = pointOfSaleService.get(null);//获取所有一级部门
        List<FieldTemplate> templateList = new ArrayList<>();//最终返回给jasperReport的报表数组
        List<FieldTemplate> templateCurrencyList = new ArrayList<>();//币种信息，需要拼接到上边的数组后边
        Integer field = 1;
        Double[] totalConsumeMoneyPerModule = new Double[pointOfSaleList.size()];
        Double[] totalCurrencyMoneyPerModule = new Double[pointOfSaleList.size()];
        Double[] totalCurrencyMoneyPerModuleRealMoney = new Double[pointOfSaleList.size()];//参与统计结算款
        Double[] totalDiscountPerModule = new Double[pointOfSaleList.size()];
        List<String> paramList = new ArrayList<>();
        Integer totalField = pointOfSaleList.size() + 2;//用于生成日报表实体类对象，第一列名称，最后一列合计，所以要加两列
        List<Integer> virtualLine = new ArrayList<>();//虚拟营业点列，不参与右侧合计
        for (PointOfSale pointOfSale : pointOfSaleList) {
            String module = pointOfSale.getModule();
            String firstPointOfSale = pointOfSale.getFirstPointOfSale();
            paramList.add(firstPointOfSale);
            totalConsumeMoneyPerModule[field - 1] = 0.0;
            totalCurrencyMoneyPerModule[field - 1] = 0.0;
            totalCurrencyMoneyPerModuleRealMoney[field - 1] = 0.0;
            totalDiscountPerModule[field - 1] = 0.0;
            if (pointOfSale.getNotNullIfVirtual()) {
                virtualLine.add(field + 1);
            }
            /*每个销售点自定义的二级销售部门*/
            for (String item : pointOfSale.getSecondPointOfSale().split(" ")) {
                Double money = 0.0;
                FieldTemplate old = null;
                for (FieldTemplate fieldTemplate : templateList) {//如果二级统计部门跟其他pos点的统计部门重复，则不需要新增
                    if (fieldTemplate.getField1().equals(item)) {
                        old = fieldTemplate;
                        break;
                    }
                }
                switch (module) {
                    case "接待":
                        /*计算消费额*/
                        money = debtHistoryService.getHistoryConsume(beginTime, endTime, item);
                        break;
                    case "餐饮"://餐饮需要考虑多个一级销售部门的情况
                        if (pointOfSale.getNotNullIfVirtual()) {
                            String target;
                            try {
                                target = pointOfSale.getVirtualTarget();
                            } catch (Exception e) {
                                throw new Exception(firstPointOfSale + "--设置为了虚拟营业点但是没有设置虚拟目标");
                            }
                            money = deskInHistoryService.getCategorySum(beginTime, endTime, target, item);
                        } else {
                            money = deskDetailHistoryService.getDeskMoneyByDatePointOfSale(beginTime, endTime, firstPointOfSale, item);
                        }
                        break;
                    case "桑拿":
                        break;
                }
                if (old == null) {
                    FieldTemplate fieldTemplateConsume = new FieldTemplate();
                    fieldTemplateConsume.setField1(item);
                    fieldTemplateConsume.setFieldN(field + 1, String.valueOf(money));
                    fieldTemplateConsume.setMark("pointOfSale");
                    templateList.add(fieldTemplateConsume);
                } else {
                    old.setFieldN(field + 1, String.valueOf(money));
                }
                totalConsumeMoneyPerModule[field - 1] = totalConsumeMoneyPerModule[field - 1] + money;
            }
            /*计算币种信息，但是先不添加，最后一起添加到templateList中*/
            List<Currency> currencyList = currencyService.get(null);
            for (Currency currency : currencyList) {
                String currencyString = currency.getCurrency();
                FieldTemplate old = null;
                for (FieldTemplate fieldTemplate : templateCurrencyList) {//如果二级统计部门跟其他pos点的统计部门重复，则不需要新增
                    if (fieldTemplate.getField1().equals(currency.getCurrency())) {
                        old = fieldTemplate;
                        break;
                    }
                }
                Double money = 0.0;
                switch (module) {
                    case "接待":
                        money = debtPayService.getDebtMoney(null, currencyString, false, beginTime, endTime);
                        break;
                    case "餐饮":
                        if (pointOfSale.getNotNullIfVirtual()) {
                            firstPointOfSale = pointOfSale.getVirtualTarget();
                            for (String category : pointOfSale.getSecondPointOfSale().split(" ")) {
                                money += deskPayRichService.getPay(currencyString, firstPointOfSale, category, beginTime, endTime);
                            }
                        } else {
                            money = deskPayService.getPay(null, currencyString, firstPointOfSale, beginTime, endTime);
                        }
                        break;
                    case "桑拿":
                        break;
                }
                if (old == null) {
                    FieldTemplate fieldTemplateCurrency = new FieldTemplate();
                    fieldTemplateCurrency.setField1(currency.getCurrency());
                    fieldTemplateCurrency.setFieldN(field + 1, String.valueOf(money));
                    fieldTemplateCurrency.setMark("currency");
                    templateCurrencyList.add(fieldTemplateCurrency);
                } else {
                    old.setFieldN(field + 1, String.valueOf(money));
                }
                totalCurrencyMoneyPerModule[field - 1] = totalCurrencyMoneyPerModule[field - 1] + money;
                if (currency.getNotNullPayTotal()) {
                    totalCurrencyMoneyPerModuleRealMoney[field - 1] = totalCurrencyMoneyPerModuleRealMoney[field - 1] + money;
                }
            }
            /*每个销售点的折扣*/
            switch (module) {
                case "接待":
                    totalDiscountPerModule[field - 1] = debtHistoryService.getTotalDiscount(beginTime, endTime);//接待的就是冲账+宴请
                    break;
                case "餐饮":
                    if (pointOfSale.getNotNullIfVirtual()) {
                        firstPointOfSale = pointOfSale.getVirtualTarget();
                        Double t=0.0;
                        for (String category : pointOfSale.getSecondPointOfSale().split(" ")) {
                            t += deskInHistoryService.getTotalDiscount(beginTime, endTime, firstPointOfSale, category);//餐饮的就是折扣
                        }
                        totalDiscountPerModule[field - 1]=t;
                    } else {
                        totalDiscountPerModule[field - 1] = deskInHistoryService.getTotalDiscount(beginTime, endTime, firstPointOfSale, null);//餐饮的就是折扣
                    }
                    break;
                case "桑拿":
                    break;
            }
            field++;
        }
        /*统计结算款合计*/
        FieldTemplate fieldTemplateTotal = new FieldTemplate();
        fieldTemplateTotal.setField1("总收入合计");
        for (int i = 0; i < totalConsumeMoneyPerModule.length; i++) {
            Double aDouble = totalConsumeMoneyPerModule[i];
            fieldTemplateTotal.setFieldN(i + 2, String.valueOf(aDouble));
        }
        templateList.add(fieldTemplateTotal);
        /*统计折扣*/
        FieldTemplate fieldTemplateDiscount = new FieldTemplate();
        fieldTemplateDiscount.setField1("冲账");
        for (int i = 0; i < totalDiscountPerModule.length; i++) {
            Double aDouble = totalDiscountPerModule[i];
            fieldTemplateDiscount.setFieldN(i + 2, String.valueOf(-aDouble));
        }
        fieldTemplateDiscount.setMark("pointOfSale");
        templateList.add(fieldTemplateDiscount);
        /*统计净收入合计----就是把之前的减一下*/
        FieldTemplate fieldTemplatePureMoney = new FieldTemplate();
        fieldTemplatePureMoney.setField1("净收入合计");
        for (int i = 0; i < totalDiscountPerModule.length; i++) {
            Double aDouble = totalDiscountPerModule[i] + totalConsumeMoneyPerModule[i];
            fieldTemplatePureMoney.setFieldN(i + 2, String.valueOf(aDouble));
        }
        templateList.add(fieldTemplatePureMoney);
        /*拼接币种信息*/
        templateList.addAll(templateCurrencyList);
        /*统计币种合计*/
        FieldTemplate fieldTemplateTotalCurrency = new FieldTemplate();
        fieldTemplateTotalCurrency.setField1("合计");
        for (int i = 0; i < totalCurrencyMoneyPerModule.length; i++) {
            Double aDouble = totalCurrencyMoneyPerModule[i];
            fieldTemplateTotalCurrency.setFieldN(i + 2, String.valueOf(aDouble));
        }
        templateList.add(fieldTemplateTotalCurrency);
        /*参与统计币种合计*/
        FieldTemplate fieldTemplateTotalCurrencyRealMoney = new FieldTemplate();
        fieldTemplateTotalCurrencyRealMoney.setField1("参与统计");
        for (int i = 0; i < totalCurrencyMoneyPerModuleRealMoney.length; i++) {
            Double aDouble = totalCurrencyMoneyPerModuleRealMoney[i];
            fieldTemplateTotalCurrencyRealMoney.setFieldN(i + 2, String.valueOf(aDouble));
        }
        templateList.add(fieldTemplateTotalCurrencyRealMoney);
        /*为后两列(一列)列头赋值*/
        paramList.add("合计");
        /*统计右侧合计*/
        for (FieldTemplate fieldTemplate : templateList) {
            Double total = 0.0;
            for (int i = field; i > 1; i--) {
                if (virtualLine.indexOf(i) == -1) {
                    total = total + Double.valueOf(NullJudgement.nullToZero(fieldTemplate.getFieldN(i)));
                }
            }
            fieldTemplate.setFieldN(field + 1, String.valueOf(total));
        }
        /*计算会员结算金额*/
        Double VipPay = NullJudgement.nullToZero(vipIntegrationService.getTotalPay(beginTime, endTime));
        /*计算单位结算金额*/
        CompanyPay companyPayQuery = companyPayService.getSumPay(null, null, null, beginTime, endTime);
        Double companyPay;
        Double companyDebt;
        if (companyPayQuery == null) {
            companyPay = 0.0;
            companyDebt = 0.0;
        } else {
            companyPay = companyPayQuery.getPay();
            companyDebt = companyPayQuery.getDebt();
        }
        /*计算当日收取的预订订金*/
        Double subscription = NullJudgement.nullToZero(bookService.getTotalSubscription(beginTime, endTime));
        paramList.add(userService.getCurrentUser());
        paramList.add(ifNotNullGetString(VipPay));
        paramList.add(ifNotNullGetString(companyPay));
        paramList.add(ifNotNullGetString(subscription));
        paramList.add(ifNotNullGetString(VipPay + companyPay + subscription));
        /*返回可视化数组*/
        List<DailyReport> dailyReportList = new ArrayList<>();
        for (FieldTemplate fieldTemplate : templateList) {
            dailyReportList.add(new DailyReport(fieldTemplate, totalField));
        }
        String[] param = new String[paramList.size()];
        paramList.toArray(param);
        reportJson.setReportIndex(reportService.generateReport(templateList, param, "dailyReport", format));
        dailyReportList.get(0).setReportJson(reportJson);
        dailyReportList.get(0).setParamList(pointOfSaleService.listToStringList(pointOfSaleList));
        /*设计返回值*/
        DailyReportReturn dailyReportReturn = new DailyReportReturn();
        dailyReportReturn.setBookMoney(subscription);
        dailyReportReturn.setCompanyDebt(companyDebt);
        dailyReportReturn.setCompanyPay(companyPay);
        dailyReportReturn.setVipPay(VipPay);
        dailyReportReturn.setDailyReportList(dailyReportList);
        return dailyReportReturn;
    }
}
