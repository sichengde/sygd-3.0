package com.sygdsoft.sqlProvider;

import java.util.Date;
import java.util.Map;

/**
 * Created by 舒展 on 2017-02-21.
 */
public class CompanyDebtSql {
    public CompanyDebtSql() {
    }

    public String getModuleDebt(Map<String, Object> map) {
        String lord = (String) map.get("lord");
        String basic = "SELECT ifnull(round(sum(debt),2),0) FROM company_debt WHERE point_of_sale=#{module} AND company=#{company} ";
        if (lord != null) {
            basic += " and lord=#{lord}";
        }
        return basic;
    }
    public String getSumDebtMoney(Map<String, Object> map) {
        Date beginTime = (Date) map.get("beginTime");
        Date endTime = (Date) map.get("endTime");
        String pointOfSale = (String) map.get("pointOfSale");
        String basic="SELECT round(ifnull(sum(debt),0),2) FROM company_debt WHERE 1=1";
        if (beginTime != null) {
            basic += " and do_time > #{beginTime}";
        }
        if (endTime != null) {
            basic += " and do_time < #{endTime}";
        }
        if (pointOfSale != null) {
            basic += " and point_of_sale = #{pointOfSale}";
        }
        return basic;
    }
}
