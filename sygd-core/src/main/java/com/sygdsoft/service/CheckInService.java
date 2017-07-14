package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInMapper;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInHistoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "checkIn")
public class CheckInService extends BaseService<CheckIn> {
    @Autowired
    CheckInMapper checkInMapper;

    /**
     * 获得该房间剩余没有结算的消费
     *
     * @return
     */
    public Double getNeedPay(CheckIn checkIn) {
        return checkIn.getNotNullConsume() - checkIn.getNotNullPay();
    }

    /**
     * 获得该房间的公付帐号
     */
    public String getGroupAccount(String roomId) {
        CheckIn checkIn = getByRoomId(roomId);
        return checkIn.getGroupAccount();
    }

    /**
     * 获得该房间的自付帐号
     */
    public String getSelfAccount(String roomId) {
        CheckIn checkIn = getByRoomId(roomId);
        return checkIn.getSelfAccount();
    }

    /**
     * 获得房间对象
     */
    public CheckIn getByRoomId(String roomId) {
        CheckIn checkInQuery = new CheckIn();
        checkInQuery.setRoomId(roomId);
        return checkInMapper.selectOne(checkInQuery);
    }

    /**
     * 获得房间对象
     */
    public CheckIn getBySelfAccount(String selfAccount) {
        CheckIn checkInQuery = new CheckIn();
        checkInQuery.setSelfAccount(selfAccount);
        return checkInMapper.selectOne(checkInQuery);
    }

    /**
     * 获得所有房间的字符串
     */
    public String getTotalRoomString(List<CheckIn> checkInList, Boolean withComma) {
        String out = "";
        for (CheckIn checkIn : checkInList) {
            if (withComma) {
                out += "\'" + checkIn.getRoomId() + "\',";
            } else {
                out += checkIn.getRoomId() + ",";
            }
        }
        return out.substring(0, out.length() - 1);
    }

}
