package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CompanyDebtHistory;
import com.sygdsoft.service.CompanyDebtHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-24.
 */
@RestController
public class CompanyDebtHistoryController {
    @Autowired
    CompanyDebtHistoryService companyDebtHistoryService;

    @RequestMapping(value = "companyDebtHistoryGet")
    public List<CompanyDebtHistory> companyDebtHistory(@RequestBody Query query)throws Exception{
        return companyDebtHistoryService.get(query);
    }
}
