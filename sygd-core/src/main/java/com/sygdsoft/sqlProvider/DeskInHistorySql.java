package com.sygdsoft.sqlProvider;

import java.util.Map;

public class DeskInHistorySql {
    public DeskInHistorySql() {
    }
    public String getTotalDiscount(Map<String, Object> parameters){
        String category= (String) parameters.get("category");
        String basic;
        basic="SELECT ifnull(sum(final_price-total_price),0) FROM desk_in_history dih ";
        if(category!=null){
            basic+=" LEFT JOIN desk d ON dih.desk = d.name ";
        }
        basic+=" WHERE dih.done_time>#{beginTime} AND dih.done_time<#{endTime} AND dih.point_of_sale=#{pointOfSale} ";
        if(category!=null){
            basic+=" AND d.category=#{category}";
        }
        return basic;
    }
}
