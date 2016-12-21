package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.StorageRemain;
import com.sygdsoft.service.StorageRemainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-22.
 * 视图控制器，只有查找
 */
@RestController
public class StorageRemainController {
    @Autowired
    StorageRemainService storageRemainService;

    @RequestMapping(value = "storageRemainGet")
    public List<StorageRemain> storageRemainGet(@RequestBody Query query) throws Exception {
        return storageRemainService.get(query);
    }
}
