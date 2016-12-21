package com.sygdsoft.service;

import com.sygdsoft.mapper.SaleCountMapper;
import com.sygdsoft.model.SaleCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-06-17.
 */
@Service
@SzMapper(id = "saleCount")
public class SaleCountService extends BaseService<SaleCount>{
    @Autowired
    SaleCountMapper saleCountMapper;
    /**
     * 通过类别名称获得一条数据
     */
    public SaleCount getByName(String firstPointOfSale,String name){
        SaleCount saleCountQuery=new SaleCount();
        saleCountQuery.setFirstPointOfSale(firstPointOfSale);
        saleCountQuery.setName(name);
        return saleCountMapper.selectOne(saleCountQuery);
    }
}
