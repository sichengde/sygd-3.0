package com.sygdsoft.service;

import com.sygdsoft.mapper.SaunaInMapper;
import com.sygdsoft.model.SaunaIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
@Service
@SzMapper(id = "saunaIn")
public class SaunaInService extends BaseService<SaunaIn>{
    @Autowired
    SaunaInMapper saunaInMapper;
    /**
     * 通过手牌号获取
     */
    public SaunaIn getByRing(String ring){
        SaunaIn saunaInQuery=new SaunaIn();
        saunaInQuery.setRing(ring);
        return saunaInMapper.selectOne(saunaInQuery);
    }
    /**
     * 根据时间段获取账单总数
     */
    public Integer getCountByDate(Date beginTime,Date endTime){
        return saunaInMapper.getCountByDate(beginTime, endTime);
    }

    /**
     * 总消费加上一个数
     */
    public void addConsume(String ring,Double consume){
        saunaInMapper.addConsume(ring, consume);
    }
}
