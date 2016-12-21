package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.StorageOutDept;
import com.sygdsoft.service.StorageOutDeptService;
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
public class StorageOutDeptController {
    @Autowired
    StorageOutDeptService storageOutDeptService;

    @RequestMapping(value = "storageOutDeptAdd")
    public void storageOutDeptAdd(@RequestBody StorageOutDept storageOutDept) throws Exception {
        storageOutDeptService.add(storageOutDept);
    }

    @RequestMapping(value = "storageOutDeptDelete")
    @Transactional(rollbackFor = Exception.class)
    public void storageOutDeptDelete(@RequestBody List<StorageOutDept> storageOutDeptList) throws Exception {
        storageOutDeptService.delete(storageOutDeptList);
    }

    @RequestMapping(value = "storageOutDeptUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void storageOutDeptUpdate(@RequestBody List<StorageOutDept> storageOutDeptList) throws Exception {
        if (storageOutDeptList.size() > 1) {
            if (storageOutDeptList.get(0).getId().equals(storageOutDeptList.get(storageOutDeptList.size() / 2).getId())) {
                storageOutDeptService.update(storageOutDeptList.subList(0, storageOutDeptList.size() / 2));
                return;
            }
        }
        storageOutDeptService.update(storageOutDeptList);
    }

    @RequestMapping(value = "storageOutDeptGet")
    public List<StorageOutDept> storageOutDeptGet(@RequestBody Query query) throws Exception {
        return storageOutDeptService.get(query);
    }
}
