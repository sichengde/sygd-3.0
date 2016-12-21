package com.sygdsoft.mapper;

import com.sygdsoft.model.DebtPay;
import com.sygdsoft.sqlProvider.DebtPaySql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface DebtPayMapper extends MyMapper<DebtPay> {
    /**
     * 获得列表
     */
    /*操作员，币种，时段*/
    @Select("select * from debt_pay where user_id = #{userId} and done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency}")
    @Results(value = {
            @Result(property = "paySerial", column = "pay_serial"),
            @Result(property = "checkOutSerial", column = "check_out_serial"),
            @Result(property = "debtMoney", column = "debt_money"),
            @Result(property = "currencyAdd", column = "currency_add"),
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "debtCategory", column = "debt_category"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "selfAccount", column = "self_account"),
            @Result(property = "groupAccount", column = "group_account"),
            @Result(property = "vipNumber", column = "vip_number"),
            @Result(property = "userId", column = "user_id"),
    })
    List<DebtPay> getByDateUserCurrency(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String curr);

    /*币种，时间段*/
    @Select("select * from debt_pay where done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency}")
    @Results(value = {
            @Result(property = "paySerial", column = "pay_serial"),
            @Result(property = "checkOutSerial", column = "check_out_serial"),
            @Result(property = "debtMoney", column = "debt_money"),
            @Result(property = "currencyAdd", column = "currency_add"),
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "debtCategory", column = "debt_category"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "selfAccount", column = "self_account"),
            @Result(property = "groupAccount", column = "group_account"),
            @Result(property = "vipNumber", column = "vip_number"),
            @Result(property = "userId", column = "user_id"),
    })
    List<DebtPay> getByCurrencyDate(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得消费额
     */
    /*操作员，币种，时间段*/
    @Select("select ifnull(sum(debt_money),0) debtMoney from debt_pay where user_id = #{userId} and done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency}")
    @ResultType(Double.class)
    Double getDebtMoneyByDateUserCurrency(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String curr);

    /*币种，时间段*/
    @Select("select ifnull(sum(debt_money),0) debtMoney from debt_pay where done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency}")
    @ResultType(Double.class)
    Double getDebtMoneyByCurrencyDate(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*操作员，时间段，有效币种*/
    @Select("select sum(debt_money) debtMoney from debt_pay LEFT JOIN currency ON debt_pay.currency=currency.currency where user_id = #{userId} and done_time > #{beginTime} and done_time< #{endTime} and pay_total=true")
    @ResultType(Double.class)
    Double getDebtMoneyByDateUser(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段，有效币种*/
    @Select("select sum(debt_money) debtMoney from debt_pay LEFT JOIN currency ON debt_pay.currency=currency.currency where done_time > #{beginTime} and done_time< #{endTime} and pay_total=true")
    @ResultType(Double.class)
    Double getDebtMoneyByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}
