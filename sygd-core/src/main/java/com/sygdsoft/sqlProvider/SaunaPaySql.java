package com.sygdsoft.sqlProvider;

import java.util.Map;

public class SaunaPaySql {
    public SaunaPaySql() {
    }
    public String getPayMoney(Map<String, Object> parameters){
        String userId= (String) parameters.get("userId");
        String currency= (String) parameters.get("currency");
        String basic;
            basic = "select ifnull(sum(pay_money),0) payMoney from sauna_pay where done_time > #{beginTime} and done_time< #{endTime}";
        if(userId!=null){
            basic+=" and user_id = #{userId}";
        }
        if(currency!=null){
            basic+=" and currency=#{currency}";
        }
        return basic;
    }
}
