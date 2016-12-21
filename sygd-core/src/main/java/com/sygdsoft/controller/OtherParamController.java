package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.OtherParamMapper;
import com.sygdsoft.model.OtherParam;
import com.sygdsoft.service.OtherParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class OtherParamController {
    @Autowired
    OtherParamService otherParamService;

    @RequestMapping(value = "otherParamAdd")
    public void otherParamAdd(@RequestBody OtherParam otherParam) throws Exception {
        otherParamService.add(otherParam);
    }

    @RequestMapping(value = "otherParamDelete")
    @Transactional(rollbackFor = Exception.class)
    public void otherParamDelete(@RequestBody List<OtherParam> otherParamList) throws Exception {
        otherParamService.delete(otherParamList);
    }

    @RequestMapping(value = "otherParamUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void otherParamUpdate(@RequestBody List<OtherParam> otherParamList) throws Exception {
        if (otherParamList.size() > 1) {
            if (otherParamList.get(0).getId().equals(otherParamList.get(otherParamList.size() / 2).getId())) {
                otherParamService.update(otherParamList.subList(0, otherParamList.size() / 2));
                return;
            }
        }
        otherParamService.update(otherParamList);
    }

    @RequestMapping(value = "otherParamGet")
    public List<OtherParam> otherParamGet(@RequestBody Query query) throws Exception {
        return otherParamService.get(query);
    }
}
