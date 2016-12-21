package com.sygdsoft.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-18.
 * 获取服务器时间
 */
@RestController
public class TimeController {
    @RequestMapping(value = "time")
    public Date getNowTime(){
        return new Date();
    }
}
