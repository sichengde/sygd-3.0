package com.sygdsoft.controller.common;

import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DebtIntegrationService;
import com.sygdsoft.service.PointOfSaleService;
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

    @RequestMapping(value = "oldSystemAllHotelReport")
    public int oldSystemAllHotelReport(@RequestBody ReportJson reportJson) throws Exception {
        Date date = reportJson.getBeginTime();//当日时间
        Date beginTime1=timeService.getMinTime(date);
        Date beginTime2=timeService.getMinMonth(date);
        Date beginTime3=timeService.getMinYear(date);
        Date endTime1=timeService.getMaxTime(date);
        Date endTime2=timeService.getMaxMonth(date);
        Date endTime3=timeService.getMaxYear(date);
        List<FieldTemplate> fieldTemplates=new ArrayList<>();
        List<String> pointOfSaleList = pointOfSaleService.getRoomPointOfSaleList();
        for (String pointOfSale : pointOfSaleList) {
            FieldTemplate fieldTemplate=new FieldTemplate();
            fieldTemplate.setField1(pointOfSale);
            fieldTemplate.setField2(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime1,endTime1, pointOfSale, true)));
            fieldTemplate.setField3(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime2,endTime2, pointOfSale, true)));
            fieldTemplate.setField4(szMath.formatTwoDecimal(debtIntegrationService.getSumConsumeByDoTime(beginTime3,endTime3, pointOfSale, true)));fieldTemplate.setField5("0.00");//现金都设置为0
        }
        return reportService.generateReport(fieldTemplates,new String[]{}, "oldSystemAllHotel","pdf" );
    }
}
