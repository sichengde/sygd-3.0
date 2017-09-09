package com.sygdsoft.mapper;

import com.sygdsoft.model.SaunaPay;
import com.sygdsoft.sqlProvider.SaunaPaySql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public interface SaunaPayMapper extends MyMapper<SaunaPay>{
    /**
     * 获得结算款
     */
    @SelectProvider(type = SaunaPaySql.class,method = "getPayMoney")
    @ResultType(Double.class)
    Double getPayMoney(@Param("userId") String userId, @Param("currency") String currency,  @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
