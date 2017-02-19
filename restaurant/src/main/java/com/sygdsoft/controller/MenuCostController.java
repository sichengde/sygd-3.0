package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.MenuCost;
import com.sygdsoft.service.MenuCostService;
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
public class MenuCostController {
    @Autowired
    MenuCostService menuCostService;

    @RequestMapping(value = "menuCostAdd")
    public void menuCostAdd(@RequestBody MenuCost menuCost) throws Exception {
        menuCostService.add(menuCost);
    }

    @RequestMapping(value = "menuCostDelete")
    @Transactional(rollbackFor = Exception.class)
    public void menuCostDelete(@RequestBody List<MenuCost> menuCostList) throws Exception {
        menuCostService.delete(menuCostList);
    }

    @RequestMapping(value = "menuCostUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void menuCostUpdate(@RequestBody List<MenuCost> menuCostList) throws Exception {
        if (menuCostList.size() > 1) {
            if (menuCostList.get(0).getId().equals(menuCostList.get(menuCostList.size() / 2).getId())) {
                menuCostService.update(menuCostList.subList(0, menuCostList.size() / 2));
                return;
            }
        }
        menuCostService.update(menuCostList);
    }

    @RequestMapping(value = "menuCostGet")
    public List<MenuCost> menuCostGet(@RequestBody Query query) throws Exception {
        return menuCostService.get(query);
    }
}
