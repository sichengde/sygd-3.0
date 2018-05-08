package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskDetailCancelAll;
import com.sygdsoft.service.DeskDetailCancelAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeskDetailCancelAllController {
    @Autowired
    DeskDetailCancelAllService deskDetailCancelAllService;

    @RequestMapping("deskDetailCancelAllGet")
    public List<DeskDetailCancelAll> deskDetailCancelAllGet(@RequestBody Query query)throws Exception{
        return deskDetailCancelAllService.get(query);
    }
}
