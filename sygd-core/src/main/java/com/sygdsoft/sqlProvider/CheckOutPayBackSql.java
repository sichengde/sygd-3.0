package com.sygdsoft.sqlProvider;

import java.util.Map;

public class CheckOutPayBackSql {
    public CheckOutPayBackSql() {
    }

    public String getTotal(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String currency = (String) map.get("currency");
        String basic = "SELECT round(sum(money),2) from check_out_pay_back WHERE done_time>#{beginTime} AND done_time<#{endTime} ";
        if (userId != null) {
            basic += " and userId=#{userId}";
        }
        if (currency != null) {
            basic += " and currency=#{currency}";
        }
        return basic;
    }
}
