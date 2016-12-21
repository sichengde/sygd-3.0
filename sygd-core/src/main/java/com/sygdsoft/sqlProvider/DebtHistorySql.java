package com.sygdsoft.sqlProvider;

import com.sygdsoft.util.Util;

import java.util.Map;

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
}
