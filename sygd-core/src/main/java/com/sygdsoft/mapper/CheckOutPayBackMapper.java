package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckOutPayBack;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface CheckOutPayBackMapper extends MyMapper<CheckOutPayBack> {
    @Select("SELECT sum(money) from check_out_pay_back WHERE done_time>#{beginTime} AND done_time<#{endTime} AND currency=#{currency} GROUP BY currency")
    @ResultType(Double.class)
    Double getTotal(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String currency);

    @Select("SELECT sum(money) from check_out_pay_back WHERE done_time>#{beginTime} AND done_time<#{endTime} AND currency=#{currency} and user_id=#{userId} GROUP BY currency")
    @ResultType(Double.class)
    Double getTotal(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String currency,@Param("userId")String userId);
}
