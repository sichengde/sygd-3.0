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
        if (userId != null && currency != null) {
            return "select sum(pay) pay from vip_integration where user_id=#{userId} and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}";
        } else if (userId == null && currency != null) {
            return "select sum(pay) pay from vip_integration where currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}";
        } else {
            return "select sum(pay) pay from vip_integration where do_time>#{beginTime} and do_time<#{endTime}";
        }
    }
}
