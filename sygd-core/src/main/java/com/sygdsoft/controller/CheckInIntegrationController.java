package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckInIntegration;
import com.sygdsoft.service.CheckInIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-01-05.
 */
@RestController
public class CheckInIntegrationController {
    @Autowired
    CheckInIntegrationService checkInIntegrationService;

    @RequestMapping(value = "checkInIntegrationGet")
    public List<CheckInIntegration> CheckInIntegrationGet(@RequestBody Query query) throws Exception {
        return checkInIntegrationService.get(query);
    }
}
