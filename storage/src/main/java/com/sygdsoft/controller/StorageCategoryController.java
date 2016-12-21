package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.StorageCategory;
import com.sygdsoft.service.StorageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
@RestController
public class StorageCategoryController {
    @Autowired
    StorageCategoryService storageCategoryService;

    @RequestMapping(value = "storageCategoryAdd")
    public void storageCategoryAdd(@RequestBody StorageCategory storageCategory) throws Exception {
        storageCategoryService.add(storageCategory);
    }

    @RequestMapping(value = "storageCategoryDelete")
    @Transactional(rollbackFor = Exception.class)
    public void storageCategoryDelete(@RequestBody List<StorageCategory> storageCategoryList) throws Exception {
        storageCategoryService.delete(storageCategoryList);
    }

    @RequestMapping(value = "storageCategoryUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void storageCategoryUpdate(@RequestBody List<StorageCategory> storageCategoryList) throws Exception {
        if (storageCategoryList.size() > 1) {
            if (storageCategoryList.get(0).getId().equals(storageCategoryList.get(storageCategoryList.size() / 2).getId())) {
                storageCategoryService.update(storageCategoryList.subList(0, storageCategoryList.size() / 2));
                return;
            }
        }
        storageCategoryService.update(storageCategoryList);
    }

    @RequestMapping(value = "storageCategoryGet")
    public List<StorageCategory> storageCategoryGet(@RequestBody Query query) throws Exception {
        return storageCategoryService.get(query);
    }
}
