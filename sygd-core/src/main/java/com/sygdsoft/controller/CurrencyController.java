package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Currency;
import com.sygdsoft.service.CurrencyService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 * 币种控制器，这里默认定义了5个币种，数据库中可以自行添加其他币种，由于只有一列，采用字符串方式，前端没有记录id号，因此没有修改，只有增加，删除，查找
 */
@RestController
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "currencyAdd")
    public void currencyAdd(@RequestBody Currency currency) throws Exception {
        currencyService.add(currency);
    }

    @RequestMapping(value = "currencyDelete")
    @Transactional(rollbackFor = Exception.class)
    public void currencyDelete(@RequestBody List<Currency> currencyList) throws Exception {
        currencyService.delete(currencyList);
    }

    @RequestMapping(value = "currencyGet")
    public List<Currency> currencyGet(@RequestBody Query query) throws Exception {
        return currencyService.get(query);
    }

    @RequestMapping(value = "currencyUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void currencyUpdate(@RequestBody List<Currency> currencyList) throws Exception {
        if (currencyList.size() > 1) {
            if (currencyList.get(0).getId().equals(currencyList.get(currencyList.size() / 2).getId())) {
                timeService.setNow();
                currencyService.update(currencyList.subList(0, currencyList.size() / 2));
                return;
            }
        }
        currencyService.update(currencyList);
    }
}
