package com.sygdsoft.service;

import com.sygdsoft.mapper.HuaYuanMapper;
import com.sygdsoft.model.DebtIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HuaYuanService {
    @Autowired
    HuaYuanMapper huaYuanMapper;
    public Double getGuestSourceConsume(Date beginTime,Date endTime,String countCategory){
        return huaYuanMapper.getGuestSourceConsume(beginTime,endTime,countCategory);
    }
}
