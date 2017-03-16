package com.sygdsoft.mapper;

import com.sygdsoft.model.DeskDetail;
import com.sygdsoft.sqlProvider.DeskDetailSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
public interface DeskDetailMapper extends MyMapper<DeskDetail>{
    @SelectProvider(type = DeskDetailSql.class,method = "getList")
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
    List<DeskDetail> getList(@Param("desk") String desk, @Param("pointOfSale") String pointOfSale, @Param("orderByList") String orderByList);
}
