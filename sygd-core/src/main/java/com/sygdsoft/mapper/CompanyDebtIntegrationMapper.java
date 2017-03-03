package com.sygdsoft.mapper;

import com.sygdsoft.model.CompanyDebtIntegration;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-02-24.
 */
public interface CompanyDebtIntegrationMapper extends MyMapper<CompanyDebtIntegration>{
    /**
     * 获得某个单位某段时间内的挂账款(算进支付的)
     */
    /*正负都算*/
    @Select("select sum(debt) from company_debt_integration where company=#{company} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDebtByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得某个时间段内某单位挂账明细
     */
    @Select("select * from company_debt_integration where company=#{company} and do_time>#{beginTime} and do_time<#{endTime}")
    @Results(value = {
            @Result(property = "paySerial",column = "pay_serial"),
            @Result(property = "doTime",column = "do_time"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "pointOfSale",column = "point_of_sale"),
            @Result(property = "currentRemain",column = "current_remain"),
    })
    List<CompanyDebtIntegration> getByCompanyDate(@Param("company") String company, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
