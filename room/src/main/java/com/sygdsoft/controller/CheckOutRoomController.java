package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckOutRoom;
import com.sygdsoft.service.CheckOutRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-06.
 */
@RestController
public class CheckOutRoomController {
    @Autowired
    CheckOutRoomService checkOutRoomService;

    @RequestMapping(value = "checkOutRoomAdd")
    public void checkOutRoomAdd(@RequestBody CheckOutRoom checkOutRoom) throws Exception {
        checkOutRoomService.add(checkOutRoom);
    }

    @RequestMapping(value = "checkOutRoomDelete")
    @Transactional(rollbackFor = Exception.class)
    public void checkOutRoomDelete(@RequestBody List<CheckOutRoom> checkOutRoomList) throws Exception {
        checkOutRoomService.delete(checkOutRoomList);
    }

    @RequestMapping(value = "checkOutRoomUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void checkOutRoomUpdate(@RequestBody List<CheckOutRoom> checkOutRoomList) throws Exception {
        if (checkOutRoomList.size() > 1) {
            if (checkOutRoomList.get(0).getId().equals(checkOutRoomList.get(checkOutRoomList.size() / 2).getId())) {
                checkOutRoomService.update(checkOutRoomList.subList(0, checkOutRoomList.size() / 2));
                return;
            }
        }
        checkOutRoomService.update(checkOutRoomList);
    }

    @RequestMapping(value = "checkOutRoomGet")
    public List<CheckOutRoom> checkOutRoomGet(@RequestBody Query query) throws Exception {
        return checkOutRoomService.get(query);
    }
}
