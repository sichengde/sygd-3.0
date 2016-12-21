package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.SaunaDetailHistory;
import com.sygdsoft.service.SaunaDetailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@RestController
public class SaunaDetailHistoryController {
    @Autowired
    SaunaDetailHistoryService saunaDetailHistoryService;

    @RequestMapping(value = "saunaDetailHistoryGet")
    public List<SaunaDetailHistory> saunaDetailHistoryGet(@RequestBody Query query) throws Exception {
        return saunaDetailHistoryService.get(query);
    }
}
