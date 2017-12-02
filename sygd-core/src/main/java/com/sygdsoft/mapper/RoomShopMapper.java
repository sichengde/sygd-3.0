package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomShop;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-14.
 */
public interface RoomShopMapper extends MyMapper<RoomShop> {
    @Select("SELECT ifnull(category,'未定义') FROM room_shop WHERE item=#{name} limit 1;")
    @ResultType(String.class)
    String getCategoryByName(@Param("name") String name);
}
