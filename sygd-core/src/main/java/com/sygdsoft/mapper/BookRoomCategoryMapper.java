package com.sygdsoft.mapper;

import com.sygdsoft.model.BookRoomCategory;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public interface BookRoomCategoryMapper extends MyMapper<BookRoomCategory>{
    /**
     * 根据订单号删除
     */
    @Delete("delete from book_room_category where book_serial = #{bookSerial}")
    void deleteBySerial(@Param("bookSerial") String bookSerial);
}
