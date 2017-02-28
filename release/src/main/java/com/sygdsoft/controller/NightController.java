package com.sygdsoft.controller;

import com.sygdsoft.service.Night;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 舒展 on 2016-09-01.
 */
@RestController
public class NightController {
    @Autowired
    Night night;
    @Autowired
    UserLogService userLogService;
    @Autowired
    TimeService timeService;

    @RequestMapping(value = "manualNightAction")
    @Transactional(rollbackFor = Exception.class)
    public void manualNightAction() throws Exception {
        night.manualNightAction();
        userLogService.addUserLog("手动夜审", userLogService.reception, userLogService.night,null);
    }
}
