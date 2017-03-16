package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-03-16.
 */
public class DeskDetailHistorySql {
    public DeskDetailHistorySql() {
    }
    public String getList(Map<String, Object> parameters){
        String orderByList= (String) parameters.get("orderByList");
        String ckSerial= (String) parameters.get("ckSerial");
        String basic;
        basic="select * from desk_detail_history where ck_serial=#{ckSerial}";
        if(orderByList!=null){
            basic+=" order by "+orderByList;
        }
        return basic;
    }
}
