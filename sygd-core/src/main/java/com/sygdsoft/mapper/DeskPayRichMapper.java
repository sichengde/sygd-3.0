package com.sygdsoft.mapper;

import com.sygdsoft.model.DeskPayRich;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by 舒展 on 2017-03-16.
 */
public interface DeskPayRichMapper extends MyMapper<DeskPayRich> {
    @Select("SELECT ifnull(sum(dpr.pay_money),0) FROM desk_pay_rich dpr LEFT JOIN desk d ON dpr.desk=d.name WHERE dpr.currency=#{currency} and dpr.point_of_sale=#{pointOfSale} and d.category=#{category} and dpr.do_time>#{beginTime} and dpr.do_time<#{endTime}")
    @ResultType(Double.class)
    Double getPay(@Param("currency") String currency, @Param("pointOfSale") String pointOfSale, @Param("category") String category, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
