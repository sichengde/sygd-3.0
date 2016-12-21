package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskDiscount;
import com.sygdsoft.service.DeskDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-10-27.
 */
@RestController
public class DeskDiscountController {
    @Autowired
    DeskDiscountService deskDiscountService;

    @RequestMapping(value = "deskDiscountAdd")
    public void deskDiscountAdd(@RequestBody DeskDiscount deskDiscount) throws Exception {
        deskDiscountService.add(deskDiscount);
    }

    @RequestMapping(value = "deskDiscountDelete")
    @Transactional(rollbackFor = Exception.class)
    public void deskDiscountDelete(@RequestBody List<DeskDiscount> deskDiscountList) throws Exception {
        deskDiscountService.delete(deskDiscountList);
    }

    @RequestMapping(value = "deskDiscountUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void deskDiscountUpdate(@RequestBody List<DeskDiscount> deskDiscountList) throws Exception {
        if (deskDiscountList.size() > 1) {
            if (deskDiscountList.get(0).getId().equals(deskDiscountList.get(deskDiscountList.size() / 2).getId())) {
                deskDiscountService.update(deskDiscountList.subList(0, deskDiscountList.size() / 2));
                return;
            }
        }
        deskDiscountService.update(deskDiscountList);
    }

    @RequestMapping(value = "deskDiscountGet")
    public List<DeskDiscount> deskDiscountGet(@RequestBody Query query) throws Exception {
        return deskDiscountService.get(query);
    }
}
