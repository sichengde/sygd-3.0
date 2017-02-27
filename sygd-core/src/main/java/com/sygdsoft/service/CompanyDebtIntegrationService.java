package com.sygdsoft.service;

import com.sygdsoft.mapper.CompanyDebtIntegrationMapper;
import com.sygdsoft.model.CompanyDebtIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-24.
 */
@Service
@SzMapper(id = "companyDebtIntegration")
public class CompanyDebtIntegrationService extends BaseService<CompanyDebtIntegration>{
    @Autowired
    CompanyDebtIntegrationMapper companyDebtIntegrationMapper;
    /**
     * 获得某个单位某段时间内的挂账款
     */
    /*正负都算*/
    public Double getDebtByCompanyDate(String company, Date beginTime, Date endTime){
        return companyDebtIntegrationMapper.getDebtByCompanyDate(company,beginTime,endTime);
    }
}
