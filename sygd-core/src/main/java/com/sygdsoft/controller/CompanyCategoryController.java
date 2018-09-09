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
    @Transactional(rollbackFor = Exception.class)
    public void companyCategoryDelete(@RequestBody List<CompanyCategory> companyCategoryList) throws Exception {
        companyCategoryService.delete(companyCategoryList);
    }

    @RequestMapping(value = "companyCategoryUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void companyCategoryUpdate(@RequestBody List<CompanyCategory> companyCategoryList) throws Exception {
        if (companyCategoryList.size() > 1) {
            if (companyCategoryList.get(0).getId().equals(companyCategoryList.get(companyCategoryList.size() / 2).getId())) {
                companyCategoryService.update(companyCategoryList.subList(0, companyCategoryList.size() / 2));
                return;
            }
        }
        companyCategoryService.update(companyCategoryList);
    }

    @RequestMapping(value = "companyCategoryGet")
    public List<CompanyCategory> companyCategoryGet(@RequestBody Query query) throws Exception {
        return companyCategoryService.get(query);
    }
}
