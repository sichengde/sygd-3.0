package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-04-11.
 */
public class CheckInIntegrationSql {
    public CheckInIntegrationSql() {
    }

    public String getSumNum(Map<String,Object> map){
        String guestSource=(String) map.get("guestSource");
        String basic="SELECT count(*) FROM check_in_integration cii LEFT JOIN guest_integration gi on cii.self_account=gi.self_account WHERE cii.reach_time>#{beginTime} AND cii.reach_time<#{endTime}";
        if(guestSource!=null){
            basic+=" and guest_source=#{guestSource}";
        }
        return basic;
    }
}
