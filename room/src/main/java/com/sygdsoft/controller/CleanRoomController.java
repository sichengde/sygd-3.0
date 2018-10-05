package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CleanRoom;
import com.sygdsoft.service.CleanRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-29.
 */
@RestController
public class CleanRoomController {
    @Autowired
    CleanRoomService cleanRoomService;

    @RequestMapping(value = "cleanRoomGet")
    public List<CleanRoom> cleanRoomGet(@RequestBody Query query) throws Exception {
        return cleanRoomService.get(query);
    }

    @RequestMapping(value = "cleanRoomGetWithCategory")
    public List<CleanRoom> cleanRoomGetWithCategory(@RequestBody JSONObject jsonObject) throws Exception {
        String userId=jsonObject.getString("userId");
        return cleanRoomService.cleanRoomGetWithCategory(userId);
    }

}
