package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CookRoom;
import com.sygdsoft.model.Menu;
import com.sygdsoft.model.SaleCount;
import com.sygdsoft.service.CleanRoomService;
import com.sygdsoft.service.CookRoomService;
import com.sygdsoft.service.MenuService;
import com.sygdsoft.service.SaleCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@RestController
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    CookRoomService cookRoomService;
    @Autowired
    SaleCountService saleCountService;

    @RequestMapping(value = "menuAdd")
    @Transactional
    public void menuAdd(@RequestBody Menu menu) throws Exception {
        /*检查厨房有没有*/
        //checkCookRoom(menu.getCookRoom());
        /*增加的时候看看有没有厨房，没有的话用营业部门的同步*/
        if (menu.getCookRoom() == null || menu.getCookRoom().equals("")) {
            SaleCount saleCount = saleCountService.getByName(menu.getPointOfSale(), menu.getCategory());
            menu.setCookRoom(saleCount.getCookRoom());
        }
        menuService.add(menu);
    }

    @RequestMapping(value = "menuDelete")
    @Transactional(rollbackFor = Exception.class)
    public void menuDelete(@RequestBody List<Menu> menuList) throws Exception {
        menuService.delete(menuList);
    }

    @RequestMapping(value = "menuUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void menuUpdate(@RequestBody List<Menu> menuList) throws Exception {
        if (menuList.size() > 1) {
            if (menuList.get(0).getId().equals(menuList.get(menuList.size() / 2).getId())) {
                List<Menu> menuListHalf=menuList.subList(0, menuList.size() / 2);
                menuService.update(menuListHalf);
                return;
            }
        }
        menuService.update(menuList);
    }

    @RequestMapping(value = "menuGet")
    public List<Menu> menuGet(@RequestBody Query query) throws Exception {
        return menuService.get(query);
    }

    private void checkCookRoom(String cookRoom) throws Exception {
        if(cookRoom!=null) {
            String[] cookRoomList = cookRoom.split(",");
            for (String s : cookRoomList) {
                if (cookRoomService.getByCookName(s).size()==0) {
                    throw new Exception("厨房:" + s + " 不存在");
                }
            }
        }
    }
}
