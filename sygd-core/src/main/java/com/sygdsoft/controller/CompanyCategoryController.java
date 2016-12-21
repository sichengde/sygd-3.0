package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CompanyCategory;
import com.sygdsoft.service.CompanyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-07-15.
 */
@RestController
public class CompanyCategoryController {
    @Autowired
    CompanyCategoryService companyCategoryService;

    @RequestMapping(value = "companyCategoryAdd")
    public void companyCategoryAdd(@RequestBody CompanyCategory companyCategory) throws Exception {
        companyCategoryService.add(companyCategory);
    }

    @RequestMapping(value = "companyCategoryDelete")
    @Transactional
    public void companyCategoryDelete(@RequestBody List<CompanyCategory> companyCategoryList) throws Exception {
        companyCategoryService.delete(companyCategoryList);
    }

    @RequestMapping(value = "companyCategoryGet")
    public List<CompanyCategory> companyCategoryGet(@RequestBody Query query) throws Exception {
        return companyCategoryService.get(query);
    }
}
