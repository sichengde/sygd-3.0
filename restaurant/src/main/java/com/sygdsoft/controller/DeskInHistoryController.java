package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskInHistory;
import com.sygdsoft.service.DeskInHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-18.
 */
@RestController
public class DeskInHistoryController {
    @Autowired
    DeskInHistoryService deskInHistoryService;

    @RequestMapping(value = "deskInHistoryGet")
    public List<DeskInHistory> deskInHistoryGet(@RequestBody Query query) throws Exception {
        return deskInHistoryService.get(query);
    }
}
