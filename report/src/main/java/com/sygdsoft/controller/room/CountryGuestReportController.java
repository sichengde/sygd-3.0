package com.sygdsoft.controller.room;

import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.CountryGuestRow;
import com.sygdsoft.model.room.CountryGuestReturn;
import com.sygdsoft.service.GuestIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-03-28.
 */
@RestController
public class CountryGuestReportController {
    @Autowired
    GuestIntegrationService guestIntegrationService;
    @Autowired
    GuestIntegrationService getGuestIntegrationService;
    @RequestMapping(value = "countryGuestReport")
    public CountryGuestReturn countryGuestReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        //TODO: 根据整数日期排列
        CountryGuestReturn countryGuestReturn=new CountryGuestReturn();
        countryGuestReturn.setCountryGuestRowList(guestIntegrationService.getList(beginTime, endTime));
        countryGuestReturn.setRemark("本地客人:"+guestIntegrationService.getLocalGuestSum(beginTime, endTime)+",外地客人:"+guestIntegrationService.getOtherGuestSum(beginTime, endTime));
        return countryGuestReturn;
    }
}
