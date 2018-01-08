package com.sygdsoft.service;

import com.sygdsoft.mapper.CompanyMoneyMapper;
import com.sygdsoft.model.CompanyMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SzMapper(id = "companyMoney")
public class CompanyMoneyService extends BaseService<CompanyMoney>{
    @Autowired
    CompanyMoneyMapper companyMoneyMapper;

    /**
     * 获得单位的可用余额列表
     */
    public List<CompanyMoney> getSumList(String company){
        return companyMoneyMapper.getSumList(company);
    }
}
