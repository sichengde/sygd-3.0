package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckOutPayBackMapper;
import com.sygdsoft.model.CheckOutPayBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@SzMapper(id = "checkOutPayBack")
public class CheckOutPayBackService extends BaseService<CheckOutPayBack>{
    @Autowired
    CheckOutPayBackMapper checkOutPayBackMapper;
    /**
     * 获得时间段内该币种的找零总和
     */
    public Double getTotal(String userId,String currency,Date beginTime,Date endTime){
        if(userId==null) {
            return checkOutPayBackMapper.getTotal(beginTime, endTime, currency);
        }else {
            return checkOutPayBackMapper.getTotal(beginTime, endTime, currency,userId);
        }
    }
}
