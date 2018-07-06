package com.sygdsoft.sqlProvider;

import java.util.Map;

public class PayPointOfSaleSql {
    public PayPointOfSaleSql() {
    }

    public String getDebtMoney(Map<String, Object> parameters) {
        String pointOfSale = (String) parameters.get("pointOfSale");
        String module = (String) parameters.get("module");
        String basic = "SELECT round(ifnull(sum(money),0),2) FROM pay_point_of_sale WHERE do_time>#{beginTime} AND do_time<#{endTime} AND currency NOT IN ('转房客','转哑房','转单位')";
        if (pointOfSale != null) {
            basic += " and point_of_sale = #{pointOfSale}";
        }
        if ("接待".equals(module)) {
            basic += " and point_of_sale != \'餐饮\'";
        } else {
            basic += " and point_of_sale = \'餐饮\'";
        }
        return basic;
    }
}
