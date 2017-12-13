package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckInGuest;
import com.sygdsoft.model.GuestMapCheckIn;
import com.sygdsoft.service.CheckInGuestService;
import com.sygdsoft.service.GuestMapCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
@RestController
public class CheckInGuestController {
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    GuestMapCheckInService guestMapCheckInService;

    @RequestMapping(value = "checkInGuestAdd")
    @Transactional(rollbackFor = Exception.class)
    public void checkInGuestAdd(@RequestBody CheckInGuest checkInGuest) throws Exception {
        checkInGuestService.add(checkInGuest);
        /*如果之前有临时客人，并且增加了真实客人，则删除临时客人*/
        checkInGuestService.deleteTempGuestByRoomId(checkInGuest.getRoomId());
        /*需要同时维护guestMapCheckIn表*/
        GuestMapCheckIn guestMapCheckIn=new GuestMapCheckIn();
        guestMapCheckIn.setCardId(checkInGuest.getCardId());
        guestMapCheckIn.setSelfAccount(checkInGuest.getSelfAccount());
        guestMapCheckInService.add(guestMapCheckIn);
    }

    @RequestMapping(value = "checkInGuestDelete")
    @Transactional(rollbackFor = Exception.class)
    public void checkInGuestDelete(@RequestBody List<CheckInGuest> checkInGuestList) throws Exception {
        checkInGuestService.delete(checkInGuestList);
        /*需要同时维护guestMapCheckIn表*/
        for (CheckInGuest checkInGuest : checkInGuestList) {
            guestMapCheckInService.deleteByCardId(checkInGuest.getCardId());
        }
    }

    @RequestMapping(value = "checkInGuestUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void checkInGuestUpdate(@RequestBody List<CheckInGuest> checkInGuestList) throws Exception {
        if (checkInGuestList.size() > 1) {
            if (checkInGuestList.get(0).getId().equals(checkInGuestList.get(checkInGuestList.size() / 2).getId())) {
                checkInGuestService.update(checkInGuestList.subList(0, checkInGuestList.size() / 2));
                return;
            }
        }
        checkInGuestService.update(checkInGuestList);
        /*需要同时维护guestMapCheckIn表*/
        for (CheckInGuest checkInGuest : checkInGuestList) {

        }
    }

    @RequestMapping(value = "checkInGuestGet")
    public List<CheckInGuest> checkInGuestGet(@RequestBody Query query) throws Exception {
        return checkInGuestService.get(query);
    }

}
