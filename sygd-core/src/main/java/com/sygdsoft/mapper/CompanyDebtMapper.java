package com.sygdsoft.mapper;

import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

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

    @Select("SELECT c.name,sum(dp.debt_money) debtMoney FROM debt_pay dp LEFT JOIN company c  ON c.name=dp.company WHERE c.sale_man=#{saleMan} and done_time>#{beginTime} and done_time<#{endTime} GROUP BY c.name")
    List<Company> getTotalDebtBySaleManDate(@Param("saleMan")String saleMan, @Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
}
