package com.sygdsoft.service;

import com.sygdsoft.mapper.RoomSnapshotMapper;
import com.sygdsoft.model.RoomSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@SzMapper(id = "roomSnapshot")
public class RoomSnapshotService extends BaseService<RoomSnapshot>{
    @Autowired
    RoomSnapshotMapper roomSnapshotMapper;
    /**
     * 通过日期删除该天的数据（夜审前）
     */
    public void deleteByDate(Date date){
        roomSnapshotMapper.deleteByDate(date);
    }

    /**
     * 获取已结账的房间
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<RoomSnapshot> getPaidRoom(Date beginTime, Date endTime) {
        return roomSnapshotMapper.getPaidRoom(beginTime,endTime );
    }

    public RoomSnapshot getSumByDate(Date beginTime, Date endTime,String state) {
        return roomSnapshotMapper.getSum(beginTime, endTime,state);
    }
}
