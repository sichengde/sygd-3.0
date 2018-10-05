package com.sygdsoft.sqlProvider;

import java.util.Map;

public class CleanRoomSql {
    public CleanRoomSql() {
    }
    public String cleanRoomGetWithCategory(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String basic="select cr.id id, num, cr.category category, room, do_time doTime, user_id userId, r.category roomCategory from clean_room cr left join room r on cr.room = r.room_id where ";
        if(userId==null){
            basic+=" cr.user_id is null";
        }else {
            basic+="cr.user_id = #{userId}";
        }
        return basic;
    }
}
