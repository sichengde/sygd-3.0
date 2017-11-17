package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-02-21.
 */
public class VipIntegrationSql {
    public VipIntegrationSql() {
    }

    public String getPay(Map<String, Object> parameters){
        String userId = (String) parameters.get("userId");
        String currency = (String) parameters.get("currency");
        String pointOfSale=(String) parameters.get("pointOfSale");
        String basic="select ifnull(sum(pay),0) pay from vip_integration where do_time>#{beginTime} and do_time<#{endTime}";
        if(userId!=null){
            basic+=" and user_id=#{userId} ";
        }
        if(currency!=null){
            basic+=" and currency=#{currency} ";
        }
        if(pointOfSale!=null){
            basic+=" and point_of_sale=#{pointOfSale}";
        }
        return basic;
    }

    public String getDeserve(Map<String, Object> parameters) {
        String userId = (String) parameters.get("userId");
        String currency = (String) parameters.get("currency");
        String pointOfSale = (String) parameters.get("pointOfSale");
        String basic = "select ifnull(sum(deserve),0) deserve from vip_integration where do_time>#{beginTime} and do_time<#{endTime}";
        if (userId != null) {
            basic += " user_id=#{userId} ";
        }
        if (currency != null) {
            basic += " and currency=#{currency} ";
        }
        if (pointOfSale != null) {
            basic += " and point_of_sale=#{pointOfSale}";
        }
        return basic;
    }
}
