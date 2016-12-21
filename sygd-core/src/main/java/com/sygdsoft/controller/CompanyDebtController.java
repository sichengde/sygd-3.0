package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.CompanyDebtMapper;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.service.CompanyDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class CompanyDebtController {
    @Autowired
    CompanyDebtService companyDebtService;

    @RequestMapping(value = "companyDebtGet")
    public List<CompanyDebt> companyDebtGet(@RequestBody Query query) throws Exception {
        return companyDebtService.get(query);
    }
}
