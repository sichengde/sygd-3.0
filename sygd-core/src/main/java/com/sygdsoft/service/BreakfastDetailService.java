package com.sygdsoft.service;

import com.sygdsoft.mapper.BreakfastDetailMapper;
import com.sygdsoft.model.BreakfastDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SzMapper(id = "breakfastDetail")
public class BreakfastDetailService extends BaseService<BreakfastDetail> {
    @Autowired
    BreakfastDetailMapper breakfastDetailMapper;

    public int breakfastDetailGetCount(String selfAccount) {
        return breakfastDetailMapper.breakfastDetailGetCount(selfAccount);
    }
}
