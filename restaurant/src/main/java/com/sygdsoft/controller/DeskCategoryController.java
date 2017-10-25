package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskCategory;
import com.sygdsoft.service.DeskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-10-21.
 */
@RestController
public class DeskCategoryController {
    @Autowired
    DeskCategoryService deskCategoryService;

    @RequestMapping(value = "deskCategoryAdd")
    public void deskCategoryAdd(@RequestBody DeskCategory deskCategory) throws Exception {
        deskCategoryService.add(deskCategory);
    }

    @RequestMapping(value = "deskCategoryDelete")
    @Transactional(rollbackFor = Exception.class)
    public void deskCategoryDelete(@RequestBody List<DeskCategory> menuList) throws Exception {
        deskCategoryService.delete(menuList);
    }

    @RequestMapping(value = "deskCategoryGet")
    public List<DeskCategory> deskCategoryGet(@RequestBody Query query) throws Exception {
        return deskCategoryService.get(query);
    }
}
