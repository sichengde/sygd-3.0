package com.sygdsoft.service;

import com.sygdsoft.mapper.PayPointOfSaleMapper;
import com.sygdsoft.model.PayPointOfSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@SzMapper(id = "payPointOfSale")
@Service
public class PayPointOfSaleService extends BaseService<PayPointOfSale> {
    @Autowired
    PayPointOfSaleMapper payPointOfSaleMapper;

    /**
     * 根据营业部门和结算时间获取除了转哑房，转房客，转单位的结算款
     */
    public Double getDebtMoney(Date beginTime, Date endTime, String pointOfSale, String module) {
        return payPointOfSaleMapper.getDebtMoney(pointOfSale, beginTime, endTime,module);
    }

    /**
     *
     * @param beginTime
     * @param endTime
     * @param currency
     * @param company true=单位结算，false=不是单位结算
     * @return
     */
    public Double getDebtMoneyWithCreate(Date beginTime, Date endTime, String currency, boolean company) {
        return payPointOfSaleMapper.getDebtMoneyWithCreate(beginTime, endTime,currency,company);
    }

}
