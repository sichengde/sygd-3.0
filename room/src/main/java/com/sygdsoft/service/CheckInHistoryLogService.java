package com.sygdsoft.service;

import com.sygdsoft.jsonModel.GuestParseRow;
import com.sygdsoft.mapper.CheckInHistoryLogMapper;
import com.sygdsoft.model.CheckInHistoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
     * 通过房号和离店结算序列号获得一条记录
     */
    public CheckInHistoryLog getByRoomIDAndCheckOutSerial(String roomId,String checkOutSerial){
        CheckInHistoryLog checkInHistoryLog=new CheckInHistoryLog();
        checkInHistoryLog.setRoomId(roomId);
        checkInHistoryLog.setCheckOutSerial(checkOutSerial);
        return checkInHistoryLogMapper.selectOne(checkInHistoryLog);
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
    /**
     * 获得时间段内客源消费情况
     */
    public List<GuestParseRow> guestSourceParse(Date beginTime, Date endTime){
        return checkInHistoryLogMapper.guestSourceParse(beginTime,endTime);
    }
}
