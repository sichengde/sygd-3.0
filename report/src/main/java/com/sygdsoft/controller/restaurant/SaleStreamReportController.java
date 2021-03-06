package com.sygdsoft.controller.restaurant;

import com.sygdsoft.model.*;
import com.sygdsoft.service.DeskDetailHistoryService;
import com.sygdsoft.service.OtherParamService;
import com.sygdsoft.service.ReportService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

/**
 * Created by Administrator on 2016/10/29 0029.
 * 销售流水/菜品统计表（自动弹出报表示范）
 */
@RestController
public class SaleStreamReportController {
    @Autowired
    TimeService timeService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    ReportService reportService;
    @Autowired
    SzMath szMath;

    /**
     * 参数：
     * 1.酒店名称
     * 2.时间段
     * 3.原价合计
     * 4.折后合计
     * 5.类别备注
     */
    @RequestMapping(value = "SaleStreamReport")
    public SaleStreamReport SaleStreamReport(@RequestBody SaleStreamQuery saleStreamQuery) {
        Date beginTime = saleStreamQuery.getBeginTime();
        Date endTime = saleStreamQuery.getEndTime();
        Boolean mergeFood=saleStreamQuery.getShowBack();
        if (mergeFood==null){
            mergeFood=false;
        }
        String pointOfSale = saleStreamQuery.getPointOfSale();
        if ("".equals(pointOfSale)) {
            pointOfSale = null;
        }
        String format = saleStreamQuery.getFormat();
        String categories = saleStreamQuery.getCategory();
        if ("".equals(categories)) {
            categories = null;
        }
        List<DeskDetailHistory> deskDetailHistoryList = new ArrayList<>();
        if (categories != null) {
            String[] categoryList = categories.split(",");
            for (String category : categoryList) {
                DeskDetailHistory deskDetailHistory = new DeskDetailHistory();
                deskDetailHistory.setFoodName(category + ":");
                deskDetailHistory.setFoodSign(category + ":");
                deskDetailHistoryList.add(deskDetailHistory);
                deskDetailHistoryList.addAll(deskDetailHistoryService.getSumList(beginTime, endTime, pointOfSale, category, mergeFood));
            }
        } else {
            deskDetailHistoryList = deskDetailHistoryService.getSumList(beginTime, endTime, pointOfSale, null, mergeFood);
        }
        List<FieldTemplate> templateList = new ArrayList<>();
        List<SaleStreamRow> saleStreamRowList = new ArrayList<>();
        Double totalConsume = 0.0;
        Double totalNum = 0.0;
        StringBuilder categoryParse = new StringBuilder();
        Double categoryConsume = 0.0;
        Double categoryNum = 0.0;
        String lastFoodName = "";
        for (DeskDetailHistory deskDetailHistory : deskDetailHistoryList) {
            FieldTemplate fieldTemplate = new FieldTemplate();
            if (mergeFood) {
                fieldTemplate.setField1(deskDetailHistory.getFoodSign());
            } else {
                fieldTemplate.setField1(deskDetailHistory.getFoodName());
            }
            fieldTemplate.setField2(ifNotNullGetString(deskDetailHistory.getNotNullNum()));
            fieldTemplate.setField3(ifNotNullGetString(deskDetailHistory.getTotal()));
            SaleStreamRow saleStreamRow = new SaleStreamRow();
            if (mergeFood) {
                saleStreamRow.setFoodName(deskDetailHistory.getFoodSign());
            } else {
                saleStreamRow.setFoodName(deskDetailHistory.getFoodName());
            }
            saleStreamRow.setNum(deskDetailHistory.getNotNullNum());
            saleStreamRow.setTotal(szMath.formatTwoDecimalReturnDouble(deskDetailHistory.getTotal()));
            saleStreamRowList.add(saleStreamRow);
            templateList.add(fieldTemplate);
            if (deskDetailHistory.getTotal() != null) {//有一行是类别，则总和为空，在此生成该类别的分析
                categoryConsume += deskDetailHistory.getTotal();
                categoryNum += deskDetailHistory.getNotNullNum();
                totalConsume += deskDetailHistory.getTotal();
            } else {//为空时，在此生成该类别的分析
                if (!"".equals(lastFoodName)) {
                    categoryParse.append(lastFoodName).append(categoryNum).append("/").append(categoryConsume).append(" , ");
                }
                lastFoodName=deskDetailHistory.getFoodName();
                categoryConsume = 0.0;
                categoryNum = 0.0;
            }
            if (deskDetailHistory.getNotNullNum() != null) {
                totalNum += deskDetailHistory.getNotNullNum();
            }
        }
        if (!"".equals(lastFoodName)) {
            categoryParse.append(lastFoodName).append(categoryNum).append("/").append(categoryConsume).append(" , ");
        }
        timeService.setNow();
        List<String> paramList = new ArrayList<>();
        paramList.add(otherParamService.getValueByName("酒店名称"));
        paramList.add(timeService.dateToStringLong(beginTime) + " 至 " + timeService.dateToStringLong(endTime));
        paramList.add(ifNotNullGetString(totalNum));
        paramList.add(ifNotNullGetString(totalConsume));
        if (categories != null) {//有类别的话还要加上类别备注
            paramList.add(categoryParse.toString());
        }
        String[] param = new String[paramList.size()];
        paramList.toArray(param);
        SaleStreamReport saleStreamReport = new SaleStreamReport();
        saleStreamReport.setTotalMoney(totalConsume);
        saleStreamReport.setTotalNum(totalNum);
        saleStreamReport.setSaleStreamRowList(saleStreamRowList);
        saleStreamReport.setSaleStreamQuery(saleStreamQuery);
        saleStreamReport.setReportIndex(reportService.generateReport(templateList, param, "SaleStream", format));
        saleStreamReport.setCategoryParse(categoryParse.toString());
        return saleStreamReport;
    }
}
