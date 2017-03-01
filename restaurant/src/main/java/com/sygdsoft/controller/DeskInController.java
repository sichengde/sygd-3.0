package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskIn;
import com.sygdsoft.service.DeskInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-24.
 */
@RestController
public class DeskInController {
    @Autowired
    DeskInService deskInService;

    @RequestMapping(value = "deskInGet")
    public List<DeskIn> deskInGet(@RequestBody Query query) throws Exception {
        return deskInService.get(query);
    }

    @RequestMapping(value = "deskInGetWithDetail")
    public List<DeskIn> deskInGetWithDetail(@RequestBody Query query)throws Exception{
        List<DeskIn> deskInList=deskInService.get(query);
        deskInService.setDeskDetail(deskInList);
        return deskInList;
    }
}
