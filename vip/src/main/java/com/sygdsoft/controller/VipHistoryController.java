package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.VipHistory;
import com.sygdsoft.service.VipHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
@RestController
public class VipHistoryController {
    @Autowired
    VipHistoryService vipHistoryService;

    @RequestMapping(value = "vipHistoryGet")
    public List<VipHistory> vipHistoryGet(@RequestBody Query query) throws Exception {
        return vipHistoryService.get(query);
    }
}
