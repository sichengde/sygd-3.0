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
     * 获取该日期总充值钱数
     */
    public Double getTotalPay(Date beginTime, Date endTime) {
        return vipIntegrationMapper.getTotalPay(beginTime, endTime);
    }

    /**
     * 获取该时间段内总充值钱数
     */
    public Double getTotalPayTimeZone(String userId, String currency, Date beginTime, Date endTime) {
        if (userId == null) {
            return vipIntegrationMapper.getPay(currency, beginTime, endTime);
        } else {
            return vipIntegrationMapper.getPay(userId, currency, beginTime, endTime);
        }
    }

    /**
     * 获取该时间段会员退款钱数
     */
    public Double getTotalDeserveTimeZone(String userId,String currency, Date beginTime, Date endTime) {
        if (userId == null) {
            return vipIntegrationMapper.getDeserve(currency,beginTime, endTime);
        } else {
            return vipIntegrationMapper.getDeserveByUser(userId,currency, beginTime, endTime);
        }
    }
}
