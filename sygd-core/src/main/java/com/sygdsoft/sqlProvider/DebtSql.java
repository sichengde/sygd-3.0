package com.sygdsoft.sqlProvider;

import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by 舒展 on 2016-08-30.
 */
public class DebtSql {
    public DebtSql() {
    }
    public String getTotalConsumeByPointOfSaleAndSerial(Map<String, Object> parameters){
        Util util=new Util();
        String serial= (String) parameters.get("serial");
        String pointOfSale= (String) parameters.get("pointOfSale");
        if(serial.substring(0,1).equals("s")){//自付账号
            return "select sum(consume) consume from debt where point_of_sale="+util.wrapWithBrackets(pointOfSale)+" and self_account="+util.wrapWithBrackets(serial);
        }else {
            return "select sum(consume) consume from debt where point_of_sale="+util.wrapWithBrackets(pointOfSale)+" and group_account="+util.wrapWithBrackets(serial);
        }
    }

    public String getDepositMoneyAll(Map<String, Object> map){
        String currency = (String) map.get("currency");
        String basic = "select ifnull(sum(deposit),0) from debt where deposit is not null ";
        if (currency != null) {
            basic += " and currency=#{currency}";
        }
        return basic;
    }

    public String getConsumeByPointOfSale(Map<String, Object> map){
        String pointOfSale = (String) map.get("pointOfSale");
        String basic="SELECT round(sum(ifnull(consume,0)),2) FROM debt ";
        if(pointOfSale!=null){
            basic+=" where point_of_sale = #{pointOfSale}";
        }
        return basic;
    }
}
