package com.sygdsoft.controller.room;

import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.CountryGuestRow;
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
    @RequestMapping(value = "countryGuestReport")
    public List<CountryGuestRow> countryGuestReport(@RequestBody ReportJson reportJson){
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        return guestIntegrationService.getList(beginTime, endTime);
    }
}
