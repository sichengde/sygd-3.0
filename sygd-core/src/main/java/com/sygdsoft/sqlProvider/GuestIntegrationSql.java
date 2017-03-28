package com.sygdsoft.sqlProvider;

import java.util.Date;
import java.util.Map;

/**
 * Created by 舒展 on 2017-03-28.
 */
public class GuestIntegrationSql {
    public GuestIntegrationSql() {
    }
    public String getList(Map<String, Object> parameters){
        String basic="SELECT count(*) total,ifnull(addr,'未定义') addr,sum(if_in) now FROM ( SELECT   card_id,   concat(country,ifnull(city,'')) addr,   self_account,   reach_time,   if_in, city FROM guest_integration LEFT JOIN card_map_city ON card_id LIKE concat(card,'%') ";
        Date beginTime=(Date) parameters.get("beginTime");
        Date endTime=(Date) parameters.get("endTime");
        if(beginTime!=null){
            basic+="where reach_time>#{beginTime} ";
            if (endTime != null) {
                basic += "and reach_time<#{endTime} ";
            }
        }else {
            if (endTime != null) {
                basic += "where reach_time<#{endTime} ";
            }
        }
        basic+=" ) AS detail GROUP BY addr;";
        return basic;
    }
}
