package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.RoomCategory;
import com.sygdsoft.model.RoomCategory;
import com.sygdsoft.service.RoomCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class RoomCategoryController {
    @Autowired
    RoomCategoryService roomCategoryService;

    @RequestMapping(value = "roomCategoryAdd")
    public void roomCategoryAdd(@RequestBody RoomCategory roomCategory) throws Exception {
        roomCategoryService.add(roomCategory);
    }

    @RequestMapping(value = "roomCategoryDelete")
    @Transactional(rollbackFor = Exception.class)
    public void roomCategoryDelete(@RequestBody List<RoomCategory> roomCategoryList) throws Exception {
        roomCategoryService.delete(roomCategoryList);
    }

    @RequestMapping(value = "roomCategoryGet")
    public List<RoomCategory> roomCategoryGetAll(@RequestBody Query query) throws Exception {
        return roomCategoryService.get(query);
    }

    @RequestMapping(value = "roomCategoryUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void roomCategoryUpdate(@RequestBody List<RoomCategory> roomCategoryList) throws Exception {
        if (roomCategoryList.size() > 1) {
            if (roomCategoryList.get(0).getId().equals(roomCategoryList.get(roomCategoryList.size() / 2).getId())) {
                roomCategoryService.update(roomCategoryList.subList(0, roomCategoryList.size() / 2));
                return;
            }
        }
        roomCategoryService.update(roomCategoryList);
    }
}
