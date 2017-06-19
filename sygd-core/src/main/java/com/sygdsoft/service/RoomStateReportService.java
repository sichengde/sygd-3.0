package com.sygdsoft.service;

import com.sygdsoft.mapper.RoomStateReportMapper;
import com.sygdsoft.model.RoomStateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    @Autowired
    TimeService timeService;

    /**
     * 通过日期删除该天的数据（夜审前）
     */
    public void deleteByDate(Date date){
        roomStateReportMapper.deleteByDate(date);
    }

    /**
     * 获得某段时期内的总和（不分房类）
     */
    public RoomStateReport getSumByDate(Date beginTime,Date endTime) throws ParseException {
        return roomStateReportMapper.getSumByDate(timeService.getMinTime(beginTime),timeService.getMinTime(endTime));
    }

    /**
     * 获得某段时期内的总和（分房类）
     */
    public List<RoomStateReport> getSumByDateCategory(Date beginTime,Date endTime) throws ParseException {
        return roomStateReportMapper.getSumByDateCategory(timeService.getMinTime(beginTime),timeService.getMinTime(endTime));
    }

    /**
     * 根据房类单独获取一下出租率
     */
    public Double getRentRateOnly(Date beginTime,Date endTime,String roomCategory) throws ParseException {
        return roomStateReportMapper.getRentRateOnly(timeService.getMinTime(beginTime), timeService.getMinTime(endTime), roomCategory);
    }

    /**
     * 根据时间获得各个总和
     */
    public List<RoomStateReport> RoomStateReportGetChart(Date beginTime,Date endTime){
        return roomStateReportMapper.RoomStateReportGetChart(beginTime, endTime);
    }
}
