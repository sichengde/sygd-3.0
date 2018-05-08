package com.sygdsoft.sqlProvider;

import com.sygdsoft.util.Util;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Administrator on 2016/9/11 0011.
 */
public class DebtHistorySql {
    public DebtHistorySql() {
    }

    public String getTotalConsumeByPointOfSaleAndSerial(Map<String, Object> parameters) {
        Util util = new Util();
        String serial = (String) parameters.get("serial");
        String pointOfSale = (String) parameters.get("pointOfSale");
        if (serial.substring(0, 1).equals("S")) {//自付账号
            return "select sum(consume) consume from debt_history where point_of_sale=" + util.wrapWithBrackets(pointOfSale) + " and self_account=" + util.wrapWithBrackets(serial);
        } else {
            return "select sum(consume) consume from debt_history where point_of_sale=" + util.wrapWithBrackets(pointOfSale) + " and group_account=" + util.wrapWithBrackets(serial);
        }
    }

    /**
     * 包括冲账
     *
     * @param parameters
     * @return
     */
    public String getHistoryConsume(Map<String, Object> parameters) {
        String pointOfSale = (String) parameters.get("pointOfSale");
        boolean positive = (boolean) parameters.get("positive");
        String basic="select ifnull(sum(consume),0) consume from debt_history where done_time>#{beginTime} and done_time<#{endTime}";
        if(pointOfSale!=null){
            basic+=" and point_of_sale=#{pointOfSale}";
        }
        if(positive){
            basic+=" and consume>0";
        }
        return basic;
    }

    /**
     * 包括冲账
     *
     * @param parameters
     * @return
     */
    public String getHistoryConsumeRich(Map<String, Object> parameters) {
        List<String> pointOfSaleList = (List<String>) parameters.get("pointOfSaleList");
        List<String> guestSourceList = (List<String>) parameters.get("guestSourceList");
        List<String> roomCategoryList = (List<String>) parameters.get("roomCategoryList");
        String basic = "SELECT ifnull(sum(dh.consume),0) FROM debt_history dh LEFT JOIN check_in_history_log cihl ON dh.self_account = cihl.self_account where done_time>#{beginTime} and done_time<#{endTime} ";

        String posStr = "";
        for (String pointOfSale : pointOfSaleList) {
            posStr = posStr + " or point_of_sale=\'" + pointOfSale + "\'";
        }
        if (!posStr.equals("")) {
            basic += " and ( " + posStr.substring(4, posStr.length()) + " )";
        }

        String categoryStr = "";
        for (String roomCategory : roomCategoryList) {
            categoryStr = categoryStr + " or room_category=\'" + roomCategory + "\'";
        }
        if (!categoryStr.equals("")) {
            basic += " and ( " + categoryStr.substring(4, categoryStr.length()) + " )";
        }

        String guestSourceStr = "";
        for (String guestSource : guestSourceList) {
            guestSourceStr = guestSourceStr + " or dh.guest_source=\'" + guestSource + "\'";
        }
        if (!guestSourceStr.equals("")) {
            basic += " and ( " + guestSourceStr.substring(4, guestSourceStr.length()) + " )";
        }
        return basic;
    }

    public String getTotalCancelDeposit(Map<String, Object> parameters) {
        String userId = (String) parameters.get("userId");
        //以前连checkOut表，团队结算时会丢失押金
        //String basic = "SELECT round(sum(debt_history.deposit), 2) deposit FROM debt_history LEFT JOIN check_in_history_log ON debt_history.self_account = check_in_history_log.self_account LEFT JOIN check_out ON check_in_history_log.check_out_serial = check_out.check_out_serial WHERE debt_history.done_time > #{beginTime} AND debt_history.done_time < #{endTime} AND debt_history.currency = #{currency} AND ifnull(back, FALSE) = FALSE AND check_out.check_out_serial IS NOT NULL";
        String basic = "SELECT round(sum(debt_history.deposit), 2) deposit FROM debt_history LEFT JOIN debt_pay ON debt_history.pay_serial = debt_pay.pay_serial WHERE debt_history.done_time > #{beginTime} AND debt_history.done_time < #{endTime} AND debt_history.currency = #{currency} AND ifnull(back, FALSE) = FALSE";
        if (userId != null) {
            basic += " and debt_pay.user_id = #{userId}";
        }
        return basic;
    }

    public String getCancelDeposit(Map<String, Object> parameters) {
        String userId = (String) parameters.get("userId");
        String basic = "SELECT debt_history.self_account, debt_history.room_id roomId, sum(debt_history.deposit) deposit, debt_pay.user_id, debt_history.currency, '结账退预付' description,debt_history.done_time doneTime FROM debt_history LEFT JOIN debt_pay ON debt_history.pay_serial = debt_pay.pay_serial WHERE debt_history.done_time > #{beginTime} AND debt_history.done_time < #{endTime} AND debt_history.currency = #{currency} AND ifnull(back, FALSE) = FALSE ";
        if (userId != null) {
            basic += " and debt_pay.user_id = #{userId}";
        }
        basic+=" GROUP BY debt_history.self_account,debt_history.currency";
        return basic;
    }
}
