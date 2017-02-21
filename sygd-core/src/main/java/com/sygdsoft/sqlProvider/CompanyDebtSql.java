package com.sygdsoft.sqlProvider;

import java.util.Date;
import java.util.Map;

/**
 * Created by 舒展 on 2017-02-21.
 */
public class CompanyDebtSql {
    public CompanyDebtSql() {
    }

    public String getDebt(Map<String, Object> parameters) {
        String userId = (String) parameters.get("userId");
        String currency = (String) parameters.get("currency");
        if (userId != null && currency != null) {
            return "select -sum(debt) from company_debt where debt<0 and user_id=#{userId} and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}";
        } else if (userId == null && currency != null) {
            return "select -sum(debt) from company_debt where debt<0 and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}";
        } else {
            return "select -sum(debt) from company_debt where debt<0 and do_time>#{beginTime} and do_time<#{endTime}";
        }
    }
}
