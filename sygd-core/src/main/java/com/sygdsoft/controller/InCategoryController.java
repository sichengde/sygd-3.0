package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.InCategory;
import com.sygdsoft.service.InCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-02.
 */
@RestController
public class InCategoryController {
    @Autowired
    InCategoryService inCategoryService;

    @RequestMapping(value = "inCategoryAdd")
    public void inCategoryAdd(@RequestBody InCategory inCategory) throws Exception {
        inCategoryService.add(inCategory);
    }

    @RequestMapping(value = "inCategoryDelete")
    @Transactional(rollbackFor = Exception.class)
    public void inCategoryDelete(@RequestBody List<InCategory> inCategoryList) throws Exception {
        inCategoryService.delete(inCategoryList);
    }

    @RequestMapping(value = "inCategoryUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void inCategoryUpdate(@RequestBody List<InCategory> inCategoryList) throws Exception {
        if (inCategoryList.size() > 1) {
            if (inCategoryList.get(0).getId().equals(inCategoryList.get(inCategoryList.size() / 2).getId())) {
                inCategoryService.update(inCategoryList.subList(0, inCategoryList.size() / 2));
                return;
            }
        }
        inCategoryService.update(inCategoryList);
    }

    @RequestMapping(value = "inCategoryGet")
    public List<InCategory> inCategoryGet(@RequestBody Query query) throws Exception {
        return inCategoryService.get(query);
    }
}
