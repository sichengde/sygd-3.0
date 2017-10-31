package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2017-03-28.
 */
public class DebtIntegrationSql {
    public DebtIntegrationSql() {
    }
    public String getDepositByUserCurrencyDate(Map<String, Object> parameters){
        String userId= (String) parameters.get("userId");
        String basic="SELECT abs(sum(deposit)) FROM debt_integration WHERE do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit<0";
        if(userId!=null){
            basic+=" and user_id = #{userId}";
        }
        return basic;
    }

    public String getSumConsumeByDoTime(Map<String, Object> parameters){
        String pointOfSale= (String) parameters.get("pointOfSale");
        boolean excludeChange= (boolean) parameters.get("excludeChange");
        String basic="SELECT sum(consume) FROM debt_integration WHERE do_time > #{beginTime} and do_time< #{endTime}";
        if(pointOfSale!=null){
            basic+=" and point_of_sale = #{pointOfSale}";
        }
        if(excludeChange){
            basic+=" and ifnull(point_of_sale,'未定义') !=\'挂账\' ";
        }
        return basic;
    }

    public String getList(Map<String, Object> parameters){
        String userId=(String) parameters.get("userId");
        String basic="SELECT point_of_sale pointOfSale,consume,deposit,currency,room_id roomId,pay_serial paySerial,self_account selfAccount,group_account groupAccount ,done_time doneTime,category from debt_integration WHERE do_time > #{beginTime} and do_time< #{endTime} and ifnull(point_of_sale, '未定义') != '挂账'";
        if(userId!=null){
            basic+=" and if(category='全日房费',#{userId},null)=#{userId} ";
        }
        return basic;
    }
    public String getSumDepositByEndTime(Map<String, Object> parameters){
        String userId=(String) parameters.get("userId");
        String basic="SELECT sum(deposit) FROM debt_integration WHERE do_time< #{endTime} and do_time > #{beginTime}";
        if(userId!=null){
            basic+=" and user_id=#{userId} ";
        }
        return basic;
    }

}
