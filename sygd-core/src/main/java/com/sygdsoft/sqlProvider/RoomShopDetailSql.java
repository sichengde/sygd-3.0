package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-03-30.
 */
public class RoomShopDetailSql {
    public RoomShopDetailSql() {
    }
    public String getList(Map<String, Object> parameters){
        String userId=(String) parameters.get("userId");
        String basic="select * from room_shop_detail where do_time>#{beginTime} and do_time < #{endTime}";
        if(userId!=null){
            basic+=" and user_id=#{userId}";
        }
        return basic;
    }
}
