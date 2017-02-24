package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-02-24.
 */
public class CompanyPaySql {
    public CompanyPaySql() {
    }
    public String getDebt(Map<String, Object> parameters) {
        String userId = (String) parameters.get("userId");
        String currency = (String) parameters.get("currency");
        String company = (String) parameters.get("company");
        if(company!=null){//有单位就先按照单位结算
            return "SELECT sum(debt) debt,sum(pay) pay FROM company_pay WHERE company=#{company} and done_time>#{beginTime} AND done_time<#{endTime}";
        }
        if (userId != null && currency != null) {
            return "SELECT sum(debt) debt,sum(pay) pay FROM company_pay WHERE user_id=#{userId} and currency={currency} and done_time>#{beginTime} AND done_time<#{endTime} AND currency!=\'转单位\'";
        } else if (userId == null && currency != null) {
            return "SELECT sum(debt) debt,sum(pay) pay FROM company_pay WHERE currency=#{currency} and done_time>#{beginTime} AND done_time<#{endTime}";
        } else {
            return "SELECT sum(debt) debt,sum(pay) pay FROM company_pay WHERE done_time>#{beginTime} AND done_time<#{endTime} AND currency!=\'转单位\'";
        }
    }
}
