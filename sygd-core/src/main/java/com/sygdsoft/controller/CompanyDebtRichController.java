package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CompanyDebtRich;
import com.sygdsoft.service.CompanyDebtRichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-03-03.
 */
@RestController
public class CompanyDebtRichController {
    @Autowired
    CompanyDebtRichService companyDebtRichService;

    @RequestMapping(value = "companyDebtRichGet")
    public List<CompanyDebtRich> companyDebtRichGet(@RequestBody Query query) throws Exception {
        return companyDebtRichService.get(query);
    }
}
