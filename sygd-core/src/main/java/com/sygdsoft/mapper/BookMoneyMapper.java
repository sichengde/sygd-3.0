package com.sygdsoft.mapper;

import com.sygdsoft.model.BookMoney;
import com.sygdsoft.sqlProvider.BookMoneySql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

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
    @SelectProvider(type = BookMoneySql.class,method = "getMoney")
    @ResultType(Double.class)
    Double getMoney(@Param("userId") String userId, @Param("currency") String currency, @Param("negative") Boolean negative, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取聚合list
     */
    @Select("SELECT sum(subscription) subscription,currency FROM book_money WHERE book_serial=#{bookSerial} GROUP BY currency")
    List<BookMoney> getByDeskBookSerial(@Param("bookSerial") String bookSerial);
}
