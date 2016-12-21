package com.sygdsoft.mapper;

import com.sygdsoft.model.BookHistory;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-07-29.
 */
public interface BookHistoryMapper extends MyMapper<BookHistory>{
    /**
     * 获得单位聚合后的订单到达率
     */
    /*根据时间*/
    @Select("SELECT sum(total_room) totalRoom, sum(booked_room) bookedRoom, company FROM book_history where do_time>#{beginTime} and do_time<#{endTime} GROUP BY company")
    List<BookHistory> getSumReachByCompanyDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
    /*全期*/
    @Select("SELECT sum(total_room) totalRoom, sum(booked_room) bookedRoom, company FROM book_history GROUP BY company")
    List<BookHistory> getSumReachByCompany();
}
