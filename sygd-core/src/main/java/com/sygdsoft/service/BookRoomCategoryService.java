package com.sygdsoft.service;

import com.sygdsoft.mapper.BookRoomCategoryMapper;
import com.sygdsoft.mapper.BookRoomMapper;
import com.sygdsoft.model.BookRoomCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
@Service
@SzMapper(id = "bookRoomCategory")
public class BookRoomCategoryService extends BaseService<BookRoomCategory>{
    @Autowired
    BookRoomCategoryMapper bookRoomCategoryMapper;
    /**
     * 根据订单号删除
     */
    public void deleteBySerial(String bookSerial) {
        bookRoomCategoryMapper.deleteBySerial(bookSerial);
    }

    /**
     * 查找时间段内冲突的订单-返回房类和数量
     */
    public List<BookRoomCategory> getViolenceRoomCategory(Date beginTime, Date endTime){
        return bookRoomCategoryMapper.getViolenceRoomCategory(beginTime, endTime);
    }
}
