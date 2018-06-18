package com.sygdsoft.mapper;

import com.sygdsoft.model.BreakfastDetail;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface BreakfastDetailMapper extends MyMapper<BreakfastDetail>{
    @Select("")
    int breakfastDetailGetCount(@Param("selfAccount") String selfAccount);

    @Select("SELECT room_id roomId, count(*) total, min(do_time) doTime, min(user_id) userId FROM breakfast_detail WHERE do_time>#{beginTime} AND do_time<#{endTime} GROUP BY room_id;")
    List<BreakfastDetail> getListGroup(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
