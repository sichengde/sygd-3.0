package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.TelDetail;
import com.sygdsoft.service.TelDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-01-13.
 */
@RestController
public class TelDetailController {
    @Autowired
    TelDetailService telDetailService;

    @RequestMapping("telDetailGet")
    public List<TelDetail> telDetailGet(@RequestBody Query query) throws Exception{
        return telDetailService.get(query);
    }
}
