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
        String basic = "SELECT sum(debt) FROM company_debt WHERE point_of_sale=#{module} AND company=#{company} ";
        if (lord != null) {
            basic += " and lord=#{lord}";
        }
        return basic;
    }
}
