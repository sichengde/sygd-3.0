package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.SaleCountMapper;
import com.sygdsoft.model.SaleCount;
import com.sygdsoft.service.SaleCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-14.
 */
@RestController
public class SaleCountController {
    @Autowired
    SaleCountService saleCountService;

    @RequestMapping(value = "saleCountAdd")
    public void saleCountAdd(@RequestBody SaleCount saleCount) throws Exception {
        saleCountService.add(saleCount);
    }

    @RequestMapping(value = "saleCountDelete")
    @Transactional(rollbackFor = Exception.class)
    public void saleCountDelete(@RequestBody List<SaleCount> saleCountList) throws Exception {
        saleCountService.delete(saleCountList);
    }

    @RequestMapping(value = "saleCountUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void saleCountUpdate(@RequestBody List<SaleCount> saleCountList) throws Exception {
        if (saleCountList.size() > 1) {
            if (saleCountList.get(0).getId().equals(saleCountList.get(saleCountList.size() / 2).getId())) {
                saleCountService.update(saleCountList.subList(0, saleCountList.size() / 2));
                return;
            }
        }
        saleCountService.update(saleCountList);
    }

    @RequestMapping(value = "saleCountGet")
    public List<SaleCount> saleCountGet(@RequestBody Query query) throws Exception {
        return saleCountService.get(query);
    }
}
