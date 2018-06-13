package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.RoomShopMapper;
import com.sygdsoft.model.RoomShop;
import com.sygdsoft.service.RoomShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-14.
 */
@RestController
public class RoomShopController {
    @Autowired
    RoomShopService roomShopService;

    @RequestMapping(value = "roomShopAdd")
    public void roomShopAdd(@RequestBody RoomShop roomShop) throws Exception {
        if(roomShop.getItem().indexOf("/")>0||roomShop.getItem().indexOf(":")>0){
            throw new Exception("不可以含有特殊符号斜杠或者冒号");
        }
        roomShopService.add(roomShop);
    }

    @RequestMapping(value = "roomShopDelete")
    @Transactional(rollbackFor = Exception.class)
    public void roomShopDelete(@RequestBody List<RoomShop> roomShopList) throws Exception {
        roomShopService.delete(roomShopList);
    }

    @RequestMapping(value = "roomShopUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void roomShopUpdate(@RequestBody List<RoomShop> roomShopList) throws Exception {
        if (roomShopList.size() > 1) {
            if (roomShopList.get(0).getId().equals(roomShopList.get(roomShopList.size() / 2).getId())) {
                roomShopService.update(roomShopList.subList(0, roomShopList.size() / 2));
                return;
            }
        }
        roomShopService.update(roomShopList);
    }

    @RequestMapping(value = "roomShopGet")
    public List<RoomShop> roomShopGet(@RequestBody Query query) throws Exception {
        return roomShopService.get(query);
    }
}
