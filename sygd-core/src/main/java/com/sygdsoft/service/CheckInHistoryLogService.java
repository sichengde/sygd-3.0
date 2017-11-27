package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInHistoryLogMapper;
import com.sygdsoft.model.CheckInHistoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
@Service
@SzMapper(id = "checkInHistoryLog")
public class CheckInHistoryLogService extends BaseService<CheckInHistoryLog>{
    @Autowired
    CheckInHistoryLogMapper checkInHistoryLogMapper;

    /**
     * 通过房号和离店结算序列号获得一条记录（换房的话容易报错）
     */
    public CheckInHistoryLog getByRoomIDAndCheckOutSerial(String roomId,String checkOutSerial){
        CheckInHistoryLog checkInHistoryLog=new CheckInHistoryLog();
        checkInHistoryLog.setRoomId(roomId);
        checkInHistoryLog.setCheckOutSerial(checkOutSerial);
        List<CheckInHistoryLog> checkInHistoryLogList=checkInHistoryLogMapper.select(checkInHistoryLog);
        try {
            return checkInHistoryLogList.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过自付账号和离店结算序列号获得一条记录
     */
    public CheckInHistoryLog getBySelfAccountAndCheckOutSerial(String selfAccount,String checkOutSerial){
        CheckInHistoryLog checkInHistoryLog=new CheckInHistoryLog();
        checkInHistoryLog.setSelfAccount(selfAccount);
        checkInHistoryLog.setCheckOutSerial(checkOutSerial);
        List<CheckInHistoryLog> checkInHistoryLogList=checkInHistoryLogMapper.select(checkInHistoryLog);
        try {
            return checkInHistoryLogList.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 通过公付账号获得队列
     */
    public List<CheckInHistoryLog> getByGroupAccount(String groupAccount){
        CheckInHistoryLog checkInHistoryLog=new CheckInHistoryLog();
        checkInHistoryLog.setGroupAccount(groupAccount);
        return checkInHistoryLogMapper.select(checkInHistoryLog);
    }

    /**
     * 通过离店序列号获取全部房号
     */
    public List<CheckInHistoryLog> getByCheckOutSerial(String checkOutSerial){
        CheckInHistoryLog checkInHistoryLog=new CheckInHistoryLog();
        checkInHistoryLog.setCheckOutSerial(checkOutSerial);
        return checkInHistoryLogMapper.select(checkInHistoryLog);
    }

    public List<String> getRoomIdListByCheckInHistoryLogList(List<CheckInHistoryLog> checkInHistoryLogList){
        List<String> roomIdList=new ArrayList<>();
        for (CheckInHistoryLog checkInHistoryLog : checkInHistoryLogList) {
            roomIdList.add(checkInHistoryLog.getRoomId());
        }
        return roomIdList;
    }
}
