package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.SaunaMenuDetail;
import com.sygdsoft.service.SaunaMenuDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-02.
 */
@RestController
public class SaunaMenuDetailController {
    @Autowired
    SaunaMenuDetailService saunaMenuDetailService;

    @RequestMapping(value = "saunaMenuDetailAdd")
    public void saunaMenuDetailAdd(@RequestBody SaunaMenuDetail saunaMenuDetail) throws Exception {
        saunaMenuDetailService.add(saunaMenuDetail);
    }

    @RequestMapping(value = "saunaMenuDetailDelete")
    @Transactional(rollbackFor = Exception.class)
    public void saunaMenuDetailDelete(@RequestBody List<SaunaMenuDetail> saunaMenuDetailList) throws Exception {
        saunaMenuDetailService.delete(saunaMenuDetailList);
    }

    @RequestMapping(value = "saunaMenuDetailUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void saunaMenuDetailUpdate(@RequestBody List<SaunaMenuDetail> saunaMenuDetailList) throws Exception {
        if (saunaMenuDetailList.size() > 1) {
            if (saunaMenuDetailList.get(0).getId().equals(saunaMenuDetailList.get(saunaMenuDetailList.size() / 2).getId())) {
                saunaMenuDetailService.update(saunaMenuDetailList.subList(0, saunaMenuDetailList.size() / 2));
                return;
            }
        }
        saunaMenuDetailService.update(saunaMenuDetailList);
    }

    @RequestMapping(value = "saunaMenuDetailGet")
    public List<SaunaMenuDetail> saunaMenuDetailGet(@RequestBody Query query) throws Exception {
        return saunaMenuDetailService.get(query);
    }
}
