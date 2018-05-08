package com.sygdsoft.service;

import com.sygdsoft.mapper.HuaYuanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HuaYuanService {
    @Autowired
    HuaYuanMapper huaYuanMapper;

    public Double getGuestSourceConsume(Date beginTime, Date endTime, String countCategory) {
        return huaYuanMapper.getGuestSourceConsume(beginTime, endTime, countCategory);
    }

    public Double getNotRoomSaleCount(Date beginTime, Date endTime) {
        return huaYuanMapper.getNotRoomSaleCount(beginTime, endTime);
    }

    public Double getNotRoomSaleConsume(Date beginTime, Date endTime) {
        return huaYuanMapper.getNotRoomSaleConsume(beginTime, endTime);
    }

    public Double getRoomShopConsume(Date beginTime, Date endTime, String pointOfSaleShop) {
        return huaYuanMapper.getRoomShopConsume(beginTime, endTime, pointOfSaleShop);
    }

    /**
     * 下边都是餐饮的
     *
     * @param beginTime
     * @param endTime
     * @param guestSource
     * @return
     */

    public Double getEatGuestSourceConsume(Date beginTime, Date endTime, String guestSource) {
        return huaYuanMapper.getEatGuestSourceConsume(beginTime, endTime, guestSource);
    }

    public Double getMenuGuestSourceConsume(Date beginTime, Date endTime, String guestSource,String foodSign) {
        return huaYuanMapper.getMenuGuestSourceConsume(beginTime, endTime, guestSource,foodSign);
    }

    public Integer getDeskNum(Date beginTime, Date endTime, String guestSource) {
        return huaYuanMapper.getDeskNum(beginTime, endTime, guestSource);
    }

    public Integer getSubDeskNum(Date beginTime, Date endTime, String guestSource) {
        return huaYuanMapper.getSubDeskNum(beginTime, endTime, guestSource);
    }

    public Integer getGroupDeskNum(Date beginTime, Date endTime, String guestSource) {
        return huaYuanMapper.getGroupDeskNum(beginTime, endTime, guestSource);
    }

}
