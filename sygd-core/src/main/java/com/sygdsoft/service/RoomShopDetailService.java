package com.sygdsoft.service;

import com.sygdsoft.mapper.RoomShopDetailMapper;
import com.sygdsoft.model.RoomShopDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-21.
 */
@Service
@SzMapper(id = "roomShopDetail")
public class RoomShopDetailService extends BaseService<RoomShopDetail>{
    @Autowired
    RoomShopDetailMapper roomShopDetailMapper;
    /**
     * 链接结账时间表查询时间段内的房吧明细
     */
    public List<RoomShopDetail> getRoomShopByDoneTime(Date beginTime, Date endTime){
        return roomShopDetailMapper.getRoomShopByDoneTime(beginTime, endTime);
    }

    /**
     * 链接结账时间表查询时间段内的零售明细
     */
    public List<RoomShopDetail> getRetailByDoneTime(Date beginTime,Date endTime){
        return roomShopDetailMapper.getRetailByDoneTime(beginTime, endTime);
    }

    /**
     * 链接结账时间表查询时间段内的房吧明细(聚合)
     */
    public List<RoomShopDetail> getSumRoomShopByDoneTimeUser(String userId,Date beginTime, Date endTime){
        if(userId==null){
            return roomShopDetailMapper.getSumRoomShopByDoneTime(beginTime, endTime);
        }else {
            return roomShopDetailMapper.getSumRoomShopByDoneTimeUser(userId, beginTime, endTime);
        }
    }
    /**
     * 链接结账时间表查询时间段内的零售明细(聚合)
     */
    public List<RoomShopDetail> getRetailByDoneTimeUser(String userId,Date beginTime,Date endTime){
        if(userId==null){
            return roomShopDetailMapper.getSumRetailByDoneTime(beginTime, endTime);
        }else {
            return roomShopDetailMapper.getSumRetailByDoneTimeUser(userId, beginTime, endTime);
        }
    }

    /**
     * 查询所有没有统计出库的商品
     */
    public List<RoomShopDetail> getByStorageDone(){
        return roomShopDetailMapper.getByStorageDone();
    }

    /**
     * 自动出库后把所有品种设置为已出库
     */
    public void setStorageDoneTrue(){
        roomShopDetailMapper.setStorageDoneTrue();
    }
}
