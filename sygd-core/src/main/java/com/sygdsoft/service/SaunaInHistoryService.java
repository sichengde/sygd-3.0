package com.sygdsoft.service;

import com.sygdsoft.mapper.SaunaInHistoryMapper;
import com.sygdsoft.model.SaunaInHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
@Service
@SzMapper(id = "saunaInHistory")
public class SaunaInHistoryService extends BaseService<SaunaInHistory>{
    @Autowired
    SaunaInHistoryMapper saunaInHistoryMapper;
    /**
     * 根据时间段获取账单总数
     */
    public Integer getCountByDate(Date beginTime, Date endTime){
        return saunaInHistoryMapper.getCountByDate(beginTime, endTime);
    }
}
