package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.report.CompanyDebtReportRow;
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
    @Select("select -sum(debt) from company_debt where debt<0 user_id=#{userId} and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDepositByUser(@Param("userId")String userId, @Param("currency")String currency, @Param("beginTime") Date beginTime, @Param("endTime")Date endTime);

    /*时间，币种*/
    @Select("select -sum(debt) from company_debt where debt<0 and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDepositBy(@Param("currency")String currency, @Param("beginTime") Date beginTime, @Param("endTime")Date endTime);

    /**
     * 根据销售员和时间获得各个单位的总消费额
     */
    @Select("SELECT c.name name,sum(dp.debt_money) consume FROM debt_pay dp LEFT JOIN company c  ON c.name=dp.company WHERE c.sale_man=#{saleMan} and done_time>#{beginTime} and done_time<#{endTime} GROUP BY c.name")
    List<Company> getTotalDebtBySaleManDate(@Param("saleMan")String saleMan, @Param("beginTime")Date beginTime, @Param("endTime")Date endTime);

    /**
     * 根据时间获得各个单位的总挂账款
     */
    @Select("SELECT company,sum(debt) debt FROM company_debt where do_time>#{beginTime} and do_time<#{endTime} GROUP BY company")
    List<CompanyDebtReportRow> getTotalDebtByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);


    /**
     * 获得某个单位某段时间内的挂账款(算进支付的)
     */
    /*正负都算*/
    @Select("select sum(debt) from company_debt where company=#{company} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDebtByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
    /*只算正的*/
    @Select("select sum(debt) from company_debt where company=#{company} and do_time>#{beginTime} and do_time<#{endTime} and debt>0")
    @ResultType(Double.class)
    Double getDebtGenerateByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
    /*只算负的*/
    @Select("select sum(debt) from company_debt where company=#{company} and do_time>#{beginTime} and do_time<#{endTime} and debt<0")
    @ResultType(Double.class)
    Double getDebtBackByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得某个单位某段时间内的房费和其他
     */
    @Select("SELECT sum(roomConsume) roomConsume FROM (SELECT cd.company, if(sum(consume) > cd.debt, cd.debt, sum(consume)) roomConsume  FROM company_debt cd LEFT JOIN debt_history dh ON cd.pay_serial = dh.pay_serial WHERE cd.do_time>#{beginTime} AND cd.do_time<#{endTime} and dh.point_of_sale='房费' GROUP BY cd.id) total WHERE total.company=#{company} GROUP BY total.company")
    CompanyDebtReportRow getRoomConsumeByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
