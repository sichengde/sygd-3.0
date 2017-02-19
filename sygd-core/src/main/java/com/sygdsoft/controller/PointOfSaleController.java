package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.CookHelper;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.PointOfSale;
import com.sygdsoft.service.PointOfSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-05-19.
 */
@RestController
public class PointOfSaleController {
    @Autowired
    PointOfSaleService pointOfSaleService;

    @RequestMapping(value = "pointOfSaleGet")
    public List<PointOfSale> pointOfSaleGetAllSecond(@RequestBody Query query) throws Exception {
        return pointOfSaleService.get(query);
    }

    @RequestMapping(value = "pointOfSaleDelete")
    @Transactional(rollbackFor = Exception.class)
    public void pointOfSaleDelete(@RequestBody List<PointOfSale> pointOfSaleList) throws Exception {
        pointOfSaleService.delete(pointOfSaleList);
    }

    @RequestMapping(value = "pointOfSaleAdd")
    public void pointOfSaleAdd(@RequestBody PointOfSale pointOfSale) throws Exception {
        pointOfSaleService.add(pointOfSale);
    }

    @RequestMapping(value = "pointOfSaleUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void pointOfSaleUpdate(@RequestBody List<PointOfSale> pointOfSaleList) throws Exception {
        if (pointOfSaleList.size() > 1) {
            if (pointOfSaleList.get(0).getId().equals(pointOfSaleList.get(pointOfSaleList.size() / 2).getId())) {
                pointOfSaleService.update(pointOfSaleList.subList(0, pointOfSaleList.size() / 2));
                return;
            }
        }
        pointOfSaleService.update(pointOfSaleList);
    }
}
