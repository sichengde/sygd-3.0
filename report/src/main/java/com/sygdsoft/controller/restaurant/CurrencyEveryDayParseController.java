package com.sygdsoft.controller.restaurant;

import com.alibaba.fastjson.JSONArray;
import com.sygdsoft.model.DeskPay;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DeskPayService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class CurrencyEveryDayParseController {
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    TimeService timeService;

    @RequestMapping(value = "currencyEveryDayParse")
    public List<DeskPay> currencyEveryDayParse(@RequestBody ReportJson reportJson) throws ParseException {
        return deskPayService.getConsumeEveryDay(reportJson.getBeginTime(),timeService.getMaxTime(reportJson.getEndTime()));
    }
}
