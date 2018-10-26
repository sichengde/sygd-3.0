package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.model.Currency;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by 舒展 on 2016-03-21.
 * 在店户籍控制器
 */
@RestController
public class CheckInController {
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    ProtocolService protocolService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    DebtService debtService;
    @Autowired
    CurrencyService currencyService;

    /**
     * 获取全部在店户籍
     */
    @RequestMapping(value = "checkInGet")
    public List<CheckIn> checkInGet(@RequestBody Query query) throws Exception {
        List<CheckIn> checkInList = checkInService.get(query);
        HashMap<String, Boolean> currencyRealMap = new HashMap<>();//全部的币种和押金map
        List<Currency> currencyList = currencyService.get();
        for (Currency currency : currencyList) {
            currencyRealMap.put(currency.getCurrency(), currency.getNotNullCheckRemain());
        }
        for (CheckIn checkIn : checkInList) {
            checkIn.setNameString(checkInGuestService.listToStringName(checkInGuestService.getListByRoomId(checkIn.getRoomId())));
            /*获取所有押金*/
            Double realDeposit = 0.0;
            List<Debt> debtList = debtService.getListByRoomId(checkIn.getRoomId());
            for (Debt debt : debtList) {
                if (debt.getNotNullDeposit() != 0.0 && currencyRealMap.getOrDefault(debt.getCurrency(), false)) {
                    realDeposit += debt.getNotNullDeposit();
                }
            }
            checkIn.setPartInDeposit(realDeposit);
        }
        return checkInList;
    }

    /**
     * 获取在店团队信息
     */
    @RequestMapping(value = "checkInGroupGet")
    public List<CheckInGroup> checkInGroupGet(@RequestBody Query query) throws Exception {
        return checkInGroupService.get(query);
    }


    /**
     * 修改在店户籍，没有批量修改的可能
     */
    @RequestMapping(value = "checkInUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void checkInUpdate(@RequestBody CheckIn checkIn) throws Exception {
        /*先获取原来的checkIn便于对比改没改房价*/
        CheckIn checkIn1 = checkInService.get(new Query("id=" + checkIn.getId())).get(0);
        /*修改在店户籍*/
        /*将消费和押金设置为null*/
        checkIn.setConsume(null);
        checkIn.setDeposit(null);
        checkInService.updateSelective(checkIn);
        /*如果是可编辑房价的话，还要修改房价协议*/
        if (otherParamService.getValueByName("可编辑房价").equals("y") && Objects.equals(checkIn1.getFinalRoomPrice(), checkIn.getFinalRoomPrice())) {
            Protocol protocol = protocolService.getByNameTemp(checkIn.getProtocol());
            if (protocol != null) {
                protocol.setRoomPrice(checkIn.getFinalRoomPrice());
                protocolService.update(protocol);
            }
        }
        /*如果是团队主账房并且修改备注的话，要同步checkInGroup*/
        if (checkIn.getGroupAccount() != null) {
            CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(checkIn.getGroupAccount());
            if (checkInGroup.getLeaderRoom().equals(checkIn.getRoomId())) {
                CheckInGroup checkInGroupUpdate = new CheckInGroup();
                checkInGroupUpdate.setId(checkInGroup.getId());
                if (checkIn.getRemark() != null && !checkIn.getRemark().equals(checkIn1.getRemark())) {
                    checkInGroupUpdate.setRemark(checkIn.getRemark());
                }
                if (checkIn.getVipNumber() != null && !checkIn.getVipNumber().equals(checkIn1.getVipNumber())) {
                    checkInGroupUpdate.setVipNumber(checkIn.getVipNumber());
                }
                checkInGroupService.updateSelective(checkInGroupUpdate);
            }
        }
    }

    /**
     * 客账余额
     */
    @RequestMapping(value = "checkInRemainReport")
    public List<JSONObject> checkInRemainReport() throws Exception {
        List<CheckIn> checkInList = checkInService.get();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        Map<String, JSONObject> roomIdCheckInMap = new HashMap<>();
        HashMap<String, Boolean> currencyRealMap = new HashMap<>();//全部的币种和押金map
        List<Currency> currencyList = currencyService.get();
        for (Currency currency : currencyList) {
            currencyRealMap.put(currency.getCurrency(), currency.getNotNullCheckRemain());
        }
        for (CheckIn checkIn : checkInList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roomId", checkIn.getRoomId());
            jsonObject.put("consume", checkIn.getNotNullConsume());
            jsonObject.put("deposit", checkIn.getDeposit());
            /*获取所有押金*/
            Double realDeposit = 0.0;
            List<Debt> debtList = debtService.getListByRoomId(checkIn.getRoomId());
            for (Debt debt : debtList) {
                if (debt.getNotNullDeposit() != 0.0 && currencyRealMap.getOrDefault(debt.getCurrency(), false)) {
                    realDeposit += debt.getNotNullDeposit();
                }
            }
            jsonObject.put("partInDeposit", realDeposit);
            jsonObjectList.add(jsonObject);
            roomIdCheckInMap.put(checkIn.getRoomId(), jsonObject);
        }
        List<Debt> debtList = debtService.getListGroupByRoomIdPointOfSale();
        for (Debt debt : debtList) {
            JSONObject row = roomIdCheckInMap.get(debt.getRoomId());
            if (row != null) {
                row.put(debt.getPointOfSale(), debt.getConsume());
            }
        }
        return jsonObjectList;
    }
}
