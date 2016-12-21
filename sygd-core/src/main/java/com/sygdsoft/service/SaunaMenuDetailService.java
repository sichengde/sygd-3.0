package com.sygdsoft.service;

import com.sygdsoft.mapper.SaunaMenuDetailMapper;
import com.sygdsoft.model.SaunaMenuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-12-02.
 */
@Service
@SzMapper(id = "saunaMenuDetail")
public class SaunaMenuDetailService extends BaseService<SaunaMenuDetail>{
    @Autowired
    SaunaMenuDetailMapper saunaMenuDetailMapper;
    /**
     * 根据账单类型和菜谱获取单价
     */
    public Double getByNameCategory(String menu,String inCategory){
        SaunaMenuDetail saunaMenuDetail=new SaunaMenuDetail();
        saunaMenuDetail.setInCategory(inCategory);
        saunaMenuDetail.setMenu(menu);
        return saunaMenuDetailMapper.selectOne(saunaMenuDetail).getPrice();
    }
}
