package com.sygdsoft.service;

import com.sygdsoft.mapper.CompanyDebtHistoryMapper;
import com.sygdsoft.model.CompanyDebtHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-23.
 */
@Service
@SzMapper(id = "companyDebtHistory")
public class CompanyDebtHistoryService extends BaseService<CompanyDebtHistory>{
    @Autowired
    CompanyDebtHistoryMapper companyDebtHistoryMapper;
    /**
     * 获得某段时间内的回款（不是实收款）
     */
    public Double getBackMoney(String companyName, Date beginTime, Date endTime){
        return companyDebtHistoryMapper.getBackMoney(companyName, beginTime, endTime);
    }
}
