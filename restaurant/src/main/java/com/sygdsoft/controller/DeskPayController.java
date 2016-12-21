package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.QuerySubReport;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskPay;
import com.sygdsoft.service.DeskPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-18.
 */
@RestController
public class DeskPayController {
    @Autowired
    DeskPayService deskPayService;

    @RequestMapping(value = "deskPayGet")
    public List<DeskPay> deskPayGet(@RequestBody Query query) throws Exception {
        return deskPayService.get(query);
    }
    /**
     * 根据时间段和币种查询
     */
    @RequestMapping(value = "deskPayGetByDateCurrency")
    public List<DeskPay> deskPayGetByDateCurrency(@RequestBody QuerySubReport querySubReport){
        String pointOfSale= querySubReport.getPointOfSale();
        String currency= querySubReport.getCurrency();
        Date beginTime= querySubReport.getBeginTime();
        Date endTime= querySubReport.getEndTime();
        return deskPayService.getByCurrencyDatePointOfSale(pointOfSale, currency, beginTime, endTime);
    }

}
