package com.sygdsoft.mapper;

import com.sygdsoft.model.FoodSet;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 舒展 on 2016-11-02.
 */
public interface FoodSetMapper extends MyMapper<FoodSet> {
    @Delete("delete from food_set where set_name=#{name}")
    void deleteBySetName(@Param("name") String name);
}
