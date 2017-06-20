package com.sygdsoft.controller.restaurant;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.model.HotelParseLine;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DebtIntegrationService;
import com.sygdsoft.service.DeskDetailHistoryService;
import com.sygdsoft.service.DeskInHistoryService;
import org.joda.time.chrono.LimitChronology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-06-20.
 */
@RestController
public class ChartController {
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    DeskInHistoryService deskInHistoryService;

    @RequestMapping("deskMoneyDateChart")
    List<HotelParseLineRow> deskMoneyDateChart(@RequestBody ReportJson reportJson) {
        return deskDetailHistoryService.getDeskMoneyDateLineByDatePointOfSale(reportJson.getBeginTime(), reportJson.getEndTime(), reportJson.getPointOfSale(), null);
    }

    @RequestMapping("deskManDateChart")
    List<HotelParseLineRow> deskManDateChart(@RequestBody ReportJson reportJson) {
        return deskInHistoryService.deskManDateChart(reportJson.getBeginTime(), reportJson.getEndTime());
    }

    @RequestMapping("roomConsumeChart")
    List<HotelParseLineRow> roomConsumeChart(@RequestBody ReportJson reportJson){
        return debtIntegrationService.roomConsumeChart(reportJson.getBeginTime(),reportJson.getEndTime());
    }
}
