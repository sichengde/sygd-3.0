package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CompanyPay;
import com.sygdsoft.service.CompanyPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-23.
 */
@RestController
public class CompanyPayController {
    @Autowired
    CompanyPayService companyPayService;

    @RequestMapping(value = "companyPayGet")
    public List<CompanyPay> companyPayGet(@RequestBody Query query) throws Exception {
        return companyPayService.get(query);
    }
}
