package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckOutRoomMapper;
import com.sygdsoft.model.CheckOutRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "checkOutRoom")
public class CheckOutRoomService extends BaseService<CheckOutRoom> {
    @Autowired
    CheckOutRoomMapper checkOutRoomMapper;

    /**
     * 根据离店序列号获得房间信息
     */
    public List<CheckOutRoom> getByCheckOutSerial(String checkOutSerial) {
        CheckOutRoom checkOutRoomQuery=new CheckOutRoom();
        checkOutRoomQuery.setCheckOutSerial(checkOutSerial);
        return checkOutRoomMapper.select(checkOutRoomQuery);
    }

    /**
     * 精简离店房数组，只返回字符串
     */
    public List<String> simpleToString(List<CheckOutRoom> checkOutRoomList){
        List<String> stringList=new ArrayList<>();
        for (CheckOutRoom checkOutRoom : checkOutRoomList) {
            stringList.add(checkOutRoom.getRoomId());
        }
        return stringList;
    }
}
