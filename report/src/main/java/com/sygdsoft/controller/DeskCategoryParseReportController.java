package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.report.DeskCategoryOut;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DeskDetailHistoryService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-11-15.
 */
@RestController
public class DeskCategoryParseReportController {
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    SzMath szMath;

    @RequestMapping("deskCategoryParseReport")
    public List<DeskCategoryOut> DeskCategoryParseReport(@RequestBody ReportJson reportJson) {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        String pointOfSale = reportJson.getPointOfSale();
        if ("".equals(pointOfSale)) {
            pointOfSale = null;
        }
        List<DeskCategoryOut> deskCategoryOutList=deskDetailHistoryService.getCategoryParse(beginTime, endTime, pointOfSale);
        Double totalMoney=0.0;
        for (DeskCategoryOut deskCategoryOut : deskCategoryOutList) {
            totalMoney+=deskCategoryOut.getTotal();
        }
        for (DeskCategoryOut deskCategoryOut : deskCategoryOutList) {
            deskCategoryOut.setPercent(szMath.formatPercent(deskCategoryOut.getTotal()/totalMoney));
        }
        return deskCategoryOutList;
    }
}
