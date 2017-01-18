package com.sygdsoft.mapper;

import com.sygdsoft.model.Debt;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.sqlProvider.DebtHistorySql;
import com.sygdsoft.sqlProvider.DebtSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 * 发生额算法：consume>0 账务历史付款方式不是转房客
 */
public interface DebtHistoryMapper extends MyMapper<DebtHistory> {
    /**
     * 查找总预付
     */
    /*操作员，时间段，币种*/
    @Select("select sum(a.deposit) deposit from " +
            "(select sum(deposit) deposit from debt_history where user_id = #{userId} and do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0 " +
            "union " +
            "select sum(deposit) deposit from debt where user_id = #{userId} and do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0) a ")
    @ResultType(Double.class)
    Double getDepositByUserCurrencyDate(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*币种，时间段*/
    @Select("select sum(a.deposit) deposit from " +
            "(select sum(deposit) deposit from debt_history where do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0 " +
            "union " +
            "select sum(deposit) deposit from debt where do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0) a ")
    @ResultType(Double.class)
    Double getDepositByCurrencyDate(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 查询退预付
     * （账务历史表中的预付信息加一起就是退的预付，因为默认结账自动退预付）
     */
    /*操作员，币种，时间段*/
    @Select("select sum(a.deposit) deposit from " +
            "(select sum(deposit) deposit from debt_history where user_id = #{userId} and done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency} " +
            "union " +
            "select abs(sum(deposit)) deposit from debt where user_id = #{userId} and do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit<0) a")
    @ResultType(Double.class)
    Double getTotalCancelDepositByUserCurrencyDate(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*币种，时间段*/
    @Select("select sum(a.deposit) deposit from " +
            "(select sum(deposit) deposit from debt_history where done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency} " +
            "union " +
            "select abs(sum(deposit)) deposit from debt where do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit<0) a")
    @ResultType(Double.class)
    Double getTotalCancelDepositByCurrencyDate(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 计算该类别在该日期的结算款
     * pointOfSale指的是二级营业部门
     */
    @Select("select ifnull(sum(consume),0) consume from debt_history where consume>0 and done_time>#{beginTime} and done_time<#{endTime} and point_of_sale=#{pointOfSale}")
    @ResultType(Double.class)
    Double getHistoryConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    /**
     * 获得当日的折扣，也就是冲账
     */
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where consume<0 and do_time>#{beginTime} and do_time<#{endTime} \n" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where consume<0 and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalDiscount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 冲账总和
     */
    /*操作员，时间段*/
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where currency=\'冲账\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where currency=\'冲账\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalDiscountByUserTimeZone(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段*/
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where currency=\'冲账\' and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where currency=\'冲账\' and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalDiscountByTimeZone(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 杂单总和
     */
    /*操作员，时间段*/
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where currency=\'杂单\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where currency=\'杂单\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalAddByUserTimeZone(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段*/
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where currency=\'杂单\'  and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where currency=\'杂单\' and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalAddByTimeZone(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得该时间段内的每日客房发生额
     */
    @Select("SELECT calendar.date doTime ,ifnull(b.consume,0) consume FROM calendar LEFT JOIN (select sum(a.consume) consume,a.date from (select sum(consume) consume,date_format(do_time,'%Y-%m-%d') date from debt where consume>0 and date_format(do_time,'%Y-%m-%d')>=date_format(#{beginTime},'%Y-%m-%d') and date_format(do_time,'%Y-%m-%d')<=date_format(#{endTime},'%Y-%m-%d') GROUP BY date UNION select sum(consume) consume,date_format(do_time,'%Y-%m-%d') date from debt_history where consume>0 and date_format(do_time,'%Y-%m-%d')>=date_format(#{beginTime},'%Y-%m-%d') and date_format(do_time,'%Y-%m-%d')<=date_format(#{endTime},'%Y-%m-%d') and pay_serial NOT IN (SELECT pay_serial FROM debt_pay WHERE currency='转房客') GROUP BY date) a GROUP BY a.date) b ON calendar.date=b.date where date_format(calendar.date,'%Y-%m-%d')>=date_format(#{beginTime},'%Y-%m-%d') and date_format(calendar.date,'%Y-%m-%d')<=date_format(#{endTime},'%Y-%m-%d') ORDER BY doTime")
    List<DebtHistory> getConsumePerDay(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 计算该营业部门在该单子下的销售情况
     */
    @SelectProvider(type = DebtHistorySql.class, method = "getTotalConsumeByPointOfSaleAndSerial")
    @ResultType(Double.class)
    Double getTotalConsumeByPointOfSaleAndSerial(@Param("pointOfSale") String pointOfSale, @Param("serial") String serial);

    /**
     * 查找除了加收房租和小时房租之外的账务，根据结账序列号，主要用于叫回账单
     */
    @Select("SELECT * FROM debt_history WHERE category NOT IN ('加收房租','小时房租') and pay_serial=#{paySerial}")
    @Results(value = {
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "selfAccount", column = "self_account"),
            @Result(property = "groupAccount", column = "group_account"),
            @Result(property = "roomId", column = "room_id"),
            @Result(property = "paySerial", column = "pay_serial"),
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "vipNumber", column = "vip_number"),
            @Result(property = "fromRoom", column = "from_room"),
    })
    List<DebtHistory> getListExcludeAddDebt(@Param("paySerial") String paySerial);

    /**
     * 根据离店序列号查找
     */
    @Select("SELECT debt_history.do_time doTime," +
            "debt_history.point_of_sale pointOfSale," +
            "debt_history.self_account selfAccount," +
            "debt_history.group_account groupAccount," +
            "debt_history.room_id roomId," +
            "debt_history.pay_serial paySerial," +
            "debt_history.done_time doneTime," +
            "debt_history.user_id userId," +
            "debt_history.vip_number vipNumber," +
            "debt_history.from_room fromRoom," +
            "debt_history.description description," +
            "debt_history.consume consume," +
            "debt_history.deposit deposit," +
            "debt_history.currency currency," +
            "debt_history.remark remark," +
            "debt_history.bed bed" +
            " FROM debt_pay LEFT JOIN debt_history on debt_pay.pay_serial=debt_history.pay_serial WHERE debt_pay.check_out_serial=#{checkOutSerial} and debt_history.id is not null")
    @Results(value = {
            @Result(property = "doTime", column = "debt_history.do_time"),
            @Result(property = "pointOfSale", column = "debt_history.point_of_sale"),
            @Result(property = "selfAccount", column = "debt_history.self_account"),
            @Result(property = "groupAccount", column = "debt_history.group_account"),
            @Result(property = "roomId", column = "debt_history.room_id"),
            @Result(property = "paySerial", column = "debt_history.pay_serial"),
            @Result(property = "doneTime", column = "debt_history.done_time"),
            @Result(property = "userId", column = "debt_history.user_id"),
            @Result(property = "vipNumber", column = "debt_history.vip_number"),
            @Result(property = "fromRoom", column = "debt_history.from_room"),
            @Result(property = "description", column = "debt_history.description"),
            @Result(property = "consume", column = "debt_history.consume"),
            @Result(property = "deposit", column = "debt_history.deposit"),
            @Result(property = "currency", column = "debt_history.currency"),
            @Result(property = "remark", column = "debt_history.remark"),
            @Result(property = "bed", column = "debt_history.bed"),
    })
    List<DebtHistory> debtHistoryGetByCheckOutSerial(@Param("checkOutSerial") String checkOutSerial);

    /**
     * 获得加收房租和小时房租的账务，根据结账序列号，主要用于叫回账单
     */
    @Select("select sum(consume) from debt_history WHERE category IN ('加收房租','小时房租') and pay_serial=#{paySerial}")
    @ResultType(Double.class)
    Double selectTotalConsumeDebtAdd(@Param("paySerial") String paySerial);

    /**
     * 删除加收房租和小时房租的账务，根据结账序列号，主要用于叫回账单
     */
    @Delete("delete from debt_history WHERE category IN ('加收房租','小时房租') and pay_serial=#{paySerial}")
    void deleteAddDebt(@Param("paySerial") String paySerial);
}
