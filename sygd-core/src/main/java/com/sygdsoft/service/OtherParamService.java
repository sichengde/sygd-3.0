package com.sygdsoft.service;

import com.sygdsoft.mapper.OtherParamMapper;
import com.sygdsoft.model.OtherParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-06-17.
 */
@Service
@SzMapper(id = "otherParam")
public class OtherParamService extends BaseService<OtherParam> {
    @Autowired
    OtherParamMapper otherParamMapper;

    public String getValueByName(String name) {
        return otherParamMapper.getValueByName(name);
    }

    public void updateValueByName(String name,String value){
        otherParamMapper.updateValueByName(name, value);
    }
}
