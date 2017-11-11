package com.sygdsoft.mapper;

import com.sygdsoft.model.VipIntegration;
import com.sygdsoft.sqlProvider.VipIntegrationSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;

/**
 * Created by 舒展 on 2016-11-30.
 */
public interface VipIntegrationMapper extends MyMapper<VipIntegration> {
    /**
     * 获取会员存钱金额
     */
    /*操作员，时间段，币种*/
    @SelectProvider(type = VipIntegrationSql.class, method = "getPay")
    @ResultType(Double.class)
    Double getPay(@Param("userId") String userId, @Param("currency") String currency, @Param("pointOfSale") String pointOfSale, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取会员抵用金额
     */
    @SelectProvider(type = VipIntegrationSql.class,method = "getDeserve")
    /*操作员，时间段，币种*/
    @ResultType(Double.class)
    Double getDeserve(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("userId") String userId, @Param("pointOfSale") String pointOfSale, @Param("currency") String currency);
}
