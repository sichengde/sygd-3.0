package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckOut;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CheckOutMapper extends MyMapper<CheckOut> {

    @Select("SELECT (deposit-consume) deposit ,self_account selfAccount FROM check_out WHERE check_out_time > #{beginTime} and check_out_time < #{endTime}")
    List<CheckOut> getPayBack(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
