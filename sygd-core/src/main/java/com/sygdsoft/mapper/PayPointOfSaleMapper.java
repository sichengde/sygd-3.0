package com.sygdsoft.mapper;

import com.sygdsoft.model.PayPointOfSale;
import com.sygdsoft.sqlProvider.PayPointOfSaleSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;

public interface PayPointOfSaleMapper extends MyMapper<PayPointOfSale> {
    @SelectProvider(type = PayPointOfSaleSql.class,method = "getDebtMoney")
    @ResultType(Double.class)
    Double getDebtMoney(@Param("pointOfSale") String pointOfSale, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("module")String module);
}
