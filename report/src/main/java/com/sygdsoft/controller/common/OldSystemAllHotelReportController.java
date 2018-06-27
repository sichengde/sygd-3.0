package com.sygdsoft.controller.common;

import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DebtIntegrationService;
import com.sygdsoft.service.PointOfSaleService;
import com.sygdsoft.service.ReportService;
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

    @RequestMapping(value = "oldSystemAllHotelReport")
    public int oldSystemAllHotelReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        List<FieldTemplate> fieldTemplates=new ArrayList<>();
        List<String> pointOfSaleList = pointOfSaleService.getRoomPointOfSaleList();
        /*当日发生*/

        return reportService.generateReport(fieldTemplates,new String[]{}, "oldSystemAllHotel","pdf" );
    }
}
