package com.sygdsoft.controller.room;

import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.DebtIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentParseDailyReportController {
    @Autowired
    DebtIntegrationService debtIntegrationService;

    @RequestMapping(value = "rentParseDailyReport")
    public List<DebtIntegration> rentParseDailyReport(@RequestBody ReportJson reportJson) throws Exception {
        return debtIntegrationService.getDailyCount(reportJson.getBeginTime(),reportJson.getEndTime(),reportJson.getNotNullParamBool());
    }
}
