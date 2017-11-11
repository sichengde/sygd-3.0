package com.sygdsoft.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.model.Currency;
import com.sygdsoft.model.PointOfSale;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.CurrencyService;
import com.sygdsoft.service.PointOfSaleService;
import com.sygdsoft.service.VipDetailService;
import com.sygdsoft.service.VipIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class VipPayReportController {
    @Autowired
    VipIntegrationService vipIntegrationService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    PointOfSaleService pointOfSaleService;

    /**
     *
     * @param reportJson
     * {
     *     headerList:[{
     *         header:表头,
     *         field:域名,
     *         pointOfSale:销售点,
     *         type:1充值,2抵用
     *     }],
     *     dataList:[{}],
     * }
     */
    @RequestMapping(value = "vipPayReport")
    public JSONObject vipPayReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        List<Currency> currencyList = currencyService.get(null);
        List<PointOfSale> pointOfSaleList = pointOfSaleService.get(null);
        JSONObject returnJSON=new JSONObject();//返回值当中含有表头信息和数据信息
        List<JSONObject> headerList=new ArrayList<>();
        List<JSONObject> dataList=new ArrayList<>();
        /*初始化表头*/
        for (PointOfSale pointOfSale : pointOfSaleList) {
            String pos=pointOfSale.getFirstPointOfSale();
            /*充值表头*/
            JSONObject header=new JSONObject();
            header.put("header",pos+"(充值)");
            header.put("field",pos+"(充值)");
            header.put("pointOfSale",pos);
            header.put("type",1);
            headerList.add(header);
            /*抵用表头*/
            header=new JSONObject();
            header.put("header",pos+"(抵用)");
            header.put("field",pos+"(抵用)");
            header.put("pointOfSale",pos);
            header.put("type",2);
            headerList.add(header);
        }
        for (Currency currency : currencyList) {
            /*数据*/
            JSONObject data=new JSONObject();
            data.put("currency",currency.getCurrency());
            Double payTotal=0.0;
            Double deserveTotal=0.0;
            for (PointOfSale pointOfSale : pointOfSaleList) {
                String pos=pointOfSale.getFirstPointOfSale();
                /*充值数据*/
                Double pay=vipIntegrationService.getPay(beginTime, endTime, null,currency.getCurrency(),pos);
                payTotal+=pay;
                data.put(pos+"(充值)",pay);
                /*抵用数据*/
                Double deserve=vipIntegrationService.getDeserve(beginTime, endTime, null,currency.getCurrency(),pos);
                deserveTotal+=deserve;
                data.put(pos+"(抵用)",deserve);
            }
            /*计算合计*/
            data.put("payTotal",payTotal);
            data.put("deserveTotal",deserveTotal);
            dataList.add(data);
        }
        returnJSON.put("headerList",headerList);
        returnJSON.put("dataList",dataList);
        return returnJSON;
    }
}
