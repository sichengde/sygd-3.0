package com.sygdsoft.service;

import com.sygdsoft.mapper.FoodSetMapper;
import com.sygdsoft.model.DeskDetail;
import com.sygdsoft.model.FoodSet;
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
        List<FoodSet> foodSetList = getBySetName(foodSetDeskDetail.getFoodName());
        List<DeskDetail> deskDetailList=new ArrayList<>();
        for (FoodSet foodSet : foodSetList) {
            DeskDetail deskDetail=new DeskDetail(foodSetDeskDetail);
            deskDetail.setNum(foodSet.getFoodNum());
            deskDetail.setFoodName(foodSet.getFoodName());
            deskDetail.setFoodSign(foodSet.getFoodName());
            deskDetail.setPrice(null);
            deskDetailList.add(deskDetail);
        }
        return deskDetailList;
    }
}
