package com.sygdsoft.service;

import com.sygdsoft.mapper.GuestMapCheckInMapper;
import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.model.GuestMapCheckIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-09.
 */
@Service
@SzMapper(id = "guestMapCheckIn")
public class GuestMapCheckInService extends BaseService<GuestMapCheckIn>{
    @Autowired
    GuestMapCheckInMapper guestMapCheckInMapper;
    /**
     * 通过自付账号获得列表
     */
    public List<GuestMapCheckIn> getBySelfAccount(String selfAccount){
        GuestMapCheckIn guestMapCheckIn=new GuestMapCheckIn();
        guestMapCheckIn.setSelfAccount(selfAccount);
        return guestMapCheckInMapper.select(guestMapCheckIn);
    }
    /**
     * 通过身份证号获得列表
     */
    public List<GuestMapCheckIn> getByCardId(String cardId){
        GuestMapCheckIn guestMapCheckIn=new GuestMapCheckIn();
        guestMapCheckIn.setCardId(cardId);
        return guestMapCheckInMapper.select(guestMapCheckIn);
    }

    /**
     * 通过身份证号和房类获取上次开房房价
     */
    public List<CheckInHistoryLog> getHistoryRoomPriceByCardId(String cardId , String roomCategory){
        return guestMapCheckInMapper.getHistoryRoomPriceByCardId(cardId, roomCategory);
    }

    /**
     * 通过证件号删除
     * @param cardId
     */
    public void deleteByCardId(String cardId){
        guestMapCheckInMapper.deleteByCardId(cardId);
    }
}
