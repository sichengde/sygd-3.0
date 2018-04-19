package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.jsonModel.QuerySubReport;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.service.DebtIntegrationService;
import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.service.GuestIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-07.
 */
@RestController
public class DebtIntegrationController {
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    GuestIntegrationService guestIntegrationService;

    @RequestMapping(value = "debtIntegrationGet")
    public List<DebtIntegration> debtIntegrationGet(@RequestBody Query query) throws Exception {
        List<DebtIntegration> debtIntegrationList=debtIntegrationService.get(query);
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            debtIntegration.setGuestName(guestIntegrationService.getGuestNameBySelfAccount(debtIntegration.getSelfAccount()));
        }
        return debtIntegrationList;
    }

}
