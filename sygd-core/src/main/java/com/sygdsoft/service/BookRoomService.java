package com.sygdsoft.service;

import com.sygdsoft.mapper.BookRoomMapper;
import com.sygdsoft.model.BookRoom;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/19 0019.
 */
@Service
@SzMapper(id = "bookRoom")
public class BookRoomService extends BaseService<BookRoom>{
    @Autowired
    BookRoomMapper bookRoomMapper;

    /**
     * 根据订单号删除
     */
    public void deleteBySerial(String bookSerial){
        bookRoomMapper.deleteBySerial(bookSerial);
    }

    /**
     * 把这些房号设置为已开
     */
    public void setOpened(String bookSerial,String roomIdString){
        bookRoomMapper.setOpened(bookSerial, roomIdString);
    }
}
