package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-03-16.
 */
public class DeskDetailSql {
    public DeskDetailSql() {
    }
    public String getList(Map<String, Object> parameters){
        String orderByList= (String) parameters.get("orderByList");
        String desk= (String) parameters.get("desk");
        Boolean foodSet= (Boolean) parameters.get("foodSet");
        String basic;
        basic="select * from desk_detail where point_of_sale=#{pointOfSale}";
        if(desk!=null){
            basic+=" and desk=#{desk}";
        }
        if(foodSet!=null){
            basic+=" and food_set=#{foodSet}";
        }
        if(orderByList!=null){
            basic+=" order by "+orderByList;
        }
        return basic;
    }
}
