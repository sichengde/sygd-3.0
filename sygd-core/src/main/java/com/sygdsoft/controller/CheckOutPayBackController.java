package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckOut;
import com.sygdsoft.model.CheckOutPayBack;
import com.sygdsoft.model.DebtPay;
import com.sygdsoft.service.CheckOutPayBackService;
import com.sygdsoft.service.CheckOutService;
import com.sygdsoft.service.DebtPayService;
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
    @Autowired
    DebtPayService debtPayService;

    @RequestMapping("CheckOutPayBackGetWithRoomId")
    public List<CheckOutPayBack> CheckOutPayBackGetWithRoomId(@RequestBody Query query) throws Exception {
        List<CheckOutPayBack> checkOutPayBacks=checkOutPayBackService.get(query);
        for (CheckOutPayBack checkOutPayBack : checkOutPayBacks) {
            CheckOut checkOut=checkOutService.getByCheckOutSerial(checkOutPayBack.getCheckOutSerial());
            List<DebtPay> debtPayList=debtPayService.getListByCheckOutSerial(checkOutPayBack.getCheckOutSerial());
            if(debtPayList!=null&&debtPayList.size()>0){
                checkOutPayBack.setGuestName(debtPayList.get(0).getGuestName());
            }
            if(checkOut!=null){
                checkOutPayBack.setRoomId(checkOut.getRoomId());
            }
        }
        return checkOutPayBacks;
    }
}
