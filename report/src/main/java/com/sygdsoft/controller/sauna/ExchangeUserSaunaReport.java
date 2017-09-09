package com.sygdsoft.controller.sauna;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.model.Currency;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.CurrencyService;
import com.sygdsoft.service.SaunaPayService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.VipIntegrationService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ExchangeUserSaunaReport {
    @Autowired
    TimeService timeService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    SaunaPayService saunaPayService;
    @Autowired
    VipIntegrationService vipIntegrationService;
    @Autowired
    SzMath szMath;
    /**
     * 桑拿交班审核表
     */
    @RequestMapping(value = "exchangeUserSaunaReport")
    public List<JSONObject> exchangeUserSaunaReport(@RequestBody ReportJson reportJson) throws Exception {
        String userId = reportJson.getUserId();
        if ("".equals(userId)) {
            userId = null;
        }
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        timeService.setNow();
        List<JSONObject> exchangeUserSaunaList = new ArrayList<>();
        List<Currency> currencyList = currencyService.get(null);
        for (Currency currency : currencyList) {
            /*打印报表赋值*/
            String currencyString = currency.getCurrency();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("currency",currency.getCurrency());//币种
            jsonObject.put("payMoney",szMath.ifNotNullGetString(saunaPayService.getDebtMoney(userId, currencyString, beginTime, endTime)));//币种
            jsonObject.put("vipMoney",szMath.ifNotNullGetString(vipIntegrationService.getTotalPayTimeZone(userId, currencyString, beginTime, endTime)));//币种
            exchangeUserSaunaList.add(jsonObject);
        }
        return exchangeUserSaunaList;
    }
}
