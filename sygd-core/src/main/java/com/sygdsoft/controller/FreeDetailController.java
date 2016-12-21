package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.FreeDetail;
import com.sygdsoft.service.FreeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-07-26.
 */
@RestController
public class FreeDetailController {
    @Autowired
    FreeDetailService freeDetailService;

    @RequestMapping(value = "freeDetailGet")
    public List<FreeDetail> freeDetailGet(@RequestBody Query query) throws Exception {
        return freeDetailService.get(query);
    }
}
