package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.FoodSet;
import com.sygdsoft.service.FoodSetService;
import com.sygdsoft.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-02.
 */
@RestController
public class FoodSetController {
    @Autowired
    FoodSetService foodSetService;
    @Autowired
    MenuService menuService;

    @RequestMapping(value = "foodSetAdd")
    public void foodSetAdd(@RequestBody FoodSet foodSet) throws Exception {
        /*检验输入的菜名菜品中是否可以找到*/
        if(menuService.getByName(foodSet.getFoodName())==null){
            throw new Exception("菜品名:"+foodSet.getFoodName()+",在菜谱中不存在");
        }
        foodSetService.add(foodSet);
    }

    @RequestMapping(value = "foodSetDelete")
    @Transactional(rollbackFor = Exception.class)
    public void foodSetDelete(@RequestBody List<FoodSet> foodSetList) throws Exception {
        foodSetService.delete(foodSetList);
    }

    @RequestMapping(value = "foodSetUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void foodSetUpdate(@RequestBody List<FoodSet> foodSetList) throws Exception {
        for (FoodSet foodSet : foodSetList) {
            if(menuService.getByName(foodSet.getFoodName())==null){
                throw new Exception("菜品名:"+foodSet.getFoodName()+",在菜谱中不存在");
            }
        }
        /*检验输入的菜名菜品中是否可以找到*/
        if (foodSetList.size() > 1) {
            if (foodSetList.get(0).getId().equals(foodSetList.get(foodSetList.size() / 2).getId())) {
                foodSetService.update(foodSetList.subList(0, foodSetList.size() / 2));
                return;
            }
        }
        foodSetService.update(foodSetList);
    }

    @RequestMapping(value = "foodSetGet")
    public List<FoodSet> foodSetGet(@RequestBody Query query) throws Exception {
        return foodSetService.get(query);
    }
}
