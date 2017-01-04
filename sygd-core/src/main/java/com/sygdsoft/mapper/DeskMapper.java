package com.sygdsoft.mapper;

import com.sygdsoft.model.Desk;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 舒展 on 2016-07-14.
 */
public interface DeskMapper extends MyMapper<Desk> {
    /**
     * 获取座位数合计
     * @param pointOfSale
     */
    @Select("select sum(seat) from desk where point_of_sale=#{pointOfSale}")
    @ResultType(Integer.class)
    Integer getTotalSeat(@Param("pointOfSale") String pointOfSale);
}
