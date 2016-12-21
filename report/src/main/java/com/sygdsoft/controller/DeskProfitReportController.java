package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.report.DeskProfitOut;
import com.sygdsoft.model.DeskProfitQuery;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DeskDetailHistoryService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-11-15.
 * 毛利率
 */
@RestController
public class DeskProfitReportController {
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "deskProfitReport")
    public List<DeskProfitOut> deskProfitReport(@RequestBody DeskProfitQuery deskProfitQuery) {
        Date beginTime = deskProfitQuery.getBeginTime();
        Date endTime = deskProfitQuery.getEndTime();
        String pointOfSale = deskProfitQuery.getPointOfSale();
        if ("".equals(pointOfSale)) {
            pointOfSale = null;
        }
        String categories = deskProfitQuery.getCategory();
        if ("".equals(categories)) {
            categories = null;
        }
        List<DeskProfitOut> deskProfitOutList=new ArrayList<>();
        if (categories != null) {
            String[] categoryList = categories.split(",");
            for (String category : categoryList) {
                DeskProfitOut deskProfitOut = new DeskProfitOut();
                deskProfitOut.setFoodName(category+" : ");
                deskProfitOutList.add(deskProfitOut);
                deskProfitOutList.addAll(deskDetailHistoryService.getDeskProfitList(beginTime, endTime, pointOfSale, category));
            }
        } else {
            deskProfitOutList = deskDetailHistoryService.getDeskProfitList(beginTime, endTime, pointOfSale, null);
        }
        for (DeskProfitOut deskProfitOut : deskProfitOutList) {
            if(deskProfitOut.getNum()!=null) {//为空是分类时的类别标题
                Double profit = deskProfitOut.getAfterDiscount() - deskProfitOut.getTotalCost();
                deskProfitOut.setCostRate(szMath.formatPercent(profit / deskProfitOut.getTotalCost()));//sql中做了非空判断
                deskProfitOut.setSaleRate(szMath.formatPercent(profit / deskProfitOut.getAfterDiscount()));
            }
        }
        return deskProfitOutList;
    }
}
