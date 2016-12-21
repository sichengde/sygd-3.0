package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.StorageInDept;
import com.sygdsoft.service.StorageInDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-17.
 */
@RestController
public class StorageInDeptController {
    @Autowired
    StorageInDeptService storageInDeptService;

    @RequestMapping(value = "storageInDeptAdd")
    public void storageInDeptAdd(@RequestBody StorageInDept storageInDept) throws Exception {
        storageInDeptService.add(storageInDept);
    }

    @RequestMapping(value = "storageInDeptDelete")
    @Transactional(rollbackFor = Exception.class)
    public void storageInDeptDelete(@RequestBody List<StorageInDept> storageInDeptList) throws Exception {
        storageInDeptService.delete(storageInDeptList);
    }

    @RequestMapping(value = "storageInDeptUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void storageInDeptUpdate(@RequestBody List<StorageInDept> storageInDeptList) throws Exception {
        if (storageInDeptList.size() > 1) {
            if (storageInDeptList.get(0).getId().equals(storageInDeptList.get(storageInDeptList.size() / 2).getId())) {
                storageInDeptService.update(storageInDeptList.subList(0, storageInDeptList.size() / 2));
                return;
            }
        }
        storageInDeptService.update(storageInDeptList);
    }

    @RequestMapping(value = "storageInDeptGet")
    public List<StorageInDept> storageInDeptGet(@RequestBody Query query) throws Exception {
        return storageInDeptService.get(query);
    }
}
