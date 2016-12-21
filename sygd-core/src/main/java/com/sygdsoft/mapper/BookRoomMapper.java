package com.sygdsoft.mapper;

import com.sygdsoft.model.BookRoom;
import com.sygdsoft.sqlProvider.BookRoomSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface BookRoomMapper extends MyMapper<BookRoom> {
    /**
     * 根据订单号删除
     */
    @Delete("delete from book_room where book_serial = #{bookSerial}")
    void deleteBySerial(@Param("bookSerial") String bookSerial);
    /**
     * 把这些房号设置为已开(直接参数传递in里不好使，只能使用拼接字符串)
     */
    @UpdateProvider(type = BookRoomSql.class,method = "setOpened")
    void setOpened(@Param("bookSerial") String bookSerial,@Param("roomIdList") String roomIdList);
}
