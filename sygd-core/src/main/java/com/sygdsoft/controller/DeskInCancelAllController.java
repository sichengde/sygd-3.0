package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskInCancelAll;
import com.sygdsoft.service.DeskDetailCancelAllService;
import com.sygdsoft.service.DeskInCancelAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeskInCancelAllController {
    @Autowired
    DeskInCancelAllService deskInCancelAllService;
    @Autowired
    DeskDetailCancelAllService deskDetailCancelAllService;

    @RequestMapping("deskInCancelAllGet")
    public List<DeskInCancelAll> deskInCancelAllGet(@RequestBody Query query)throws Exception{
        List<DeskInCancelAll> deskInCancelAllList=deskInCancelAllService.get(query);
        for (DeskInCancelAll deskInCancelAll : deskInCancelAllList) {
            deskInCancelAll.setDeskDetailCancelAllList(deskDetailCancelAllService.getList(deskInCancelAll.getPointOfSale(),deskInCancelAll.getDoneTime()));
        }
        return deskInCancelAllList;
    }
}
