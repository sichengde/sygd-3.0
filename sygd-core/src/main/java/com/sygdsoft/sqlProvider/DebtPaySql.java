package com.sygdsoft.sqlProvider;

import com.sygdsoft.service.TimeService;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 舒展 on 2016-07-12.
 */
public class DebtPaySql {
    public DebtPaySql() {
    }

    public String getList(Map<String, Object> parameters){
        String userId=(String) parameters.get("userId");
        String currency=(String) parameters.get("currency");
        String orderByList= (String) parameters.get("orderByList");
        String basic;
        basic="select * from debt_pay where done_time > #{beginTime} and done_time< #{endTime}";
        if(userId!=null){
            basic+=" and user_id = #{userId}";
        }
        if(currency!=null){
            basic+=" and currency=#{currency}";
        }
        if(orderByList!=null){
            basic+="order by "+orderByList;
        }
        return basic;
    }

    public String getDebtMoney(Map<String, Object> parameters){
        String userId= (String) parameters.get("userId");
        String currency= (String) parameters.get("currency");
        Boolean payTotal= (Boolean) parameters.get("payTotal");
        String basic;
        if(payTotal){
            basic="select ifnull(sum(debt_money),0) debtMoney from debt_pay LEFT JOIN currency ON debt_pay.currency=currency.currency where done_time > #{beginTime} and done_time< #{endTime}  and pay_total=true";
        }else {
            basic = "select ifnull(sum(debt_money),0) debtMoney from debt_pay where done_time > #{beginTime} and done_time< #{endTime}";
        }
        if(userId!=null){
            basic+=" and user_id = #{userId}";
        }
        if(currency!=null){
            basic+=" and currency=#{currency}";
        }
        return basic;
    }
}
