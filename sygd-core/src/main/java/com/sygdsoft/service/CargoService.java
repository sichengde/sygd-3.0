package com.sygdsoft.service;

import com.sygdsoft.mapper.CargoMapper;
import com.sygdsoft.model.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
@Service
@SzMapper(id = "cargo")
public class CargoService extends BaseService<Cargo>{
    @Autowired
    CargoMapper cargoMapper;
    /**
     * 根据类别获取货物
     */
    public List<Cargo> getListByCategory(String category){
        Cargo cargoQuery=new Cargo();
        cargoQuery.setCategory(category);
        return cargoMapper.select(cargoQuery);
    }
    /**
     * 根据货物名称获得
     */
    public Cargo getByName(String name){
        Cargo cargoQuery=new Cargo();
        cargoQuery.setName(name);
        return cargoMapper.selectOne(cargoQuery);
    }
}
