package com.sygdsoft.service;

import com.sygdsoft.mapper.MenuMapper;
import com.sygdsoft.model.Desk;
import com.sygdsoft.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Service
@SzMapper(id = "menu")
public class MenuService extends BaseService<Menu>{
    @Autowired
    MenuMapper menuMapper;
    /**
     * 通过菜名查找数据
     */
    public Menu getByName(String name){
        Menu menuQuery=new Menu();
        menuQuery.setName(name);
        return menuMapper.selectOne(menuQuery);
    }
}
