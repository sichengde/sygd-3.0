package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.HotelMessage;
import com.sygdsoft.service.HotelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
@RestController
public class HotelMessageController {
    @Autowired
    HotelMessageService hotelMessageService;

    @RequestMapping(value = "hotelMessageAdd")
    public void hotelMessageAdd(@RequestBody HotelMessage hotelMessage) throws Exception {
        hotelMessageService.add(hotelMessage);
    }

    @RequestMapping(value = "hotelMessageDelete")
    @Transactional(rollbackFor = Exception.class)
    public void hotelMessageDelete(@RequestBody List<HotelMessage> hotelMessageList) throws Exception {
        hotelMessageService.delete(hotelMessageList);
    }

    @RequestMapping(value = "hotelMessageUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void hotelMessageUpdate(@RequestBody List<HotelMessage> hotelMessageList) throws Exception {
        if (hotelMessageList.size() > 1) {
            if (hotelMessageList.get(0).getId().equals(hotelMessageList.get(hotelMessageList.size() / 2).getId())) {
                hotelMessageService.update(hotelMessageList.subList(0, hotelMessageList.size() / 2));
                return;
            }
        }
        hotelMessageService.update(hotelMessageList);
    }

    @RequestMapping(value = "hotelMessageGet")
    public List<HotelMessage> hotelMessageGet(@RequestBody Query query) throws Exception {
        return hotelMessageService.get(query);
    }
}
