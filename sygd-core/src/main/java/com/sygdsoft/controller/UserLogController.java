package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.UserLog;
import com.sygdsoft.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-15.
 */
@RestController
public class UserLogController {
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "userLogGet")
    public List<UserLog> userLogGetSome(@RequestBody Query query) throws Exception {
        return userLogService.get(query);
    }
}
