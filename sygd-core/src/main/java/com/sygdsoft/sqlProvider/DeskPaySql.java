package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-03-21.
 */
public class DeskPaySql {
    public DeskPaySql() {
    }

    public String getPay(Map<String, Object> parameters){
        String userId=(String) parameters.get("userId");
        String currency=(String) parameters.get("currency");
        String pointOfSale=(String) parameters.get("pointOfSale");
        String basic;
        basic="select ifnull(sum(pay_money),0) deskMoney from desk_pay where done_time > #{beginTime} and done_time< #{endTime} and ifnull(disabled,false)=false";
        if(pointOfSale!=null){
            basic+=" and point_of_sale = #{pointOfSale}";
        }
        if(currency!=null){
            basic+=" and currency=#{currency}";
        }
        if(userId!=null){
            basic+=" and user_id=#{userId}";
        }
        return basic;
    }
}
