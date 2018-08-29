package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-02-21.
 */
public class VipIntegrationSql {
    public VipIntegrationSql() {
    }

    public String updateVipRemain(Map<String, Object> parameters){
        String basic="update vip set remain=ifnull(remain,0)+#{money}";
        Boolean withScore=(Boolean) parameters.get("withScore");
        if(withScore!=null&&withScore){
            basic+=",score=ifnull(score,0)+#{money} ";
        }
        basic+=" where vip_number=#{vipNumber}";
        return basic;
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
            basic += " and user_id=#{userId} ";
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
