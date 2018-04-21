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
 */
public interface DebtHistoryMapper extends MyMapper<DebtHistory> {
    /**
     * 查找总预付
     */
    /*操作员，时间段，币种*/
    @Select("select round(ifnull(sum(a.deposit),0),2) deposit from " +
            "(select sum(deposit) deposit from debt_history where user_id = #{userId} and do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0 " +
            "union all " +
            "select sum(deposit) deposit from debt where user_id = #{userId} and do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0) a ")
    @ResultType(Double.class)
    Double getDepositByUserCurrencyDate(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*币种，时间段*/
    @Select("select round(ifnull(sum(a.deposit),0),2) deposit from " +
            "(select sum(deposit) deposit from debt_history where do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0 " +
            "union all " +
            "select sum(deposit) deposit from debt where do_time > #{beginTime} and do_time< #{endTime} and currency=#{currency} and deposit>0) a ")
    @ResultType(Double.class)
    Double getDepositByCurrencyDate(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 查询退预付
     * （账务历史表中的预付信息加一起就是退的预付，因为默认结账自动退预付）
     */
    @SelectProvider(type = DebtHistorySql.class,method = "getTotalCancelDeposit")
    @ResultType(Double.class)
    Double getTotalCancelDeposit(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @SelectProvider(type = DebtHistorySql.class,method = "getCancelDeposit")
    @Results(value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "selfAccount", column = "self_account"),
            @Result(property = "groupAccount", column = "group_account"),
            @Result(property = "roomId", column = "room_id"),
            @Result(property = "paySerial", column = "pay_serial"),
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "vipNumber", column = "vip_number"),
            @Result(property = "fromRoom", column = "from_room"),
            @Result(property = "companyPaid", column = "company_paid"),
    })
    List<DebtHistory> getCancelDeposit(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 计算该类别在该日期的结算款
     * pointOfSale指的是二级营业部门
     */
    @SelectProvider(type = DebtHistorySql.class, method = "getHistoryConsume")
    @ResultType(Double.class)
    Double getHistoryConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale,@Param("positive") boolean positive);

    @SelectProvider(type = DebtHistorySql.class, method = "getHistoryConsumeRich")
    @ResultType(Double.class)
    Double getHistoryConsumeRich(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSaleList") List<String> pointOfSaleList,@Param("guestSourceList")List<String> guestSourceList,@Param("roomCategoryList") List<String> roomCategoryList);

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
            "(select sum(consume) consume from debt where category=\'冲账\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where category=\'冲账\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalDiscountByUserTimeZone(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段*/
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where category=\'冲账\' and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where category=\'冲账\' and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalDiscountByTimeZone(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 杂单总和
     */
    /*操作员，时间段*/
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where category=\'杂单\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where category=\'杂单\' and user_id=#{userId} and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalAddByUserTimeZone(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段*/
    @Select("select ifnull(sum(a.consume),0) consume from\n" +
            "(select sum(consume) consume from debt where category=\'杂单\'  and do_time>#{beginTime} and do_time<#{endTime}" +
            "UNION\n" +
            " select sum(consume) consume from debt_history where category=\'杂单\' and do_time>#{beginTime} and do_time<#{endTime}) a")
    @ResultType(Double.class)
    Double getTotalAddByTimeZone(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

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
            @Result(property = "companyPaid", column = "company_paid"),
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
    @Delete("delete from debt_history WHERE category IN ('加收房租','小时房费') and pay_serial=#{paySerial}")
    void deleteAddDebt(@Param("paySerial") String paySerial);

    @Update("update debt_history set company_paid=true where id=#{id}")
    void setPaidById(@Param("id") Integer id);

    /**
     * 删除中间结算在debt_history表中产生的临时平账数据
     */
    @Delete("delete from debt_history where category=\'中间结算冲账\'")
    void deleteMiddlePay();

    /**
     * 根据结账序列号获取押金总和
     */
    @Select("SELECT round(sum(deposit),2) FROM debt_history WHERE pay_serial=#{paySerial}")
    @ResultType(value = Double.class)
    Double getTotalDeposit(String paySerial);
}
