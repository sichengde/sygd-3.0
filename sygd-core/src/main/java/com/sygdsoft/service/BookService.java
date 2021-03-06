package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.BookMapper;
import com.sygdsoft.model.Book;
import com.sygdsoft.model.BookHistory;
import com.sygdsoft.model.BookRoom;
import com.sygdsoft.model.BookRoomCategory;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-13.
 */
@Service
@SzMapper(id = "book")
public class BookService extends BaseService<Book>{
    public String cancel="失效";
    public String active="有效";
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookRoomService bookRoomService;
    @Autowired
    BookRoomCategoryService bookRoomCategoryService;
    @Autowired
    Util util;
    @Autowired
    UserLogService userLogService;
    @Autowired
    BookHistoryService bookHistoryService;

    /**
     * 获得日期内订金总和
     */
    public Double getTotalSubscription(Date beginTime,Date endTime){
        return bookMapper.getTotalSubscription(beginTime, endTime);
    }

    /**
     * 更新过期订单（设置为失效）
     */
    public List<Book> getExpired(){
        return bookMapper.getExpired();
    }

    /**
     * 补交订金
     */
    public void addSubscription(String bookSerial, Double subscription){
        bookMapper.addSubscription(bookSerial, subscription);
    }
    /**
     * 根据房号查找订单
     */
    public List<Book> getBookByRoomId(String roomId){
        return bookMapper.getBookByRoomId(roomId);
    }

    /**
     * 删除订单（带预定房号，预定房类表一起删除）
     */
    public String bookDelete(List<Book> bookList) throws Exception {
        /*失效订单保留一天，第二天转移到历史订单中*/
        List<BookHistory> bookHistoryList = new ArrayList<>();
        for (Book book : bookList) {
            bookHistoryList.add(new BookHistory(book));
        }
        /*过期订单,已开房数=总预定房数的订单失效，当天夜审设置为失效的订单可以保存到第二天*/
        bookHistoryService.add(bookHistoryList);
        String bookLogString = "";
        if(bookList.size()>0) {
        /*删除子表*/
            Query query1 = new Query();
            List<BookRoom> bookRoomList = new ArrayList<>();
            List<BookRoomCategory> bookRoomCategoryList = new ArrayList<>();
            for (Book book : bookList) {
                query1.setCondition("book_serial=" + util.wrapWithBrackets(String.valueOf(book.getBookSerial())));
                bookRoomList.addAll(bookRoomService.get(query1));
                bookRoomCategoryList.addAll(bookRoomCategoryService.get(query1));
                bookLogString += book.getBookSerial() + ":" + book.getName() + ",";
            }
            delete(bookList);
            bookRoomService.delete(bookRoomList);
            bookRoomCategoryService.delete(bookRoomCategoryList);
        }
        return bookLogString;
    }
}














