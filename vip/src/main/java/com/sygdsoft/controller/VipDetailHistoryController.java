package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.VipDetailHistory;
import com.sygdsoft.service.VipDetailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
@RestController
public class VipDetailHistoryController {
    @Autowired
    VipDetailHistoryService vipDetailHistoryService;
    @RequestMapping(value = "vipDetailHistoryGet")
    public List<VipDetailHistory> vipDetailHistoryGet(@RequestBody Query query) throws Exception {
        return vipDetailHistoryService.get(query);
    }
}
