package com.sygdsoft.service;

import com.sygdsoft.mapper.CompanyPayMapper;
import com.sygdsoft.model.CompanyPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-23.
 */
@Service
@SzMapper(id = "companyPay")
public class CompanyPayService extends BaseService<CompanyPay> {
    @Autowired
    CompanyPayMapper companyPayMapper;

    /**
     * 获取一段时间内的单位支付款
     */
    public CompanyPay getSumPay(String company,String userId, String currency,Date beginTime, Date endTime) {
        return companyPayMapper.getSumPay(company,userId, currency, beginTime, endTime);
    }

}
