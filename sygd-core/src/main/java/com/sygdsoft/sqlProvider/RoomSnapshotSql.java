package com.sygdsoft.sqlProvider;

import java.util.Map;

public class RoomSnapshotSql {
    public RoomSnapshotSql() {
    }


    public String getSum(Map<String, Object> parameters) {
        String state=(String) parameters.get("state");
        String basic="SELECT ifnull(sum(rent),2) sumRent, ifnull(sum(all_day_room),2) sumAllDayRoom, ifnull(sum(hour_room),2) sumHourRoom, ifnull(sum(add_room),2) sumAddRoom, ifnull(sum(night_room),2) sumNightRoom, ifnull(sum(real_room),2) sumRealRoom, ifnull(round(sum(all_day_room_consume), 2),2) allDayRoomConsume, ifnull(round(sum(hour_room_consume), 2),2) hourRoomConsume, ifnull(round(sum(add_room_consume), 2),2) addRoomConsume, ifnull(round(sum(night_room_consume), 2),2) nightRoomConsume FROM room_snapshot WHERE report_time >=#{beginTime} AND report_time<=#{endTime}";
        if(state!=null){
            basic+=" and state=#{state}";
        }
        return basic;
    }
    public String getCount(Map<String, Object> parameters){
        Boolean ifRoom=(Boolean) parameters.get("ifRoom");
        String basic="select count(*) from room_snapshot WHERE report_time>=? and report_time<=?";
        if(ifRoom!=null){
            basic+=" and real_room = #{ifRoom}";
        }
        return basic;
    }
}
