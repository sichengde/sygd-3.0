package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomSnapshot;
import com.sygdsoft.sqlProvider.RoomSnapshotSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface RoomSnapshotMapper extends MyMapper<RoomSnapshot> {
    @Delete("delete from room_snapshot where report_time=#{date}")
    void deleteByDate(@Param("date") Date date);

    @Select(" SELECT currency, sum(rent) sumRent, sum(all_day_room) sumAllDayRoom, sum(hour_room) sumHourRoom, sum(add_room) sumAddRoom, sum(night_room) sumNightRoom, round(sum(all_day_room_consume),2) allDayRoomConsume, round(sum(hour_room_consume),2) hourRoomConsume, round(sum(add_room_consume),2) addRoomConsume, round(sum(night_room_consume),2) nightRoomConsume FROM (SELECT room_snapshot.*, debt_pay.currency FROM room_snapshot, debt_pay WHERE room_snapshot.self_account = debt_pay.self_account AND debt_pay.pay_serial IS NOT NULL AND debt_pay.debt_category = '离店结算' AND report_time >=#{beginTime} AND report_time<=#{endTime} union ALL SELECT room_snapshot.*,debt_pay.currency FROM room_snapshot ,debt_pay WHERE room_snapshot.group_account=debt_pay.group_account AND debt_pay.pay_serial IS NOT NULL AND debt_pay.debt_category='离店结算' and report_time>=#{beginTime} AND report_time<#{endTime}) a GROUP BY currency")
    List<RoomSnapshot> getPaidRoom(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @SelectProvider(type = RoomSnapshotSql.class,method = "getSum")
    RoomSnapshot getSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("state")String state);

    @SelectProvider(type = RoomSnapshotSql.class,method = "getCount")
    Integer getCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("ifRoom") Boolean ifRoom);

    @Select("SELECT ifnull(sum(ifnull(all_day_room,0) + ifnull(night_room,0)),0) FROM room_snapshot  WHERE guest_source IS NOT NULL AND report_time >= #{beginTime} AND report_time < #{endTime} AND guest_source = #{guestSource}")
    @ResultType(Double.class)
    Double getNumByGuestSource(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("guestSource")String guestSource);

    @Select("SELECT ifnull(round(sum(ifnull(all_day_room_consume, 0) + ifnull(night_room_consume, 0))/sum(ifnull(all_day_room, 0) + ifnull(night_room, 0)), 2),0) FROM room_snapshot rs LEFT JOIN guest_source gs ON rs.guest_source = gs.guest_source WHERE gs.guest_source IS NOT NULL AND report_time >= #{beginTime} AND report_time < #{endTime} AND gs.count_category=#{countCategory}")
    @ResultType(Double.class)
    Double getConsumeByGuestSource(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("countCategory")String countCategory);

    @Select("SELECT ifnull(count(*),0) FROM room_snapshot WHERE report_time>=#{beginTime} AND report_time<#{endTime} AND real_room is NULL;")
    @ResultType(Integer.class)
    Integer getNotRoomCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT ifnull(sum(available),0) FROM room_snapshot WHERE report_time>=#{beginTime} AND report_time<#{endTime} AND real_room is NULL;")
    @ResultType(Integer.class)
    Integer getNotRoomAvailableCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
