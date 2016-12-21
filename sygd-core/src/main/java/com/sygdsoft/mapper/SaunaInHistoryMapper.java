package com.sygdsoft.mapper;

import com.sygdsoft.model.SaunaInHistory;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public interface SaunaInHistoryMapper extends MyMapper<SaunaInHistory>{
    /**
     * 根据时间段获取账单总数
     */
    @Select("select count(*) from sauna_in_history where done_time>#{beginTime} and done_time<#{endTime}")
    @ResultType(Integer.class)
    Integer getCountByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
