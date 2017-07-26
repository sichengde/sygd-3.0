package com.sygdsoft.mapper;

import com.sygdsoft.model.BookRoomCategory;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public interface BookRoomCategoryMapper extends MyMapper<BookRoomCategory> {
    /**
     * 根据订单号删除
     */
    @Delete("delete from book_room_category where book_serial = #{bookSerial}")
    void deleteBySerial(@Param("bookSerial") String bookSerial);

    /**
     * 查找时间段内冲突的订单-返回房类和数量
     */
    @Select("SELECT count(*) num,room_category roomCategory FROM book LEFT JOIN book_room ON book.book_serial = book_room.book_serial LEFT JOIN room ON book_room.room_id = room.room_id WHERE date_format(book.reach_time , '%Y-%m-%d')< date_format(#{endTime}, '%Y-%m-%d') AND date_format(book.leave_time ,'%Y-%m-%d')>date_format(#{beginTime}, '%Y-%m-%d') GROUP BY room_category UNION SELECT count(*) num,room_category roomCategory FROM book LEFT JOIN book_room_category ON book.book_serial = book_room_category.book_serial WHERE date_format(book.reach_time , '%Y-%m-%d')< date_format(#{endTime}, '%Y-%m-%d') AND date_format(book.leave_time ,'%Y-%m-%d')>date_format(#{beginTime}, '%Y-%m-%d') GROUP BY room_category")
    List<BookRoomCategory> getViolenceRoomCategory(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
