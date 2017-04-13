package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-04-11.
 */
public class CheckInIntegrationSql {
    public CheckInIntegrationSql() {
    }
    public String getSumCount(Map<String,Object> map){
        String guestSource=(String) map.get("guestSource");
        String roomCategory=(String) map.get("roomCategory");
        String basic="select count(*) from check_in_integration where reach_time>#{beginTime} and reach_time<#{endTime}";
        if(guestSource!=null){
            basic+=" and guest_source=#{guestSource}";
        }
        if(roomCategory!=null){
            basic+=" and room_category=#{roomCategory}";
        }
        return basic;
    }

    public String getSumConsume(Map<String,Object> map){
        String guestSource=(String) map.get("guestSource");
        String roomCategory=(String) map.get("roomCategory");
        String basic="select ifnull(sum(consume),0) from check_in_integration where reach_time>#{beginTime} and reach_time<#{endTime}";
        if(guestSource!=null){
            basic+=" and guest_source=#{guestSource}";
        }
        if(roomCategory!=null){
            basic+=" and room_category=#{roomCategory}";
        }
        return basic;
    }
}