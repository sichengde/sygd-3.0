package com.sygdsoft.mapper;

import com.sygdsoft.model.VipIntegration;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by 舒展 on 2016-11-30.
 */
public interface VipIntegrationMapper extends MyMapper<VipIntegration> {
    /**
     * 获取当日会员存钱金额
     */
    @Select("select sum(pay) pay from vip_integration where do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getTotalPay(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取会员存钱金额
     */
    /*操作员，时间段，币种*/
    @Select("select sum(pay) pay from vip_integration where user_id=#{userId} and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getPayByUser(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段，币种*/
    @Select("select sum(pay) pay from vip_integration where currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getPay(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取会员抵用金额
     */
    /*操作员，时间段，币种*/
    @Select("select sum(deserve) deserve from vip_integration where user_id=#{userId} and currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDeserveByUser(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段，币种*/
    @Select("select sum(deserve) deserve from vip_integration where currency=#{currency} and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getDeserve(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
