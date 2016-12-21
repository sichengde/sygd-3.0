package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.VipIdNumber;
import com.sygdsoft.service.VipIdNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-07-22.
 */
@RestController
public class VipIdNumberController {
    @Autowired
    VipIdNumberService vipIdNumberService;

    @RequestMapping(value = "vipIdNumberGet")
    public List<VipIdNumber> vipIdNumberGet(@RequestBody Query query) throws Exception {
        return vipIdNumberService.get(query);
    }
}
