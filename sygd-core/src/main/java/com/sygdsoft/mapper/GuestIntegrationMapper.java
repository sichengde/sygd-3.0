package com.sygdsoft.mapper;

import com.sygdsoft.model.GuestIntegration;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    @Select("SELECT count(*) FROM guest_integration WHERE card_id LIKE #{firstNum} and reach_time>#{beginTime} and reach_time<#{endTime}")
    @ResultType(String.class)
    String getLocalGuestSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("firstNum") String firstNum);

    /**
     * 获得外地客人总数
     */
    @Select("SELECT count(*) FROM guest_integration WHERE card_id not LIKE #{firstNum}  and reach_time>#{beginTime} and reach_time<#{endTime}")
    @ResultType(String.class)
    String getOtherGuestSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("firstNum") String firstNum);
}
