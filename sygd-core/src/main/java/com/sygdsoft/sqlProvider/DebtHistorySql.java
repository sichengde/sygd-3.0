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

    public String getTotalCancelDeposit(Map<String,Object> parameters){
        String userId=(String) parameters.get("userId");
        String basic="select sum(debt_history.deposit) deposit from debt_history LEFT JOIN debt_pay ON debt_history.pay_serial=debt_pay.pay_serial where debt_history.done_time > #{beginTime} and debt_history.done_time< #{endTime} and debt_history.currency=#{currency} and debt_history.deposit>0";
        if(userId!=null){
            basic+=" and debt_pay.user_id = #{userId}";
        }
        return basic;
    }

    public String getCancelDeposit(Map<String,Object> parameters){
        String userId=(String) parameters.get("userId");
        String basic="select * from debt_history LEFT JOIN debt_pay ON debt_history.pay_serial=debt_pay.pay_serial where debt_history.done_time > #{beginTime} and debt_history.done_time< #{endTime} and debt_history.currency=#{currency} and debt_history.deposit>0";
        if(userId!=null){
            basic+=" and debt_pay.user_id = #{userId}";
        }
        return basic;
    }
}
