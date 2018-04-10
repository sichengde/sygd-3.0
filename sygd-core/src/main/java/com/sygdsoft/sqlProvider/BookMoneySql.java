package com.sygdsoft.sqlProvider;

import java.util.Map;

public class BookMoneySql {
    public BookMoneySql() {
    }

    public String getMoney(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String currency = (String) map.get("currency");
        Boolean negative = (Boolean) map.get("negative");
        String basic = "select sum(subscription) from book_money where do_time > #{beginTime} and do_time< #{endTime} ";
        if (userId != null) {
            basic += " and userId=#{userId}";
        }
        if (currency != null) {
            basic += " and currency=#{currency}";
        }
        if (negative != null && negative) {
            basic += " and subscription<0";
        } else {
            basic += " and subscription>0";
        }
        return basic;
    }
}
