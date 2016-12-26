package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckOutGroupMapper;
import com.sygdsoft.model.CheckOutGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-09-12.
 */
@Service
@SzMapper(id = "checkOutGroup")
public class CheckOutGroupService extends BaseService<CheckOutGroup>{
    @Autowired
    CheckOutGroupMapper checkOutGroupMapper;
    /**
     * 通过离店结算序列号查找
     */
    public CheckOutGroup getByCheckOutSerial(String checkOutSerial){
        CheckOutGroup checkOutGroupQuery=new CheckOutGroup();
        checkOutGroupQuery.setCheckOutSerial(checkOutSerial);
        return checkOutGroupMapper.selectOne(checkOutGroupQuery);
    }
}
