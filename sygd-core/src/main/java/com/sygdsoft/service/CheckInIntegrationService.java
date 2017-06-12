package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInIntegrationMapper;
import com.sygdsoft.model.CheckInIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2017-01-05.
 */
@Service
@SzMapper(id = "checkInIntegration")
public class CheckInIntegrationService extends BaseService<CheckInIntegration>{
    @Autowired
    CheckInIntegrationMapper checkInIntegrationMapper;

    /**
     * 获得房间对象
     */
    public CheckInIntegration getBySelfAccount(String selfAccount) {
        CheckInIntegration checkInIntegrationQuery = new CheckInIntegration();
        checkInIntegrationQuery.setSelfAccount(selfAccount);
        return checkInIntegrationMapper.selectOne(checkInIntegrationQuery);
    }

    /**
     * 统计开房数（来店数）
     */
    public Integer getSumCount(Date beginTime,Date endTime,String guestSource,String roomCategory){
        return checkInIntegrationMapper.getSumCount(beginTime, endTime, guestSource,roomCategory);
    }

    /**
     * 统计消费（来店数）
     */
    public Double getSumConsume(Date beginTime,Date endTime,String guestSource,String roomCategory){
        return checkInIntegrationMapper.getSumConsume(beginTime, endTime, guestSource,roomCategory);
    }

    /**
     * 根据房类和客源获取入住天数
     */
    public Integer getTotalLiveDay(Date beginTime, Date endTime, String guestSource, String roomCategory){
        return checkInIntegrationMapper.getTotalLiveDay(beginTime, endTime, guestSource, roomCategory);
    }

    /**
     * 根据房类和客源获取一段时间内的平均房价
     */
    public Double getAvaRoomPrice(Date beginTime, Date endTime, String guestSource, String roomCategory){
        return checkInIntegrationMapper.getAvaRoomPrice(beginTime, endTime, guestSource, roomCategory);
    }
}
