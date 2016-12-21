package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.RoomPriceAddMapper;
import com.sygdsoft.model.RoomPriceAdd;
import com.sygdsoft.service.RoomPriceAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-27.
 */
@RestController
public class RoomPriceAddController {
    @Autowired
    RoomPriceAddService roomPriceAddService;


    @RequestMapping(value = "roomPriceAddGet")
    public List<RoomPriceAdd> roomPriceAddGet(@RequestBody Query query) throws Exception {
        return roomPriceAddService.get(query);
    }

    @RequestMapping(value = "roomPriceAddAdd")
    public void add(@RequestBody RoomPriceAdd roomPriceAdd) throws Exception {
        roomPriceAddService.add(roomPriceAdd);
    }

    @RequestMapping(value = "roomPriceAddDelete")
    @Transactional(rollbackFor = Exception.class)
    public void roomPriceAddDelete(@RequestBody List<RoomPriceAdd> roomPriceAddList) throws Exception {
        roomPriceAddService.delete(roomPriceAddList);
    }

    @RequestMapping(value = "roomPriceAddUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void roomPriceAddUpdate(@RequestBody List<RoomPriceAdd> roomPriceAddList) throws Exception {
        if (roomPriceAddList.size() > 1) {
            if (roomPriceAddList.get(0).getId().equals(roomPriceAddList.get(roomPriceAddList.size() / 2).getId())) {
                roomPriceAddService.update(roomPriceAddList.subList(0, roomPriceAddList.size() / 2));
                return;
            }
        }
        roomPriceAddService.update(roomPriceAddList);
    }
}