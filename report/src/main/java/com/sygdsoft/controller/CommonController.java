package com.sygdsoft.controller;

import com.sygdsoft.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/10/6 0006.
 * 公共方法
 */
@RestController
public class CommonController {
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "getAllPrinter")
    public List<String> getAllPrinter(){
        return  reportService.getAllPrinter();
    }
}
