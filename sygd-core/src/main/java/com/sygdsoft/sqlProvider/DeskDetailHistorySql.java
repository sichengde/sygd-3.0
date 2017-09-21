package com.sygdsoft.sqlProvider;

import java.util.Date;
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
    public String getSumList(Map<String, Object> parameters){
        String pointOfSale= (String) parameters.get("pointOfSale");
        String category= (String) parameters.get("category");
        Boolean mergeFood= (Boolean) parameters.get("mergeFood");
        String basic;
        if(mergeFood) {
            basic = "SELECT food_sign foodSign, sum(num) num,sum(total) total,sum(after_discount) afterDiscount FROM desk_detail_history ddh WHERE done_time>#{beginTime} AND done_time<#{endTime} and ifnull(disabled,false)=false";
        }else {
            basic = "SELECT food_name foodName, sum(num) num,sum(total) total,sum(after_discount) afterDiscount FROM desk_detail_history ddh WHERE done_time>#{beginTime} AND done_time<#{endTime} and ifnull(disabled,false)=false";
        }
        if(pointOfSale!=null){
            basic+=" and ddh.point_of_sale=#{pointOfSale}";
        }
        if(category!=null){
            basic+=" and category=#{category}";
        }
        if(mergeFood){
            basic+=" group by food_sign";
        }else {
            basic+=" group by food_name";
        }
        return basic;
    }

}
