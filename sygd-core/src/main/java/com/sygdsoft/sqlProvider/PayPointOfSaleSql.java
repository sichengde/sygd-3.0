package com.sygdsoft.sqlProvider;

import java.util.Date;
import java.util.Map;

public class PayPointOfSaleSql {
    public PayPointOfSaleSql() {
    }

    public String getDebtMoney(Map<String, Object> parameters){
        String pointOfSale=(String) parameters.get("pointOfSale");
        String basic="SELECT round(ifnull(sum(money),0),2) FROM pay_point_of_sale WHERE do_time>#{beginTime} AND do_time<#{endTime} AND currency NOT IN ('转房客','转哑房','转单位')";
        if(pointOfSale!=null){
            basic+=" and point_of_sale = #{pointOfSale}";
        }
        return basic;
    }
}
