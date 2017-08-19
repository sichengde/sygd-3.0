package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckOutMapper;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "checkOut")
public class CheckOutService extends BaseService<CheckOut> {
    @Autowired
    CheckOutMapper checkOutMapper;

    /**
     * 判断来期在起始日期之前，结账日期在范围内的找回金额(deposit-consume)
     */
    public List<CheckOut> getPayBack(Date beginTime, Date endTime) {
        return checkOutMapper.getPayBack(beginTime,endTime);
    }
}
