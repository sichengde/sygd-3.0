package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.SaunaRing;
import com.sygdsoft.service.SaunaRingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@RestController
public class SaunaRingController {
    @Autowired
    SaunaRingService saunaRingService;

    @RequestMapping(value = "saunaRingAdd")
    public void saunaRingAdd(@RequestBody SaunaRing saunaRing) throws Exception {
        saunaRingService.add(saunaRing);
    }

    @RequestMapping(value = "saunaRingDelete")
    @Transactional(rollbackFor = Exception.class)
    public void saunaRingDelete(@RequestBody List<SaunaRing> saunaRingList) throws Exception {
        saunaRingService.delete(saunaRingList);
    }

    @RequestMapping(value = "saunaRingUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void saunaRingUpdate(@RequestBody List<SaunaRing> saunaRingList) throws Exception {
        if (saunaRingList.size() > 1) {
            if (saunaRingList.get(0).getId().equals(saunaRingList.get(saunaRingList.size() / 2).getId())) {
                saunaRingService.update(saunaRingList.subList(0, saunaRingList.size() / 2));
                return;
            }
        }
        saunaRingService.update(saunaRingList);
    }

    @RequestMapping(value = "saunaRingGet")
    public List<SaunaRing> saunaRingGet(@RequestBody Query query) throws Exception {
        List<SaunaRing> saunaRingList=saunaRingService.get(query);
        saunaRingService.setRingDetail(saunaRingList);
        return saunaRingList;
    }
}
