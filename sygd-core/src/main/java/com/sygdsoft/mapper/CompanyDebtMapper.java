package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.report.CompanyDebtReportRow;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.sqlProvider.CompanyDebtSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CompanyDebtMapper extends MyMapper<CompanyDebt> {
    /**
     * 根据销售员和时间获得各个单位的总消费额
     */
    @Select("SELECT c.name name,sale_man saleMan,sum(dp.debt_money) consume FROM debt_pay dp LEFT JOIN company c  ON c.name=dp.company WHERE c.name is not null and done_time>#{beginTime} and done_time<#{endTime} GROUP BY c.name,c.sale_man")
    List<Company> getTotalDebtBySaleManDate(@Param("saleMan") String saleMan, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得某个单位某段时间内的挂账款(算进支付的)
     */
    /*正负都算*/
    @Select("select round(ifnull(sum(debt),0),2) from company_debt where company=#{company} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDebtByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得某个单位某段时间内的房费和其他(暂时弃用)
     */
    @Select("SELECT sum(roomConsume) roomConsume FROM (SELECT cd.company, if(sum(consume) > cd.debt, cd.debt, sum(consume)) roomConsume  FROM company_debt cd LEFT JOIN debt_history dh ON cd.pay_serial = dh.pay_serial WHERE cd.do_time>#{beginTime} AND cd.do_time<#{endTime} and dh.point_of_sale='房费' GROUP BY cd.id) total WHERE total.company=#{company} GROUP BY total.company")
    CompanyDebtReportRow getRoomConsumeByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得某个时间段内某单位挂账明细
     */
    @Select("select * from company_debt where company=#{company} and do_time>#{beginTime} and do_time<#{endTime}")
    @Results(value = {
            @Result(property = "paySerial",column = "pay_serial"),
            @Result(property = "doTime",column = "do_time"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "pointOfSale",column = "point_of_sale"),
            @Result(property = "currentRemain",column = "current_remain"),
    })
    List<CompanyDebt> getByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
    @Select("select * from company_debt where company=#{company} and pay_serial=#{paySerial}")
    @Results(value = {
            @Result(property = "paySerial",column = "pay_serial"),
            @Result(property = "doTime",column = "do_time"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "pointOfSale",column = "point_of_sale"),
            @Result(property = "currentRemain",column = "current_remain"),
    })
    List<CompanyDebt> getByNameSerial(@Param("company") String company, @Param("paySerial") String paySerial);

    @SelectProvider(type = CompanyDebtSql.class,method = "getSumDebtMoney")
    @ResultType(Double.class)
    Double getSumDebtMoney(@Param("beginTime")Date beginTime,@Param("endTime")Date endTime,@Param("pointOfSale") String pointOfSale);

    @Select("select round(ifnull(sum(debt),0),2) from company_debt where ifnull(other_consume,false)=true")
    @ResultType(Double.class)
    Double getSumOtherConsume();
}
