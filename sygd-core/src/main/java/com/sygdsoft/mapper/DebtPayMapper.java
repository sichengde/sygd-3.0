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
    @SelectProvider(type = DebtPaySql.class,method = "getList")
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
    List<DebtPay> getList(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("orderByList") String orderByList);


    /**
     * 获得消费额
     */
    @SelectProvider(type = DebtPaySql.class,method = "getDebtMoney")
    @ResultType(Double.class)
    Double getDebtMoney(@Param("userId") String userId,@Param("currency") String currency,@Param("payTotal") Boolean payTotal, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
