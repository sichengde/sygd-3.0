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
        String basic="SELECT  item,sum(num) num,sum(total_money) totalMoney,unit,ifnull(currency,'挂账') currency FROM room_shop_detail LEFT JOIN debt_pay ON room_shop_detail.pay_serial = debt_pay.pay_serial where do_time>#{beginTime} and do_time < #{endTime} GROUP BY item,unit,currency";
        if(userId!=null){
            basic+=" and room_shop_detail.user_id=#{userId}";
        }
        return basic;
    }
}
