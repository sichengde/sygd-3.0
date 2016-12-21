package com.sygdsoft.service;

import com.sygdsoft.mapper.DeskInMapper;
import com.sygdsoft.model.DeskIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-09-13.
 */
@Service
@SzMapper(id = "deskIn")
public class DeskInService extends BaseService<DeskIn>{
    @Autowired
    DeskInMapper deskInMapper;
    /**
     * 通过桌号和销售点获得桌台对象
     */
    public DeskIn getByDesk(String deskName,String pointOfSale){
        DeskIn deskInQuery=new DeskIn();
        deskInQuery.setDesk(deskName);
        deskInQuery.setPointOfSale(pointOfSale);
        return deskInMapper.selectOne(deskInQuery);
    }
}
