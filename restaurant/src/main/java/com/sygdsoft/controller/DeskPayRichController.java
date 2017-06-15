package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskPayRich;
import com.sygdsoft.service.DeskPayRichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-03-16.
 */
@RestController
public class DeskPayRichController {
    @Autowired
    DeskPayRichService deskPayRichService;

    @RequestMapping("deskPayRichGet")
    public List<DeskPayRich> deskPayRichGet(@RequestBody Query query) throws Exception {
        query.setOrderByListDesc(new String[]{"doneTime"});
        return deskPayRichService.get(query);
    }
}
