package com.sygdsoft.model;

import java.util.Map;

/**
 * Created by 舒展 on 2017-03-30.
 */
public class ExtraSql {
    public ExtraSql() {
    }
    public String getList(Map<String,Object> params){
        String userId=(String) params.get("userId");
        String basic="SELECT  item,num,total_money totalMoney,unit,currency FROM room_shop_detail LEFT JOIN debt_pay ON room_shop_detail.pay_serial = debt_pay.pay_serial where do_time>#{beginTime} and do_time < #{endTime}";
        if(userId!=null){
            basic+="and user_id=#{userId}";
        }
        return basic;
    }
}
