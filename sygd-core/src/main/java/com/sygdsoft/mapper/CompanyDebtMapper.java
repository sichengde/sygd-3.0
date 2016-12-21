package com.sygdsoft.mapper;

import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CompanyDebtMapper extends MyMapper<CompanyDebt> {
    /**
     * 获得单位支付/充值
     */
    /*操作员，时间，币种*/
    @Select("select sum(deposit) from company_debt where user_id=#{userId} and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDepositByUser(@Param("userId")String userId, @Param("currency")String currency, @Param("beginTime") Date beginTime, @Param("endTime")Date endTime);

    /*时间，币种*/
    @Select("select sum(deposit) from company_debt where currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDepositBy(@Param("currency")String currency, @Param("beginTime") Date beginTime, @Param("endTime")Date endTime);
}
