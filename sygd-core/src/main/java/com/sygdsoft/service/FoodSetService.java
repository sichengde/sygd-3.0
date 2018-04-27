package com.sygdsoft.service;

import com.sygdsoft.mapper.FoodSetMapper;
import com.sygdsoft.model.DeskDetail;
import com.sygdsoft.model.FoodSet;
import com.sygdsoft.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-11-02.
 */
@Service
@SzMapper(id = "foodSet")
public class FoodSetService extends BaseService<FoodSet>{
    @Autowired
    FoodSetMapper foodSetMapper;
    @Autowired
    MenuService menuService;
    /**
     * 通过套餐名称获取所有菜拼
     */
    public List<FoodSet> getBySetName(String setName){
        FoodSet foodSetQuery=new FoodSet();
        foodSetQuery.setSetName(setName);
        return foodSetMapper.select(foodSetQuery);
    }

    /**
     * 通过套餐名称转化为所有点菜的菜品
     */
    public List<DeskDetail> getDeskDetailBySetName(DeskDetail foodSetDeskDetail){
        List<FoodSet> foodSetList = getBySetName(foodSetDeskDetail.getFoodSign());
        List<DeskDetail> deskDetailList=new ArrayList<>();
        for (FoodSet foodSet : foodSetList) {
            Menu menu=menuService.getByName(foodSet.getFoodName());
            DeskDetail deskDetail=new DeskDetail(foodSetDeskDetail);
            deskDetail.setNum(foodSet.getFoodNum()*foodSetDeskDetail.getNum());
            deskDetail.setFoodName(foodSet.getFoodName());
            deskDetail.setFoodSign(foodSet.getFoodName());
            deskDetail.setPrice(0.0);
            deskDetail.setNeedInsert(true);
            if(menu!=null) {
                deskDetail.setCategory(menu.getCategory());
                deskDetail.setCookRoom(menu.getCookRoom());
                deskDetail.setCargo(menu.getCargo());
            }
            deskDetailList.add(deskDetail);
        }
        return deskDetailList;
    }


    public void deleteBySetName(String name) {
        foodSetMapper.deleteBySetName(name);
    }
}
