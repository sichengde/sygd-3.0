package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.HouseMapper;
import com.sygdsoft.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
@Service
@SzMapper(id = "house")
public class HouseService extends BaseService<House>{
    @Autowired
    HouseMapper houseMapper;
    /**
     * 通过仓库名称获取
     */
    public House getByHouseName(String house){
        House houseQuery=new House();
        houseQuery.setHouseName(house);
        return houseMapper.selectOne(houseQuery);
    }
}
