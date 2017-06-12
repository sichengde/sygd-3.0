package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomStateReport;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-02-07.
 */
public interface RoomStateReportMapper extends MyMapper<RoomStateReport>{
    @Delete("delete from room_state_report where report_time=#{date}")
    void deleteByDate(@Param("date") Date date);

    @Select("SELECT \'总计\' category, sum(total) total, sum(empty) empty, sum(repair) repair, sum(self) self, sum(back_up) backUp, sum(rent) rent, sum(total_real) totalReal, sum(all_day_room) allDayRoom, sum(hour_room) hourRoom, sum(add_room) addRoom, sum(all_day_room_consume) allDayRoomConsume, sum(hour_room_consume) hourRoomConsume, sum(add_room_consume) addRoomConsume, sum(night_room) nightRoom, sum(night_room_consume) nightRoomConsume FROM room_state_report WHERE report_time>=#{beginTime} AND report_time<=#{endTime}")
    RoomStateReport getSumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT category, sum(total) total, sum(empty) empty, sum(repair) repair, sum(self) self, sum(back_up) backUp, sum(rent) rent, sum(total_real) totalReal, sum(all_day_room) allDayRoom, sum(hour_room) hourRoom, sum(add_room) addRoom, sum(all_day_room_consume) allDayRoomConsume, sum(hour_room_consume) hourRoomConsume, sum(add_room_consume) addRoomConsume, sum(night_room) nightRoom, sum(night_room_consume) nightRoomConsume FROM room_state_report where report_time>=#{beginTime} AND report_time<=#{endTime} group by category")
    List<RoomStateReport> getSumByDateCategory(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT truncate(sum(rent)/sum(total_real),2) FROM room_state_report where report_time>=#{beginTime} AND report_time<=#{endTime} and category=#{roomCategory}")
    @ResultType(Double.class)
    Double getRentRateOnly(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("roomCategory")String roomCategory);
}
