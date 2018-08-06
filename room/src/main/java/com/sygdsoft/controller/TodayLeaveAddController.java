package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.TodayLeaveAddMapper;
import com.sygdsoft.model.TodayLeaveAdd;
import com.sygdsoft.service.TodayLeaveAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-27.
 */
@RestController
public class TodayLeaveAddController {
    @Autowired
    TodayLeaveAddService todayLeaveAddService;


    @RequestMapping(value = "todayLeaveAddGet")
    public List<TodayLeaveAdd> todayLeaveAddGet(@RequestBody Query query) throws Exception {
        return todayLeaveAddService.get(query);
    }

    @RequestMapping(value = "todayLeaveAddAdd")
    public void add(@RequestBody TodayLeaveAdd todayLeaveAdd) throws Exception {
        todayLeaveAddService.add(todayLeaveAdd);
    }

    @RequestMapping(value = "todayLeaveAddDelete")
    @Transactional(rollbackFor = Exception.class)
    public void todayLeaveAddDelete(@RequestBody List<TodayLeaveAdd> todayLeaveAddList) throws Exception {
        todayLeaveAddService.delete(todayLeaveAddList);
    }

    @RequestMapping(value = "todayLeaveAddUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void todayLeaveAddUpdate(@RequestBody List<TodayLeaveAdd> todayLeaveAddList) throws Exception {
        if (todayLeaveAddList.size() > 1) {
            if (todayLeaveAddList.get(0).getId().equals(todayLeaveAddList.get(todayLeaveAddList.size() / 2).getId())) {
                todayLeaveAddService.update(todayLeaveAddList.subList(0, todayLeaveAddList.size() / 2));
                return;
            }
        }
        todayLeaveAddService.update(todayLeaveAddList);
    }
}