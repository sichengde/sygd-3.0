package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomStateReport;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-02-07.
 */
public interface RoomStateReportMapper extends MyMapper<RoomStateReport>{
    @Delete("delete from room_state_report where report_time=#{date}")
    void deleteByDate(@Param("date") Date date);

    @Select("SELECT sum(total) total ,sum(rent) rent FROM room_state_report WHERE report_time>#{beginTime} AND report_time<#{endTime}")
    RoomStateReport getSumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
