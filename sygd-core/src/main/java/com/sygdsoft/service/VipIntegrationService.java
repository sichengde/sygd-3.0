package com.sygdsoft.service;

import com.sygdsoft.mapper.VipIntegrationMapper;
import com.sygdsoft.model.VipIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2016-11-30.
 */
@Service
@SzMapper(id = "vipIntegration")
public class VipIntegrationService extends BaseService<VipIntegration> {
    @Autowired
    VipIntegrationMapper vipIntegrationMapper;

    /**
     * 获取该时间段内总充值钱数
     */
    public Double getPay(Date beginTime, Date endTime, String userId, String currency, String pointOfSale) {
        return vipIntegrationMapper.getPay(userId, currency, pointOfSale, beginTime, endTime);
    }

    /**
     * 获取会员抵用金额
     */
    public Double getDeserve(Date beginTime, Date endTime, String userId, String currency, String pointOfSale) {
        return vipIntegrationMapper.getDeserve(beginTime, endTime, userId, pointOfSale, currency);
    }
}
