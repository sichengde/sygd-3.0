package com.sygdsoft.controller;

import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 舒展 on 2017-03-23.
 */
@RestController
public class ShenBeiTiChengController {
    @Autowired
    ReportService reportService;
    @RequestMapping(value = "getShenBeiTiCheng")
    public Integer getTiCheng(@RequestBody ReportJson reportJson){

        return 1;
    }
}
