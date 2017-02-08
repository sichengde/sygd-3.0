package com.sygdsoft.mapper;

import com.sygdsoft.model.GroupIntegration;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-08.
 */
public interface GroupIntegrationMapper extends MyMapper<GroupIntegration>{
    @Select("SELECT count(*) from group_integration where reach_time>#{beginTime} and reach_time<#{endTime}")
    @ResultType(Integer.class)
    Integer getSumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
