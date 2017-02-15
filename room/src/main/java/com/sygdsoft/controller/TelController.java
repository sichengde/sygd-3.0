package com.sygdsoft.controller;

import com.sygdsoft.model.Debt;
import com.sygdsoft.model.TelDetail;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 舒展 on 2017-01-12.
 * 电话计费控制器
 */
@Controller
public class TelController {
    @Autowired
    InterfaceDoorService interfaceDoorService;
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    SzMath szMath;
    @Autowired
    DebtService debtService;
    @Autowired
    TelDetailService telDetailService;

    private String last;

    @RequestMapping(value = "telSend", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public void telSend(String telId, String duration, String consume, String targetNumber, String price, String id) throws Exception {
        if ((telId + duration + consume + targetNumber + price + id).equals(last)) {
            return;
        }
        last = telId + duration + consume + targetNumber + price + id;
        String roomId = interfaceDoorService.getRoomIdByTelId(telId);
        if (!roomId.equals("")) {
            timeService.setNow();
            Debt debt = new Debt();
            debt.setRoomId(roomId);
            debt.setDescription("电话费" + duration);
            debt.setCurrency("挂账");
            debt.setPointOfSale(pointOfSaleService.DH);
            debt.setConsume(szMath.formatTwoDecimalReturnDouble(Double.valueOf(consume)));
            debt.setCategory(debtService.tel);
            debtService.addDebt(debt);
        }
        TelDetail telDetail = new TelDetail();
        telDetail.setTelId(telId);
        telDetail.setTelTime(timeService.getNow());
        telDetail.setDuration(Integer.valueOf(duration));
        telDetail.setPrice(Double.valueOf(price));
        telDetail.setTargetNumber(targetNumber);
        telDetail.setTotalPrice(szMath.formatTwoDecimalReturnDouble(Double.valueOf(consume)));
        telDetailService.add(telDetail);
    }
}
