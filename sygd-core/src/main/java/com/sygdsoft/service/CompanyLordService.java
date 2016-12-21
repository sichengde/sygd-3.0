package com.sygdsoft.service;

import com.sygdsoft.mapper.CompanyLordMapper;
import com.sygdsoft.model.CompanyLord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-06-21.
 */
@Service
@SzMapper(id = "companyLord")
public class CompanyLordService extends BaseService<CompanyLord>{
    @Autowired
    CompanyLordMapper companyLordMapper;

    /**
     * 增加一笔挂账
     */
    public void addDebt(String lord,Double debt){
        companyLordMapper.addDebt(lord, debt);
    }
}
