package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CleanRoomMan;
import com.sygdsoft.service.CleanRoomManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
@RestController
public class CleanRoomManController {
    @Autowired
    CleanRoomManService cleanRoomManService;

    @RequestMapping(value = "cleanRoomManAdd")
    public void cleanRoomManAdd(@RequestBody CleanRoomMan cleanRoomMan) throws Exception {
        cleanRoomManService.add(cleanRoomMan);
    }

    @RequestMapping(value = "cleanRoomManDelete")
    @Transactional(rollbackFor = Exception.class)
    public void protocolDelete(@RequestBody List<CleanRoomMan> cleanRoomManList) throws Exception {
        cleanRoomManService.delete(cleanRoomManList);
    }

    @RequestMapping(value = "cleanRoomManUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void protocolUpdate(@RequestBody List<CleanRoomMan> cleanRoomManList) throws Exception {
        if (cleanRoomManList.size() > 1) {
            if (cleanRoomManList.get(0).getId().equals(cleanRoomManList.get(cleanRoomManList.size() / 2).getId())) {
                cleanRoomManService.update(cleanRoomManList.subList(0, cleanRoomManList.size() / 2));
                return;
            }
        }
        cleanRoomManService.update(cleanRoomManList);
    }

    @RequestMapping(value = "cleanRoomManGet")
    public List<CleanRoomMan> protocolGet(@RequestBody Query query) throws Exception {
        return cleanRoomManService.get(query);
    }

    @RequestMapping(value = "arrangeCleanRoomMan")
    public void arrangeCleanRoomMan(@RequestBody JSONObject jsonObject){
        cleanRoomManService.arrangeCleanRoomMan(jsonObject);
    }

    @RequestMapping(value = "confirmCheckOutRoom")
    public void confirmCheckOutRoom(@RequestBody JSONObject jsonObject){
        cleanRoomManService.confirmCheckOutRoom(jsonObject);
    }

}
