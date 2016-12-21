package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.VipIntegration;
import com.sygdsoft.service.VipIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-30.
 */
@RestController
public class VipIntegrationController {
    @Autowired
    VipIntegrationService vipIntegrationService;
    @RequestMapping(value = "vipIntegrationGet")
    public List<VipIntegration> vipIntegrationGet(@RequestBody Query query) throws Exception {
        return vipIntegrationService.get(query);
    }
}
