package com.sygdsoft.mapper;

import com.sygdsoft.model.SaunaIn;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public interface SaunaInMapper extends MyMapper<SaunaIn> {
    /**
     * 根据时间段获取账单总数
     */
    @Select("select count(*) from sauna_in where do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Integer.class)
    Integer getCountByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
    /**
     * 给账单加个金额
     */
    @Update("update sauna_in set total_price = total_price+#{consume} where ring=#{ring}")
    void addConsume(@Param("ring") String ring,@Param("consume") Double consume);
}
