package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.BreakfastDetail;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.service.BreakfastDetailService;
import com.sygdsoft.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BreakfastDetailController {
    @Autowired
    BreakfastDetailService breakfastDetailService;
    @Autowired
    CheckInService checkInService;

    @RequestMapping(value = "breakfastDetailAdd")
    public void breakfastDetailAdd(@RequestBody BreakfastDetail breakfastDetail) throws Exception {
        breakfastDetailService.add(breakfastDetail);
    }

    @RequestMapping(value = "breakfastDetailDelete")
    @Transactional
    public void breakfastDetailDelete(@RequestBody List<BreakfastDetail> breakfastDetailList) throws Exception {
        breakfastDetailService.delete(breakfastDetailList);
    }

    @RequestMapping(value = "breakfastDetailUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void breakfastDetailUpdate(@RequestBody List<BreakfastDetail> breakfastDetailList) throws Exception {
        if (breakfastDetailList.size() > 1) {
            if (breakfastDetailList.get(0).getId().equals(breakfastDetailList.get(breakfastDetailList.size() / 2).getId())) {
                breakfastDetailService.update(breakfastDetailList.subList(0, breakfastDetailList.size() / 2));
                return;
            }
        }
        breakfastDetailService.update(breakfastDetailList);
    }

    @RequestMapping(value = "breakfastDetailGet")
    public List<BreakfastDetail> breakfastDetailGet(@RequestBody Query query) throws Exception {
        return breakfastDetailService.get(query);
    }

    /**
     * 录入早餐
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "breakfastDetailIn")
    public boolean breakfastDetailGetCount(@RequestBody JSONObject jsonObject){
        String roomId=jsonObject.getString("roomId");
        CheckIn checkIn=checkInService.getByRoomId(roomId);
        int remain= breakfastDetailService.breakfastDetailGetCount(checkIn.getSelfAccount());
        //if(checkIn.getNotNullConsume())
        return true;
    }
}
