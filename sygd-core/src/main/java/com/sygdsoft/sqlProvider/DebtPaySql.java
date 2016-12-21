package com.sygdsoft.sqlProvider;

import com.sygdsoft.service.TimeService;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by 舒展 on 2016-07-12.
 */
public class DebtPaySql {
    public DebtPaySql() {
    }

    public String selectByCurrencyDate(Map<String, Object> parameters){
        Util util=new Util();
        TimeService timeService=new TimeService();
        String currency= (String) parameters.get("currency");
        String beginTime= timeService.dateToStringLong((Date) parameters.get("beginTime"));
        String endTime= timeService.dateToStringLong((Date) parameters.get("endTime"));
        if(currency.equals("未选择币种")) {
            return "select * from debt_pay where currency is null and done_time>" + util.wrapWithBrackets(beginTime)+" and done_time<"+util.wrapWithBrackets(endTime);
        }else {
            return "select * from debt_pay where currency=" + util.wrapWithBrackets(currency) + " and done_time>" + util.wrapWithBrackets(beginTime)+" and done_time<"+util.wrapWithBrackets(endTime);
        }
    }
}
