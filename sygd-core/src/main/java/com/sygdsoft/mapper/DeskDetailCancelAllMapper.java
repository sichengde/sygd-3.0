package com.sygdsoft.mapper;

import com.sygdsoft.model.DeskDetailCancelAll;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface DeskDetailCancelAllMapper extends MyMapper<DeskDetailCancelAll> {
    @Select("SELECT * FROM desk_detail_cancel_all WHERE point_of_sale=#{pointOfSale} and done_time=#{doneTime}")
    @Results(value = {
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "foodSign", column = "food_sign"),
            @Result(property = "waitCall", column = "wait_call"),
            @Result(property = "callUp", column = "call_up"),
            @Result(property = "ifDiscount", column = "if_discount"),
            @Result(property = "foodSet", column = "food_set"),
            @Result(property = "cookRoom", column = "cook_room"),
            @Result(property = "storageDone", column = "storage_done"),
    })
    List<DeskDetailCancelAll> getList(@Param("pointOfSale") String pointOfSale, @Param("doneTime") Date doneTime);
}
