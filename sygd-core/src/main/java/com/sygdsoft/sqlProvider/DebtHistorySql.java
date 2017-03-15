package com.sygdsoft.sqlProvider;

import com.sygdsoft.util.Util;

import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Administrator on 2016/9/11 0011.
 */
public class DebtHistorySql {
    public DebtHistorySql() {
    }
    public String getTotalConsumeByPointOfSaleAndSerial(Map<String, Object> parameters){
        Util util=new Util();
        String serial= (String) parameters.get("serial");
        String pointOfSale= (String) parameters.get("pointOfSale");
        if(serial.substring(0,1).equals("S")){//自付账号
            return "select sum(consume) consume from debt_history where point_of_sale="+util.wrapWithBrackets(pointOfSale)+" and self_account="+util.wrapWithBrackets(serial);
        }else {
            return "select sum(consume) consume from debt_history where point_of_sale="+util.wrapWithBrackets(pointOfSale)+" and group_account="+util.wrapWithBrackets(serial);
        }
    }

    public String getHistoryConsume(Map<String, Object> parameters){
        String pointOfSale=(String) parameters.get("pointOfSale");
        if(pointOfSale==null){
            return "select ifnull(sum(consume),0) consume from debt_history where consume>0 and done_time>#{beginTime} and done_time<#{endTime}";
        }else {
            return "select ifnull(sum(consume),0) consume from debt_history where consume>0 and done_time>#{beginTime} and done_time<#{endTime} and point_of_sale=#{pointOfSale}";
        }
    }
}
