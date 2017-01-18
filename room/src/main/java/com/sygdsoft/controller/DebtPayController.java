package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.CurrencyPost;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.jsonModel.QuerySubReport;
import com.sygdsoft.model.DebtPay;
import com.sygdsoft.model.LostRoom;
import com.sygdsoft.model.SaunaIn;
import com.sygdsoft.service.CheckInHistoryLogService;
import com.sygdsoft.service.DebtPayService;
import com.sygdsoft.service.SerialService;
import com.sygdsoft.service.UserLogService;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
@RestController
public class DebtPayController {
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    Util util;
    @Autowired
    SerialService serialService;

    @RequestMapping(value = "debtPayGet")
    public List<DebtPay> debtPayGet(@RequestBody Query query) throws Exception {
        return debtPayService.get(query);
    }

    /**
     * 根据时间段和币种查询
     */
    @RequestMapping(value = "debtPayGetByDateCurrency")
    public List<DebtPay> debtPayGetByDateCurrency(@RequestBody QuerySubReport querySubReport) {
        String currency = querySubReport.getCurrency();
        Date beginTime = querySubReport.getBeginTime();
        Date endTime = querySubReport.getEndTime();
        return debtPayService.getByCurrencyDate(currency, beginTime, endTime);
    }

    /**
     * 根据时间段，币种和操作员查询
     */
    @RequestMapping(value = "debtPayGetByDateCurrencyUserId")
    public List<DebtPay> debtPayGetByDateCurrencyUserId(@RequestBody QuerySubReport querySubReport) {
        String currency = querySubReport.getCurrency();
        Date beginTime = querySubReport.getBeginTime();
        Date endTime = querySubReport.getEndTime();
        String userId = querySubReport.getUserId();
        if ("".equals(userId)) {
            return debtPayService.getByCurrencyDate(currency, beginTime, endTime);
        } else {
            return debtPayService.getByCurrencyDateUser(userId, currency, beginTime, endTime);
        }
    }

    @RequestMapping(value = "lostRoomCheckOut")
    public void lostRoomCheckOut(@RequestBody LostRoom lostRoom) throws Exception {
        serialService.setPaySerial();
        List<CurrencyPost> currencyPostList = lostRoom.getCurrencyPostList();
        DebtPay debtPay = lostRoom.getDebtPay();
        /*先把原先的删了*/
        debtPayService.delete(debtPay);
        List<String> roomIdList = checkInHistoryLogService.getRoomIdListByCheckInHistoryLogList(checkInHistoryLogService.getByCheckOutSerial(debtPay.getCheckOutSerial()));
        for (CurrencyPost currencyPost : currencyPostList) {
            String currency = currencyPost.getCurrency();
            String currencyAdd = currencyPost.getCurrencyAdd();
            Double money = currencyPost.getMoney();
            DebtPay debtPayInsert = new DebtPay(debtPay);
            debtPayInsert.setCurrency(currency);
            debtPayInsert.setDebtMoney(money);
            debtPayService.add(debtPayInsert);
            debtPayService.parseCurrency(currency, currencyAdd, money, roomIdList, debtPay.getGroupAccount(), "哑房结算", serialService.getPaySerial(), "接待");
        }
        String roomString = util.listToString(roomIdList);
        userLogService.addUserLog("哑房结算:" + roomString, userLogService.reception, userLogService.lostRoomCheckOut, roomString);
    }
}
