package com.sygdsoft.mapper;

import com.sygdsoft.model.DeskBook;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
public interface DeskBookMapper extends MyMapper<DeskBook> {
    /**
     * 补交订金
     */
    @Update("update desk_book set subscription=ifnull(subscription,0)+#{subscription} where desk_book_serial=#{deskBookSerial}")
    void addSubscription(@Param("deskBookSerial") String deskBookSerial, @Param("subscription") Double subscription);

    /**
     * 根据桌号查找最近三条订单
     */
    @Select("select * from desk_book where instr(desk,#{desk}) LIMIT 0,3")
    @Results(value = {
            @Result(property = "reachTime",column = "reach_time"),
            @Result(property = "remainTime",column = "remain_time"),
            @Result(property = "guestName",column = "guest_name"),
            @Result(property = "pointOfSale",column = "point_of_sale"),
            @Result(property = "deskBookSerial",column = "desk_book_serial")
    })
    List<DeskBook> getLastThreeByDesk(@Param("desk") String desk);
}
