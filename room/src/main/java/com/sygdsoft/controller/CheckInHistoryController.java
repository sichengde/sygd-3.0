package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.service.CheckInHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-08-24.
 */
@RestController
public class CheckInHistoryController {
    @Autowired
    CheckInHistoryService checkInHistoryService;

    @RequestMapping(value = "checkInHistoryGet")
    public List<CheckInHistory> checkInHistoryGet(@RequestBody Query query) throws Exception {
        return checkInHistoryService.get(query);
    }
}
