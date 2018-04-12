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

    @Select(" SELECT currency, sum(rent) sumRent, sum(all_day_room) sumAllDayRoom, sum(hour_room) sumHourRoom, sum(add_room) sumAddRoom, sum(night_room) sumNightRoom, sum(all_day_room_consume) sumAllDayRoomConsume, sum(hour_room_consume) sumHourRoomConsume, sum(add_room_consume) sumAddRoomConsume, sum(night_room_consume) sumNightRoomConsume FROM (SELECT room_snapshot.*,debt_pay.currency FROM room_snapshot ,debt_pay WHERE room_snapshot.self_account=debt_pay.self_account AND debt_pay.pay_serial IS NOT NULL AND debt_pay.debt_category='离店结算' and report_time>=#{beginTime} AND report_time<=#{endTime} union ALL SELECT room_snapshot.*,debt_pay.currency FROM room_snapshot ,debt_pay WHERE room_snapshot.group_account=debt_pay.group_account AND debt_pay.pay_serial IS NOT NULL AND debt_pay.debt_category='离店结算' and report_time>=#{beginTime} AND report_time<=#{endTime}) a GROUP BY currency")
    List<RoomSnapshot> getPaidRoom(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT sum(rent) sumRent, sum(all_day_room) sumAllDayRoom, sum(hour_room) sumHourRoom, sum(add_room) sumAddRoom, sum(night_room) sumNightRoom, sum(all_day_room_consume) sumAllDayRoomConsume, sum(hour_room_consume) sumHourRoomConsume, sum(add_room_consume) sumAddRoomConsume, sum(night_room_consume) sumNightRoomConsume FROM room_snapshot where report_time>=#{beginTime} AND report_time<=#{endTime}")
    RoomSnapshot getSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
