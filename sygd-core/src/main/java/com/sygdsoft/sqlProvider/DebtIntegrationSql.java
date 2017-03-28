package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-03-28.
 */
public class DebtIntegrationSql {
    public DebtIntegrationSql() {
    }
    public String getDepositByUserCurrencyDate(Map<String, Object> parameters){
        String userId= (String) parameters.get("userId");
        String basic="SELECT abs(sum(deposit)) FROM debt_integration WHERE do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit<0";
        if(userId!=null){
            basic+=" and user_id = #{userId}";
        }
        return basic;
    }
}
