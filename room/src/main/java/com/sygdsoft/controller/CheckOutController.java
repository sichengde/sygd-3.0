package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckOut;
import com.sygdsoft.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-06.
 */
@RestController
public class CheckOutController {
    @Autowired
    CheckOutService checkOutService;

    @RequestMapping(value = "checkOutAdd")
    public void checkOutAdd(@RequestBody CheckOut checkOut) throws Exception{
        checkOutService.add(checkOut);
    }
    @RequestMapping(value = "checkOutDelete")
    @Transactional(rollbackFor = Exception.class)
    public void checkOutDelete(@RequestBody List<CheckOut> checkOutList) throws Exception{
        checkOutService.delete(checkOutList);
    }
    @RequestMapping(value = "checkOutUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void checkOutUpdate(@RequestBody List<CheckOut> checkOutList) throws Exception{
        if (checkOutList.size() > 1) {
            if (checkOutList.get(0).getId().equals(checkOutList.get(checkOutList.size() / 2).getId())) {
                checkOutService.update(checkOutList.subList(0, checkOutList.size() / 2));
                return;
            }
        }
        checkOutService.update(checkOutList);
    }
    @RequestMapping(value = "checkOutGet")
    public List<CheckOut> checkOut(@RequestBody Query query) throws Exception{
        return checkOutService.get(query);
    }

}
