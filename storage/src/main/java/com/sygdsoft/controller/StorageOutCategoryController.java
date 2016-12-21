package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.StorageOutCategory;
import com.sygdsoft.service.StorageOutCategoryService;
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
public class StorageOutCategoryController {
    @Autowired
    StorageOutCategoryService storageOutCategoryService;

    @RequestMapping(value = "storageOutCategoryAdd")
    public void storageOutCategoryAdd(@RequestBody StorageOutCategory storageOutCategory) throws Exception {
        storageOutCategoryService.add(storageOutCategory);
    }

    @RequestMapping(value = "storageOutCategoryDelete")
    @Transactional(rollbackFor = Exception.class)
    public void storageOutCategoryDelete(@RequestBody List<StorageOutCategory> storageOutCategoryList) throws Exception {
        storageOutCategoryService.delete(storageOutCategoryList);
    }

    @RequestMapping(value = "storageOutCategoryUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void storageOutCategoryUpdate(@RequestBody List<StorageOutCategory> storageOutCategoryList) throws Exception {
        if (storageOutCategoryList.size() > 1) {
            if (storageOutCategoryList.get(0).getId().equals(storageOutCategoryList.get(storageOutCategoryList.size() / 2).getId())) {
                storageOutCategoryService.update(storageOutCategoryList.subList(0, storageOutCategoryList.size() / 2));
                return;
            }
        }
        storageOutCategoryService.update(storageOutCategoryList);
    }

    @RequestMapping(value = "storageOutCategoryGet")
    public List<StorageOutCategory> storageOutCategoryGet(@RequestBody Query query) throws Exception {
        return storageOutCategoryService.get(query);
    }
}
