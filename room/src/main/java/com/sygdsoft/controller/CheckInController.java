package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGroup;
import com.sygdsoft.model.Protocol;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 获取全部在店户籍
     */
    @RequestMapping(value = "checkInGet")
    public List<CheckIn> checkInGet(@RequestBody Query query) throws Exception {
        List<CheckIn> checkInList=checkInService.get(query);
        for (CheckIn checkIn : checkInList) {
            checkIn.setNameString(checkInGuestService.listToStringName(checkInGuestService.getListByRoomId(checkIn.getRoomId())));
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
        CheckIn checkIn1=checkInService.get(new Query("id="+checkIn.getId())).get(0);
        /*修改在店户籍*/
        /*将消费和押金设置为null*/
        checkIn.setConsume(null);
        checkIn.setDeposit(null);
        checkInService.updateSelective(checkIn);
        /*如果是可编辑房价的话，还要修改房价协议*/
        if(otherParamService.getValueByName("可编辑房价").equals("y") && checkIn1.getFinalRoomPrice()==checkIn.getFinalRoomPrice()) {
            Protocol protocol = protocolService.getByNameTemp(checkIn.getProtocol());
            protocol.setRoomPrice(checkIn.getFinalRoomPrice());
            protocolService.update(protocol);
        }
    }
}
