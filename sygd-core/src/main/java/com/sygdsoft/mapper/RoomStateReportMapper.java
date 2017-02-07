package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomStateReport;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-07.
 */
public interface RoomStateReportMapper extends MyMapper<RoomStateReport>{
    @Delete("delete from room_state_report where report_time=#{date}")
    void deleteByDate(@Param("date") Date date);
}
