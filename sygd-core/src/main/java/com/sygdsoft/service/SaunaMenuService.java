package com.sygdsoft.service;

import com.sygdsoft.mapper.SaunaMenuMapper;
import com.sygdsoft.model.SaunaMenu;
import org.apache.http.cookie.SM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-12-01.
 */
@Service
@SzMapper(id = "saunaMenu")
public class SaunaMenuService extends BaseService<SaunaMenu>{
    @Autowired
    SaunaMenuMapper saunaMenuMapper;
    /**
     * 根据货品名称获取桑拿货品
     */
    public SaunaMenu getByName(String name){
        SaunaMenu saunaMenu=new SaunaMenu();
        saunaMenu.setName(name);
        return saunaMenuMapper.selectOne(saunaMenu);
    }
}
