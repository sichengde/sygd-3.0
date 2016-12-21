package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.VipDetail;
import com.sygdsoft.service.VipDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-07-21.
 */
@RestController
public class VipDetailController {
    @Autowired
    VipDetailService vipDetailService;

    @RequestMapping(value = "vipDetailGet")
    public List<VipDetail> vipDetailGet(@RequestBody Query query) throws Exception {
        return vipDetailService.get(query);
    }
}
