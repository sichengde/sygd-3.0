package com.sygdsoft.controller.restaurant;

import com.sygdsoft.jsonModel.report.DeskCategoryOut;
import com.sygdsoft.jsonModel.report.HeadField;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by Administrator on 2016/10/29 0029.
 * 餐饮账单分析
 */
@RestController
public class DeskInHistoryParseController {
    @Autowired
    DeskInHistoryService deskInHistoryService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    DeskService deskService;
    @Autowired
    TimeService timeService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "deskInHistoryParseReport")
    public DeskInHistoryParseOut deskInHistoryParseReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        String pointOfSale = reportJson.getPointOfSale();
        if ("".equals(pointOfSale)) {
            pointOfSale = null;
        }
        List<DeskInHistory> deskInHistoryList=deskInHistoryService.getByDatePointOfSale(beginTime, endTime, pointOfSale);
        List<FieldTemplate> fieldTemplateList=new ArrayList<>();
        List<HeadField> headFieldList=new ArrayList<>();
        /*先设置表头*/
        headFieldList.add(new HeadField("日期","field1"));
        headFieldList.add(new HeadField("人数","field2"));
        headFieldList.add(new HeadField("桌号","field3"));
        Map<String,Integer> columnMap=new ManagedMap<>();
        Integer column=4;
        List<DeskCategoryOut> deskCategoryOutTotalList=deskDetailHistoryService.getCategorySecondParse(beginTime, endTime, pointOfSale);//设置类别表头
        for (DeskCategoryOut deskCategoryOutTotal : deskCategoryOutTotalList) {
            if(deskCategoryOutTotal.getTotal()!=null){
                headFieldList.add(new HeadField(deskCategoryOutTotal.getCategory(),"field"+column));
                columnMap.put(deskCategoryOutTotal.getCategory(),column);
                column++;
            }
        }
        headFieldList.add(new HeadField("账单金额","field"+column));//设置账单信息表头
        columnMap.put("账单金额",column);
        column++;
        headFieldList.add(new HeadField("折扣","field"+column));
        columnMap.put("折扣",column);
        column++;
        headFieldList.add(new HeadField("应付金额","field"+column));
        columnMap.put("应付金额",column);
        column++;
        List<DeskPay> deskPayTotalList=deskPayService.getByDatePointOfSale(beginTime, endTime, pointOfSale);//设置币种表头
        for (DeskPay deskPayTotal : deskPayTotalList) {
            if(deskPayTotal.getPayMoney()!=null){
                headFieldList.add(new HeadField(deskPayTotal.getCurrency(),"field"+column));
                columnMap.put(deskPayTotal.getCurrency(),column);
                column++;
            }
        }
        /*表头设置完毕，开始处理数组*/
        Integer totalPeople=0;//同时计算一下来了多少人
        Double totalConsume=0.0;//同时计算一下来了多少人
        for (DeskInHistory deskInHistory : deskInHistoryList) {
            totalConsume+=deskInHistory.getFinalPrice();
            totalPeople+=deskInHistory.getNotNullNum();
            FieldTemplate fieldTemplate=new FieldTemplate();
            /*设置日期和人数*/
            fieldTemplate.setField1(timeService.dateToStringShort(deskInHistory.getDoneTime()));
            fieldTemplate.setField2(ifNotNullGetString(deskInHistory.getNum()));
            fieldTemplate.setField3(deskInHistory.getDesk());
            /*设置品种类别*/
            List<DeskCategoryOut> deskCategoryOutList=deskDetailHistoryService.getCategorySecondParseBySerial(beginTime, endTime, pointOfSale, deskInHistory.getCkSerial());
            for (DeskCategoryOut deskCategoryOut : deskCategoryOutList) {
                if(deskCategoryOut.getTotal()!=null){
                    fieldTemplate.setFieldN(columnMap.get(deskCategoryOut.getCategory()), String.valueOf(deskCategoryOut.getTotal()));
                }
            }
            /*设置账单金额*/
            fieldTemplate.setFieldN(columnMap.get("账单金额"), String.valueOf(deskInHistory.getTotalPrice()));
            fieldTemplate.setFieldN(columnMap.get("折扣"), String.valueOf(deskInHistory.getTotalPrice()-deskInHistory.getFinalPrice()));
            fieldTemplate.setFieldN(columnMap.get("应付金额"), String.valueOf(deskInHistory.getFinalPrice()));
            /*设置付款方式*/
            List<DeskPay> deskPayList=deskPayService.getByCkSerial(deskInHistory.getCkSerial());
            for (DeskPay deskPay : deskPayList) {
                fieldTemplate.setFieldN(columnMap.get(deskPay.getCurrency()), String.valueOf(deskPay.getPayMoney()));
            }
            fieldTemplateList.add(fieldTemplate);
        }
        /*统计上座率和人均消费*/
        Integer totalSeat=deskService.getTotalSeat(pointOfSale);
        String remark="合计人数："+totalPeople+"    合计金额"+totalConsume+"    人均消费: "+szMath.formatTwoDecimal(totalConsume,totalPeople)+"    上座率:"+szMath.formatPercent(totalPeople,totalSeat);
        DeskInHistoryParseOut deskInHistoryParseOut=new DeskInHistoryParseOut();
        deskInHistoryParseOut.setFieldTemplateList(fieldTemplateList);
        deskInHistoryParseOut.setHeadFieldList(headFieldList);
        deskInHistoryParseOut.setRemark(remark);
        return deskInHistoryParseOut;
    }
}
