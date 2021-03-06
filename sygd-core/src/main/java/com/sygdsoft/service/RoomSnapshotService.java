package com.sygdsoft.service;

import com.sygdsoft.mapper.RoomSnapshotMapper;
import com.sygdsoft.model.RoomSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@SzMapper(id = "roomSnapshot")
public class RoomSnapshotService extends BaseService<RoomSnapshot> {
    @Autowired
    RoomSnapshotMapper roomSnapshotMapper;

    /**
     * 获取数组
     */
    public List<RoomSnapshot> getSumList(Date beginTime,Date endTime,String field){
        return roomSnapshotMapper.getSumList(beginTime,endTime,field);
    }

    /**
     * 通过日期删除该天的数据（夜审前）
     */
    public void deleteByDate(Date date) {
        roomSnapshotMapper.deleteByDate(date);
    }

    /**
     * 获取已结账的房间
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<RoomSnapshot> getPaidRoom(Date beginTime, Date endTime) {
        return roomSnapshotMapper.getPaidRoom(beginTime, endTime);
    }

    /**
     * 获取总数
     * @param beginTime
     * @param endTime
     * @return
     */
    public Integer getCount(Date beginTime, Date endTime,String state,Boolean realRoom) {
        return roomSnapshotMapper.getCount(beginTime, endTime,state,realRoom);
    }

    public RoomSnapshot getSumByDate(Date beginTime, Date endTime, String state) {
        return roomSnapshotMapper.getSum(beginTime, endTime, state);
    }

    public Double getSumByGuestSource(Date beginTime, Date endTime, String guestSource) {
        return roomSnapshotMapper.getNumByGuestSource(beginTime, endTime, guestSource);
    }

    public Double getConsumeByGuestSource(Date beginTime, Date endTime, String guestSource) {
        return roomSnapshotMapper.getConsumeByGuestSource(beginTime, endTime, guestSource);
    }

    public int getNotRoomCount(Date beginTime, Date endTime) {
        return roomSnapshotMapper.getNotRoomCount(beginTime, endTime);
    }

    public int getNotRoomAvailableCount(Date beginTime, Date endTime) {
        return roomSnapshotMapper.getNotRoomAvailableCount(beginTime, endTime);
    }

    public List<RoomSnapshot> getListGroupByCategory(Date beginTime, Date endTime) {
        return roomSnapshotMapper.getListGroupByCategory(beginTime, endTime);
    }

}
