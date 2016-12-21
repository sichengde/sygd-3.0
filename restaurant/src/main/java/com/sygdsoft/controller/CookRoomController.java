package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CookRoom;
import com.sygdsoft.service.CookRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-04.
 */
@RestController
public class CookRoomController {
    @Autowired
    CookRoomService cookRoomService;

    @RequestMapping(value = "cookRoomAdd")
    public void cookRoomAdd(@RequestBody CookRoom cookRoom) throws Exception {
        cookRoomService.add(cookRoom);
    }

    @RequestMapping(value = "cookRoomDelete")
    @Transactional(rollbackFor = Exception.class)
    public void cookRoomDelete(@RequestBody List<CookRoom> cookRoomList) throws Exception {
        cookRoomService.delete(cookRoomList);
    }

    @RequestMapping(value = "cookRoomUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void cookRoomUpdate(@RequestBody List<CookRoom> cookRoomList) throws Exception {
        if (cookRoomList.size() > 1) {
            if (cookRoomList.get(0).getId().equals(cookRoomList.get(cookRoomList.size() / 2).getId())) {
                cookRoomService.update(cookRoomList.subList(0, cookRoomList.size() / 2));
                return;
            }
        }
        cookRoomService.update(cookRoomList);
    }

    @RequestMapping(value = "cookRoomGet")
    public List<CookRoom> cookRoomGet(@RequestBody Query query) throws Exception {
        return cookRoomService.get(query);
    }
}
