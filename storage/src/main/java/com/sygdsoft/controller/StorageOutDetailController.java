package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-17.
 */
@RestController
public class StorageOutDetailController {
    @Autowired
    StorageOutDetailService storageOutDetailService;

    @RequestMapping(value = "storageOutDetailGet")
    public List<StorageOutDetail> storageOutDetailGet(@RequestBody Query query) throws Exception {
        return storageOutDetailService.get(query);
    }

    @RequestMapping(value = "storageOutDetailRichGet")
    public List<StorageOutDetail> getStorageOutDetailRich(@RequestBody ReportJson reportJson) {
        return this.storageOutDetailService.getStorageOutDetailParse(reportJson.getBeginTime(),reportJson.getEndTime(),reportJson.getParam1());
    }
}
