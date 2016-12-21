package com.sygdsoft.service;

import com.sygdsoft.mapper.SaunaDetailMapper;
import com.sygdsoft.model.SaunaDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@Service
@SzMapper(id = "saunaDetail")
public class SaunaDetailService extends BaseService<SaunaDetail> {
    @Autowired
    SaunaDetailMapper saunaDetailMapper;
    /**
     * 通过手牌号获取消费明细
     */
    public List<SaunaDetail> getByRing(String ring){
        SaunaDetail saunaDetail=new SaunaDetail();
        saunaDetail.setRing(ring);
        return saunaDetailMapper.select(saunaDetail);
    }
}
