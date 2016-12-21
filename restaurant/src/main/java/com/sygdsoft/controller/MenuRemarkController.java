package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.MenuRemark;
import com.sygdsoft.service.MenuRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-10-21.
 */
@RestController
public class MenuRemarkController {
    @Autowired
    MenuRemarkService menuRemarkService;

    @RequestMapping(value = "menuRemarkAdd")
    public void menuRemarkAdd(@RequestBody MenuRemark menuRemark) throws Exception {
        menuRemarkService.add(menuRemark);
    }

    @RequestMapping(value = "menuRemarkDelete")
    @Transactional(rollbackFor = Exception.class)
    public void menuRemarkDelete(@RequestBody List<MenuRemark> menuList) throws Exception {
        menuRemarkService.delete(menuList);
    }

    @RequestMapping(value = "menuRemarkGet")
    public List<MenuRemark> menuRemarkGet(@RequestBody Query query) throws Exception {
        return menuRemarkService.get(query);
    }
}
