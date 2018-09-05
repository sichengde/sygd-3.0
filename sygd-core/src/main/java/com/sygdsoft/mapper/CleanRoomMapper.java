package com.sygdsoft.mapper;

import com.sygdsoft.model.CleanRoom;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-29.
 */
public interface CleanRoomMapper extends MyMapper<CleanRoom> {
    /**
     * 统计各个房扫工作情况
     */
    @Select("SELECT ifnull(sum(num),0) num,ifnull(user_id,'未指定') userId,category FROM clean_room where do_time>#{beginTime} and do_time<#{endTime} GROUP BY userId,category")
    List<CleanRoom> getSumNumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
