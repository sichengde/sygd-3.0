package com.sygdsoft.mapper;

import com.sygdsoft.model.Book;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface BookMapper extends MyMapper<Book> {
    /**
     * 获取当日的预定订金数
     */
    @Select("select sum(subscription) subscription from book where do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(Double.class)
    Double getTotalSubscription(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);

    /**
     * 过期订单状态设置为失效
     */
    @Update("UPDATE book set state='失效' WHERE ifnull(remain_time,reach_time+INTERVAL 2 DAY) <now() or total_room=booked_room")
    void updateExpired();

    /**
     * 补交订金
     */
    @Update("update book set subscription=ifnull(subscription,0)+#{subscription} where book_serial=#{bookSerial}")
    void addSubscription(@Param("bookSerial") String bookSerial, @Param("subscription") Double subscription);

    /**
     * 根据房号查找订单
     */
    @Select("select * from book LEFT JOIN book_room ON book.book_serial=book_room.book_serial WHERE book_room.room_id=#{roomId} and book_room.opened=0 and book.state='有效' order by reach_time")
    @Results(value = {
            @Result(property = "bookSerial", column = "book_serial"),
            @Result(property = "reachTime", column = "reach_time"),
            @Result(property = "leaveTime", column = "leave_time"),
            @Result(property = "guestSource", column = "guest_source"),
            @Result(property = "roomPriceCategory", column = "room_price_category"),
            @Result(property = "totalRoom", column = "total_room"),
            @Result(property = "bookedRoom", column = "booked_room"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "bookRoom.roomId", column = "room_id"),
            @Result(property = "bookRoom.roomPrice", column = "room_price"),
    })
    List<Book> getBookByRoomId(@Param("roomId") String roomId);
}
