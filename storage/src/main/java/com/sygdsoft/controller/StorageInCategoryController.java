package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.StorageInCategory;
import com.sygdsoft.service.StorageInCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
@RestController
public class StorageInCategoryController {
    @Autowired
    StorageInCategoryService storageInCategoryService;

    @RequestMapping(value = "storageInCategoryAdd")
    public void storageInCategoryAdd(@RequestBody StorageInCategory storageInCategory) throws Exception {
        storageInCategoryService.add(storageInCategory);
    }

    @RequestMapping(value = "storageInCategoryDelete")
    @Transactional(rollbackFor = Exception.class)
    public void storageInCategoryDelete(@RequestBody List<StorageInCategory> storageInCategoryList) throws Exception {
        storageInCategoryService.delete(storageInCategoryList);
    }

    @RequestMapping(value = "storageInCategoryUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void storageInCategoryUpdate(@RequestBody List<StorageInCategory> storageInCategoryList) throws Exception {
        if (storageInCategoryList.size() > 1) {
            if (storageInCategoryList.get(0).getId().equals(storageInCategoryList.get(storageInCategoryList.size() / 2).getId())) {
                storageInCategoryService.update(storageInCategoryList.subList(0, storageInCategoryList.size() / 2));
                return;
            }
        }
        storageInCategoryService.update(storageInCategoryList);
    }

    @RequestMapping(value = "storageInCategoryGet")
    public List<StorageInCategory> storageInCategoryGet(@RequestBody Query query) throws Exception {
        return storageInCategoryService.get(query);
    }
}
