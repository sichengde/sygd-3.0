package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.SaunaMenu;
import com.sygdsoft.service.SaunaMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@RestController
public class SaunaMenuController {
    @Autowired
    SaunaMenuService saunaMenuService;

    @RequestMapping(value = "saunaMenuAdd")
    public void saunaMenuAdd(@RequestBody SaunaMenu saunaMenu) throws Exception {
        saunaMenuService.add(saunaMenu);
    }

    @RequestMapping(value = "saunaMenuDelete")
    @Transactional(rollbackFor = Exception.class)
    public void saunaMenuDelete(@RequestBody List<SaunaMenu> saunaMenuList) throws Exception {
        saunaMenuService.delete(saunaMenuList);
    }

    @RequestMapping(value = "saunaMenuUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void saunaMenuUpdate(@RequestBody List<SaunaMenu> saunaMenuList) throws Exception {
        if (saunaMenuList.size() > 1) {
            if (saunaMenuList.get(0).getId().equals(saunaMenuList.get(saunaMenuList.size() / 2).getId())) {
                saunaMenuService.update(saunaMenuList.subList(0, saunaMenuList.size() / 2));
                return;
            }
        }
        saunaMenuService.update(saunaMenuList);
    }

    @RequestMapping(value = "saunaMenuGet")
    public List<SaunaMenu> saunaMenuGet(@RequestBody Query query) throws Exception {
        return saunaMenuService.get(query);
    }
}
