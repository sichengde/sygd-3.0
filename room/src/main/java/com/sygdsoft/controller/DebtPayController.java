package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.CurrencyPost;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.jsonModel.QuerySubReport;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    @Autowired
    DebtService debtService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;

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
        return debtPayService.getList(null, currency, beginTime, endTime, null);
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
            userId = null;
        }
        return debtPayService.getList(userId, currency, beginTime, endTime, null);
    }

    @RequestMapping(value = "lostRoomCheckOut")
    @Transactional(rollbackFor = Exception.class)
    public void lostRoomCheckOut(@RequestBody LostRoom lostRoom) throws Exception {
        serialService.setPaySerial();
        timeService.setNow();
        List<CurrencyPost> currencyPostList = lostRoom.getCurrencyPostList();
        DebtPay debtPay = lostRoom.getDebtPay();
        /*挂账记录转移到账务历史*/
        List<DebtHistory> debtHistoryList = new ArrayList<>();
        List<Debt> debtList = debtService.get(new Query("from_room=\'" + debtPay.getPaySerial() + "\'"));
        for (Debt debt : debtList) {
            debt.setPaySerial(serialService.getPaySerial());
            DebtHistory debtHistory = new DebtHistory(debt);
            debtHistory.setDoneTime(timeService.getNow());
            debtHistoryList.add(debtHistory);
        }
        debtService.delete(debtList);
        debtHistoryService.add(debtHistoryList);
        /*改成标志位*/
        debtPay.setLostDone(true);
        debtPayService.update(debtPay);
        List<String> roomIdList = checkInHistoryLogService.getRoomIdListByCheckInHistoryLogList(checkInHistoryLogService.getByCheckOutSerial(debtPay.getCheckOutSerial()));
        for (CurrencyPost currencyPost : currencyPostList) {
            String currency = currencyPost.getCurrency();
            String currencyAdd = currencyPost.getCurrencyAdd();
            Double money = currencyPost.getMoney();
            DebtPay debtPayInsert = new DebtPay(debtPay);
            debtPayInsert.setCurrency(currency);
            debtPayInsert.setDebtMoney(money);
            debtPayInsert.setDoneTime(timeService.getNow());
            debtPayInsert.setPaySerial(serialService.getPaySerial());
            debtPayInsert.setCheckOutSerial(null);
            debtPayInsert.setSelfAccount(null);
            debtPayInsert.setGroupAccount(null);
            debtPayInsert.setUserId(userService.getCurrentUser());
            debtPayInsert.setDebtCategory("哑房结算");
            debtPayService.add(debtPayInsert);
            debtPayService.parseCurrency(currency, currencyAdd, money, roomIdList, debtPay.getGroupAccount(), "哑房结算", serialService.getPaySerial(), "接待", "接待");
        }
        String roomString = util.listToString(roomIdList);
        userLogService.addUserLog("哑房结算:" + roomString, userLogService.reception, userLogService.lostRoomCheckOut, roomString);
    }
}
