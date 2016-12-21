package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskBookHistory;
import com.sygdsoft.service.DeskBookHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-18.
 */
@RestController
public class DeskBookHistoryController {
    @Autowired
    DeskBookHistoryService deskBookHistoryService;

    @RequestMapping(value = "deskBookHistoryGet")
    public List<DeskBookHistory> deskBookHistoryGet(@RequestBody Query query) throws Exception {
        return deskBookHistoryService.get(query);
    }
}
