package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInGuestMapper;
import com.sygdsoft.model.CheckInGuest;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "checkInGuest")
public class CheckInGuestService extends BaseService<CheckInGuest>{
    @Autowired
    CheckInGuestMapper checkInGuestMapper;
    @Autowired
    Util util;

    /**
     * 通过房号获得在店宾客数组
     */
    public List<CheckInGuest> getListByRoomId(String roomId){
        CheckInGuest checkInGroupQuery = new CheckInGuest();
        checkInGroupQuery.setRoomId(roomId);
        return checkInGuestMapper.select(checkInGroupQuery);
    }
    /**
     * 通过房号列表获得客人姓名字符串
     */
    public String getListByRoomId(List<String> roomIdList){
        String out="";
        for (String roomId : roomIdList) {
            List<CheckInGuest> checkInGuests=getListByRoomId(roomId);
            out+=listToStringName(checkInGuests)+",";
        }
        return out;
    }

    /**
     * 通过房号数组获得在店宾客数组
     */
    public List<CheckInGuest> getListByRoomIdList(List<String> roomIdList){
        return checkInGuestMapper.getListByRoomIdList(util.listToString(roomIdList));
    }
    /**
     * 通过床位号数组和房间号获得在店宾客数组
     */
    public List<CheckInGuest> getListByBedList(String roomId,List<String> bedList){
        return checkInGuestMapper.getListByBedList(roomId, util.listToString(bedList));
    }

    /**
     * 获得在店宾客字符串
     */
    public String listToStringName(List<CheckInGuest> checkInGuestList){
        String out="";
        for (CheckInGuest checkInGuest : checkInGuestList) {
            out+=checkInGuest.getName()+",";
        }
        return out.substring(0,out.length()-1);
    }

    /**
     * 删除该房间的临时客人
     */
    public void deleteTempGuestByRoomId(String roomId){
        CheckInGuest checkInGuestQuery=new CheckInGuest();
        checkInGuestQuery.setCardId("000");
        checkInGuestQuery.setRoomId(roomId);
        checkInGuestMapper.delete(checkInGuestQuery);
    }

}
