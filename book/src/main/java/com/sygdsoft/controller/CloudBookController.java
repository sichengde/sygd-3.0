package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@RestController
public class CloudBookController {
    @Autowired
    CloudBookService cloudBookService;
    @Autowired
    BookService bookService;
    @Autowired
    BookRoomCategoryService bookRoomCategoryService;
    @Autowired
    BookMoneyService bookMoneyService;
    @Autowired
    SerialService serialService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "cloudBookAdd")
    @Transactional(rollbackFor = Exception.class)
    public void cloudBookAdd(@RequestBody CloudBook cloudBook) throws Exception {
        cloudBookService.add(cloudBook);
        /*提取信息*/
        String bookSerial = serialService.setBookSerial();
        /*设置时间*/
        timeService.setNow();
        /*生成预定信息和预定房间信息*/
        Book book = new Book(cloudBook);
        BookRoomCategory bookRoomCategory=new BookRoomCategory();
        bookRoomCategory.setBookSerial(bookSerial);
        bookRoomCategory.setNum(book.getTotalRoom());
        bookRoomCategory.setPrice(cloudBook.getPrice());
        bookRoomCategory.setRoomCategory(cloudBook.getRoomCategory());
        List<BookRoomCategory> bookRoomCategoryList = new ArrayList<>();
        bookRoomCategoryList.add(bookRoomCategory);
        book.setBookSerial(bookSerial);
        book.setDoTime(timeService.getNow());
        /*如果有订金的话，生成预订订金信息*/
        if (book.getSubscription() != null) {
            BookMoney bookMoney = new BookMoney();
            bookMoney.setBookSerial(book.getBookSerial());
            bookMoney.setDoTime(timeService.getNow());
            bookMoney.setSubscription(book.getSubscription());
            bookMoney.setCurrency(book.getCurrency());
            bookMoneyService.add(bookMoney);
        }
        bookService.add(book);
        bookRoomCategoryService.add(bookRoomCategoryList);
        userLogService.addUserLog(book.getProtocol()+"网络预定:" + bookSerial, userLogService.reception, userLogService.newBook,bookSerial);
    }

    @RequestMapping(value = "cloudBookDelete")
    @Transactional(rollbackFor = Exception.class)
    public void cloudBookDelete(@RequestBody List<CloudBook> cloudBookList) throws Exception {
        cloudBookService.delete(cloudBookList);
    }

    @RequestMapping(value = "cloudBookGet")
    public List<CloudBook> cloudBookGet(@RequestBody Query query) throws Exception {
        return cloudBookService.get(query);
    }

    @RequestMapping(value = "cloudBookUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void cloudBookUpdate(@RequestBody List<CloudBook> cloudBookList) throws Exception {
        if (cloudBookList.size() > 1) {
            if (cloudBookList.get(0).getId().equals(cloudBookList.get(cloudBookList.size() / 2).getId())) {
                cloudBookService.update(cloudBookList.subList(0, cloudBookList.size() / 2));
                return;
            }
        }
        cloudBookService.update(cloudBookList);
    }
}
