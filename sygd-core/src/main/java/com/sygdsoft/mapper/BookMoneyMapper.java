package com.sygdsoft.mapper;

import com.sygdsoft.model.BookMoney;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-08-24.
 */
public interface BookMoneyMapper extends MyMapper<BookMoney> {
    /**
     * 订金总数
     */
    /*操作员，币种，时间段*/
    @Select("select sum(subscription) from book_money where user_id = #{userId} and do_time > #{beginTime} and do_time< #{endTime} and subscription>0 and currency=#{currency}")
    @ResultType(Double.class)
    Double getBookSubscriptionByUser(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*币种，时间段*/
    @Select("select sum(subscription) from book_money where do_time > #{beginTime} and do_time< #{endTime} and subscription>0 and currency=#{currency}")
    @ResultType(Double.class)
    Double getBookSubscription(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 退订金总数
     */
    /*操作员，币种，时间段*/
    @Select("select abs(sum(subscription)) from book_money where user_id = #{userId} and do_time > #{beginTime} and do_time< #{endTime} and subscription<0 and currency=#{currency}")
    @ResultType(Double.class)
    Double getCancelBookSubscriptionByUser(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*币种，时间段*/
    @Select("select abs(sum(subscription)) from book_money where do_time > #{beginTime} and do_time< #{endTime} and subscription<0 and currency=#{currency}")
    @ResultType(Double.class)
    Double getCancelBookSubscription(@Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取聚合list
     */
    @Select("SELECT sum(subscription) subscription,currency FROM book_money WHERE book_serial=#{bookSerial} GROUP BY currency")
    List<BookMoney> getByDeskBookSerial(@Param("bookSerial") String bookSerial);
}
