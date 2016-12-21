package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.service.CheckInHistoryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-08-24.
 */
@RestController
public class CheckInHistoryLogController {
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;

    @RequestMapping(value = "checkInHistoryLogGet")
    public List<CheckInHistoryLog> checkInHistoryLogGet(@RequestBody Query query) throws Exception {
        return checkInHistoryLogService.get(query);
    }
}
