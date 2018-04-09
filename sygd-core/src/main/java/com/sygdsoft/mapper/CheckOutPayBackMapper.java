package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckOutPayBack;
import com.sygdsoft.sqlProvider.CheckOutPayBackSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;

public interface CheckOutPayBackMapper extends MyMapper<CheckOutPayBack> {
    @SelectProvider(type = CheckOutPayBackSql.class, method = "getTotal")
    @ResultType(Double.class)
    Double getTotal(@Param("currency") String currency, @Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
