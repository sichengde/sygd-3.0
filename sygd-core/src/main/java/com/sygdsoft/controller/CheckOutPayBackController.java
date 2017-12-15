package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckOut;
import com.sygdsoft.model.CheckOutPayBack;
import com.sygdsoft.service.CheckOutPayBackService;
import com.sygdsoft.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckOutPayBackController {
    @Autowired
    CheckOutPayBackService checkOutPayBackService;
    @Autowired
    CheckOutService checkOutService;

    @RequestMapping("CheckOutPayBackGetWithRoomId")
    public List<CheckOutPayBack> CheckOutPayBackGetWithRoomId(@RequestBody Query query) throws Exception {
        List<CheckOutPayBack> checkOutPayBacks=checkOutPayBackService.get(query);
        for (CheckOutPayBack checkOutPayBack : checkOutPayBacks) {
            CheckOut checkOut=checkOutService.getByCheckOutSerial(checkOutPayBack.getCheckOutSerial());
            if(checkOut!=null){
                checkOutPayBack.setRoomId(checkOut.getRoomId());
            }
        }
        return checkOutPayBacks;
    }
}
