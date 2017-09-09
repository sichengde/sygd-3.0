package com.sygdsoft.service;

import com.sygdsoft.mapper.SaunaPayMapper;
import com.sygdsoft.model.SaunaPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
@Service
@SzMapper(id = "saunaPay")
public class SaunaPayService extends BaseService<SaunaPay>{
    @Autowired
    SaunaPayMapper saunaPayMapper;
    /**
     * 获得该日期该币种的消费额
     */
    public Double getDebtMoney(String userId, String currency,  Date beginTime, Date endTime) {
        return saunaPayMapper.getPayMoney(userId, currency,  beginTime, endTime);
    }
}
