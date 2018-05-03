package com.sygdsoft.sqlProvider;

import java.util.Map;

public class RoomSnapshotSql {
    public RoomSnapshotSql() {
    }


    public String getSum(Map<String, Object> parameters) {
        String state=(String) parameters.get("state");
        String basic="SELECT ifnull(sum(rent), 0) sumRent, ifnull(sum(all_day_room), 0) sumAllDayRoom, ifnull(sum(hour_room), 0) sumHourRoom, ifnull(sum(add_room), 0) sumAddRoom, ifnull(sum(night_room), 0) sumNightRoom, ifnull(sum(real_room), 0) sumRealRoom, ifnull(sum(free), 0) sumFree, ifnull(sum(available), 0) sumAvailable, ifnull(sum(self), 0) sumSelf, ifnull(sum(repair), 0) sumRepair, ifnull(round(sum(all_day_room_consume), 2), 0) allDayRoomConsume, ifnull(round(sum(hour_room_consume), 2), 0) hourRoomConsume, ifnull(round(sum(add_room_consume), 2), 0) addRoomConsume, ifnull(round(sum(night_room_consume), 2), 0) nightRoomConsume FROM room_snapshot WHERE report_time >=#{beginTime} AND report_time<#{endTime}";
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
