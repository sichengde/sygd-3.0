package com.sygdsoft.mapper;

import com.sygdsoft.model.CompanyPay;
import com.sygdsoft.sqlProvider.CompanyPaySql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-23.
 */
public interface CompanyPayMapper extends MyMapper<CompanyPay>{
    /**
     *  获取一段时间内的所有单位支付款，不包括转单位的支付方式
     */
    @SelectProvider(type = CompanyPaySql.class, method = "getDebt")
    CompanyPay getSumPay(@Param("company") String company, @Param("userId") String userId, @Param("currency") String currency, @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

}
