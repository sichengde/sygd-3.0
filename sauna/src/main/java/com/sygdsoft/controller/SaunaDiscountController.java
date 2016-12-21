package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.SaunaDiscount;
import com.sygdsoft.service.SaunaDiscountService;
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
public class SaunaDiscountController {
    @Autowired
    SaunaDiscountService saunaDiscountService;

    @RequestMapping(value = "saunaDiscountAdd")
    public void saunaDiscountAdd(@RequestBody SaunaDiscount saunaDiscount) throws Exception {
        saunaDiscountService.add(saunaDiscount);
    }

    @RequestMapping(value = "saunaDiscountDelete")
    @Transactional(rollbackFor = Exception.class)
    public void saunaDiscountDelete(@RequestBody List<SaunaDiscount> saunaDiscountList) throws Exception {
        saunaDiscountService.delete(saunaDiscountList);
    }

    @RequestMapping(value = "saunaDiscountUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void saunaDiscountUpdate(@RequestBody List<SaunaDiscount> saunaDiscountList) throws Exception {
        if (saunaDiscountList.size() > 1) {
            if (saunaDiscountList.get(0).getId().equals(saunaDiscountList.get(saunaDiscountList.size() / 2).getId())) {
                saunaDiscountService.update(saunaDiscountList.subList(0, saunaDiscountList.size() / 2));
                return;
            }
        }
        saunaDiscountService.update(saunaDiscountList);
    }

    @RequestMapping(value = "saunaDiscountGet")
    public List<SaunaDiscount> saunaDiscountGet(@RequestBody Query query) throws Exception {
        return saunaDiscountService.get(query);
    }
}
