package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomSnapshot;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface RoomSnapshotMapper extends MyMapper<RoomSnapshot> {
    @Delete("delete from room_snapshot where report_time=#{date}")
    void deleteByDate(@Param("date") Date date);

    @Select("SELECT area, sum(rent) rent, sum(all_day_room) allDayRoom, sum(hour_room) hourRoom, sum(add_room) addRoom, sum(night_room) nightRoom, sum(all_day_room_consume) allDayRoomConsume, sum(hour_room_consume) hourRoomConsume, sum(add_room_consume) addRoomConsume, sum(night_room_consume) nightRoomConsume, currency FROM (SELECT room_snapshot.*,debt_pay.currency FROM room_snapshot ,debt_pay WHERE room_snapshot.self_account=debt_pay.self_account AND debt_pay.pay_serial IS NOT NULL union ALL SELECT room_snapshot.*,debt_pay.currency FROM room_snapshot ,debt_pay WHERE room_snapshot.group_account=debt_pay.group_account AND debt_pay.pay_serial IS NOT NULL) a GROUP BY area,currency")
    List<RoomSnapshot> getPaidRoom(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT area, sum(rent) rent, sum(all_day_room) allDayRoom, sum(hour_room) hourRoom, sum(add_room) addRoom, sum(night_room) nightRoom, sum(all_day_room_consume) allDayRoomConsume, sum(hour_room_consume) hourRoomConsume, sum(add_room_consume) addRoomConsume, sum(night_room_consume) nightRoomConsume FROM room_snapshot GROUP BY area")
    RoomSnapshot getSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
