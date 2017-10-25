package com.sygdsoft.service;

import com.sygdsoft.mapper.DeskPayRichMapper;
import com.sygdsoft.model.DeskPayRich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2017-03-16.
 */
@Service
@SzMapper(id = "deskPayRich")
public class DeskPayRichService extends BaseService<DeskPayRich>{
    @Autowired
    DeskPayRichMapper deskPayRichMapper;

    public Double getPay(String currency, String pointOfSale,String category, Date beginTime, Date endTime){
        return deskPayRichMapper.getPay(currency, pointOfSale, category, beginTime, endTime);
    }
}
