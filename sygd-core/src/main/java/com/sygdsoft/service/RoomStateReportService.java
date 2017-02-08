package com.sygdsoft.service;

import com.sygdsoft.mapper.RoomStateReportMapper;
import com.sygdsoft.model.RoomStateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-02-07.
 */
@Service
@SzMapper(id = "roomStateReport")
public class RoomStateReportService extends BaseService<RoomStateReport>{
    @Autowired
    RoomStateReportMapper roomStateReportMapper;

    /**
     * 通过日期删除该天的数据（夜审前）
     */
    public void deleteByDate(Date date){
        roomStateReportMapper.deleteByDate(date);
    }

    /**
     * 获得某段时期内的总和（不分房类）
     */
    public RoomStateReport getSumByDate(Date beginTime,Date endTime){
        return roomStateReportMapper.getSumByDate(beginTime,endTime);
    }
}
