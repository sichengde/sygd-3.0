package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.PointOfSaleShop;
import com.sygdsoft.service.PointOfSaleShopService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class PointOfSaleShopController {
    @Autowired
    PointOfSaleShopService pointOfSaleShopService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "pointOfSaleShopAdd")
    public void pointOfSaleShopAdd(@RequestBody PointOfSaleShop pointOfSaleShop) throws Exception {
        pointOfSaleShopService.add(pointOfSaleShop);
    }

    @RequestMapping(value = "pointOfSaleShopDelete")
    @Transactional(rollbackFor = Exception.class)
    public void pointOfSaleShopDelete(@RequestBody List<PointOfSaleShop> pointOfSaleShopList) throws Exception {
        pointOfSaleShopService.delete(pointOfSaleShopList);
    }

    @RequestMapping(value = "pointOfSaleShopGet")
    public List<PointOfSaleShop> pointOfSaleShopGet(@RequestBody Query query) throws Exception {
        return pointOfSaleShopService.get(query);
    }

    @RequestMapping(value = "pointOfSaleShopUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void pointOfSaleShopUpdate(@RequestBody List<PointOfSaleShop> pointOfSaleShopList) throws Exception {
        if (pointOfSaleShopList.size() > 1) {
            if (pointOfSaleShopList.get(0).getId().equals(pointOfSaleShopList.get(pointOfSaleShopList.size() / 2).getId())) {
                pointOfSaleShopService.update(pointOfSaleShopList.subList(0, pointOfSaleShopList.size() / 2));
                return;
            }
        }
        pointOfSaleShopService.update(pointOfSaleShopList);
    }
}
