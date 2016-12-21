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
}
