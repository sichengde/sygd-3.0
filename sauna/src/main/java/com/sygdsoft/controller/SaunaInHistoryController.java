package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.SaunaInHistory;
import com.sygdsoft.service.SaunaInHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@RestController
public class SaunaInHistoryController {
    @Autowired
    SaunaInHistoryService saunaInHistoryService;

    @RequestMapping(value = "saunaInHistoryGet")
    public List<SaunaInHistory> saunaInHistoryGet(@RequestBody Query query) throws Exception {
        return saunaInHistoryService.get(query);
    }
}
