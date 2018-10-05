package com.sygdsoft.service;

import com.sygdsoft.mapper.CleanRoomMapper;
import com.sygdsoft.model.CleanRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-29.
 */
@Service
@SzMapper(id = "cleanRoom")
public class CleanRoomService extends BaseService<CleanRoom> {

    public static String leaveRoom = "走客房";
    public static String inRoom = "在住房";

    @Autowired
    CleanRoomMapper cleanRoomMapper;

    /**
     * 统计各个房扫工作情况
     */
    public List<CleanRoom> getSumNumByDate(Date beginTime, Date endTime) {
        return cleanRoomMapper.getSumNumByDate(beginTime, endTime);
    }


    public List<CleanRoom> cleanRoomGetWithCategory(String userId) {
        return cleanRoomMapper.cleanRoomGetWithCategory(userId);
    }
}
