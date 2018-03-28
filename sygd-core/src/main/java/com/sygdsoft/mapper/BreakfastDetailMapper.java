package com.sygdsoft.mapper;

import com.sygdsoft.model.BreakfastDetail;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BreakfastDetailMapper extends MyMapper<BreakfastDetail>{
    @Select("")
    int breakfastDetailGetCount(@Param("selfAccount") String selfAccount);
}
