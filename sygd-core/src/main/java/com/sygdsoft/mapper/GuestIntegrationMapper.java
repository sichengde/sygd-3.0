package com.sygdsoft.mapper;

import com.sygdsoft.model.CountryGuestRow;
import com.sygdsoft.model.GuestIntegration;
import com.sygdsoft.sqlProvider.GuestIntegrationSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-02-09.
 */
public interface GuestIntegrationMapper extends MyMapper<GuestIntegration>{
    /**
     * 获得总人数
     */
    @Select("SELECT count(*) FROM guest_integration WHERE reach_time>#{beginTime} and reach_time<#{endTime}")
    @ResultType(String.class)
    String getTotalSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取本地客人总数
     */
    @SelectProvider(type =GuestIntegrationSql.class,method ="getLocalGuestSum" )
    @ResultType(Integer.class)
    Integer getLocalGuestSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("firstNum") String firstNum);

    /**
     * 获得外地客人总数
     */
    @SelectProvider(type =GuestIntegrationSql.class,method ="getOtherGuestSum" )
    @ResultType(Integer.class)
    Integer getOtherGuestSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("firstNum") String firstNum);

    /**
     * 根据时间获得列表
     */
    @SelectProvider(type =GuestIntegrationSql.class,method ="getList" )
    @Results(value = {
            @Result(column = "card_id",property = "cardId"),
            @Result(column = "self_account",property = "selfAccount"),
            @Result(column = "reach_time",property = "reachTime"),
            @Result(column = "if_in",property = "ifIn"),
    })
    List<CountryGuestRow> getList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
