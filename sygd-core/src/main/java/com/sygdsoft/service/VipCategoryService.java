package com.sygdsoft.service;

import com.sygdsoft.mapper.VipCategoryMapper;
import com.sygdsoft.model.VipCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-07-21.
 */
@Service
@SzMapper(id = "vipCategory")
public class VipCategoryService extends BaseService<VipCategory>{
    @Autowired
    VipCategoryMapper vipCategoryMapper;

    public VipCategory getByCategory(String category){
        VipCategory vipCategoryQuery=new VipCategory();
        vipCategoryQuery.setCategory(category);
        return vipCategoryMapper.selectOne(vipCategoryQuery);
    }
}
