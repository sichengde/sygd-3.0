package com.sygdsoft.controller.especially;

import com.sygdsoft.model.DeskGuestSource;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.GuestSource;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DeskGuestSourceService;
import com.sygdsoft.service.GuestSourceService;
import com.sygdsoft.service.ReportService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class HuayuanDailyParseController {
    @Autowired
    ReportService reportService;
    @Autowired
    GuestSourceService guestSourceService;
    @Autowired
    DeskGuestSourceService deskGuestSourceService;
    @Autowired
    TimeService timeService;
    @RequestMapping(value = "huayuanDailyParseReport")
    public int huayuanDailyParseReport(@RequestBody ReportJson reportJson)throws Exception{
        Date beginTime1 = timeService.getMinTime(reportJson.getBeginTime());
        Date beginTime2 = timeService.getMinMonth(beginTime1);
        Date beginTime3 = timeService.getMinYear(beginTime1);
        Date endTime1 = timeService.getMaxTime(reportJson.getBeginTime());
        Date endTime2 = timeService.getMaxMonth(endTime1);
        Date endTime3 = timeService.getMaxYear(endTime1);
        List<FieldTemplate> fieldTemplateList=new ArrayList<>();
        List<GuestSource> guestSourceList = guestSourceService.get();
        List<DeskGuestSource> deskGuestSourceList=deskGuestSourceService.get();
        //------------------------------客源-------------------------------------
        boolean firstRow=true;
        for (GuestSource guestSource : guestSourceList) {
            FieldTemplate fieldTemplate=new FieldTemplate();
            if(firstRow){
                firstRow=false;
                fieldTemplate.setField1("客源");
            }
            fieldTemplate.setField2(guestSource.getGuestSource());
            fieldTemplateList.add(fieldTemplate);
        }
        for (DeskGuestSource deskGuestSource : deskGuestSourceList) {
            FieldTemplate fieldTemplate=new FieldTemplate();
            fieldTemplate.setField2(deskGuestSource.getName());
            fieldTemplateList.add(fieldTemplate);
        }
        FieldTemplate fieldTemplate=new FieldTemplate();
        fieldTemplate.setField2("未选择客源");
        fieldTemplateList.add(fieldTemplate);
        fieldTemplate=new FieldTemplate();
        fieldTemplate.setField2("小计");
        fieldTemplateList.add(fieldTemplate);
        //------------------------------客源-------------------------------------
        fieldTemplate=new FieldTemplate();
        fieldTemplate.setField1("房态");
        fieldTemplate.setField2("房间总数");
        fieldTemplateList.add(fieldTemplate);
        return reportService.generateReport(fieldTemplateList, new String[]{},"huayuanDailyParseReport","pdf");
    }
}
