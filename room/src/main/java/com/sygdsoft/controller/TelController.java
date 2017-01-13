package com.sygdsoft.controller;

import com.sygdsoft.model.Debt;
import com.sygdsoft.service.DebtService;
import com.sygdsoft.service.InterfaceDoorService;
import com.sygdsoft.service.PointOfSaleService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping(value = "telSend")
    public void telSend(String telId, String description, String consume) {
        String roomId=interfaceDoorService.getRoomIdByTelId(telId);
        timeService.setNow();
        Debt debt = new Debt();
        debt.setRoomId(roomId);
        debt.setDescription("电话费"+description);
        debt.setCurrency("挂账");
        debt.setPointOfSale(pointOfSaleService.FF);
        debt.setConsume(szMath.formatTwoDecimalReturnDouble(Double.valueOf(consume)));
        debt.setCategory(debtService.tel);
        debtService.addDebt(debt);
    }
}
