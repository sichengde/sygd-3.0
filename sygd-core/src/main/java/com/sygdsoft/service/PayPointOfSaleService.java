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
     * 获取除了转哑房，转房客，转单位的结算款
     */
    public Double getDebtMoney(Date beginTime, Date endTime, String pointOfSale) {
        return payPointOfSaleMapper.getDebtMoney(pointOfSale, beginTime, endTime);
    }
}
