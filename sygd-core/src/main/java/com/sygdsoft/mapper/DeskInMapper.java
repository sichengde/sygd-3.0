package com.sygdsoft.mapper;

import com.sygdsoft.model.DeskIn;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 舒展 on 2016-09-13.
 */
public interface DeskInMapper extends MyMapper<DeskIn>{
    @Update("UPDATE desk_in SET consume = (SELECT ifnull(round(sum(price*desk_detail.num),2),0) FROM desk_detail WHERE desk_detail.desk = #{desk} AND desk_detail.point_of_sale = #{pointOfSale}) WHERE desk=#{desk} and point_of_sale=#{pointOfSale};")
    void updateConsume(@Param("desk") String desk, @Param("pointOfSale") String pointOfSale);
}
