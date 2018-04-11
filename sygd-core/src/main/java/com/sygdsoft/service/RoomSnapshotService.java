package com.sygdsoft.service;

import com.sygdsoft.mapper.RoomSnapshotMapper;
import com.sygdsoft.model.RoomSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
