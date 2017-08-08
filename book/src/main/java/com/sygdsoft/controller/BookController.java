package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-05-13.
 */
@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    BookRoomService bookRoomService;
    @Autowired
    BookRoomCategoryService bookRoomCategoryService;
    @Autowired
    SerialService serialService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    Util util;
    @Autowired
    BookMoneyService bookMoneyService;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    OtherParamService otherParamService;

    @RequestMapping(value = "bookGet")
    public List<Book> bookGet(@RequestBody Query query) throws Exception {
        List<Book> bookList = bookService.get(query);
        Query query1 = new Query();
        /*自带预订房间数组*/
        for (Book book : bookList) {
            query1.setCondition("book_serial=" + util.wrapWithBrackets(String.valueOf(book.getBookSerial())));
            book.setBookRoomList(bookRoomService.get(query1));
            book.setBookRoomCategoryList(bookRoomCategoryService.get(query1));
            book.setBookMoneyList(bookMoneyService.getByDeskBookSerial(book.getBookSerial()));
        }
        /*计算押金数据*/
        return bookList;
    }

    @RequestMapping(value = "bookDelete")
    @Transactional
    public void bookDelete(@RequestBody List<Book> bookList) throws Exception {
        String bookLogString=bookService.bookDelete(bookList);
        userLogService.addUserLog("订单删除:" + bookLogString, userLogService.reception, userLogService.deleteBook,bookLogString);
    }

    @RequestMapping(value = "bookRoomGet")
    public List<BookRoom> bookRoomGet(@RequestBody Query query) throws Exception {
        return bookRoomService.get(query);
    }

    /**
     * 剩余的单人房类型预定
     */
    @RequestMapping(value = "getBookAlone")
    public List getBookAlone(@RequestBody String category) throws Exception {
        List<Book> bookAlone=new ArrayList<>();
        List<Book> bookList=this.bookGet(new Query("total_room-booked_room=1"));
        for (Book book : bookList) {
            if(book.getBookRoomCategoryList().size()==1){
                if(book.getBookRoomCategoryList().get(0).getRoomCategory().equals(category)) {
                    bookAlone.add(book);
                }
            }
        }
        return bookAlone;
    }

    /**
     * 补交订金
     */
    @RequestMapping(value = "addSubscription")
    @Transactional(rollbackFor = Exception.class)
    public void bookUpdate(@RequestBody Book book) throws Exception {
        timeService.setNow();
        bookService.addSubscription(book.getBookSerial(), book.getSubscription());
        BookMoney bookMoney = new BookMoney();
        bookMoney.setBookSerial(book.getBookSerial());
        bookMoney.setDoTime(timeService.getNow());
        bookMoney.setSubscription(book.getSubscription());
        bookMoney.setUserId(userService.getCurrentUser());
        bookMoney.setCurrency(book.getCurrency());
        bookMoneyService.add(bookMoney);
        String action;
        if (book.getSubscription() > 0) {
            action = "补交";
        } else {
            action = "退";
        }
        userLogService.addUserLog("预订单" + book.getBookSerial() + action + "订金" + book.getSubscription(), userLogService.reception, userLogService.addSubscription,book.getBookSerial());
    }

    /**
     * 录入一条预定订单
     * 预定房类和预订房间互斥，如果确定房间号则预订房间，否则预定房类
     */
    @RequestMapping(value = "bookInput")
    @Transactional(rollbackFor = Exception.class)
    public Integer bookInput(@RequestBody BookInput bookInput) throws Exception {
        /*提取信息*/
        Book book = bookInput.getBook();
        List<BookRoom> bookRoomList = bookInput.getBookRoomList();
        List<BookRoomCategory> bookRoomCategoryList = bookInput.getBookRoomCategoryList();
        String bookSerial = serialService.setBookSerial();
        /*设置时间*/
        timeService.setNow();
        /*生成预定信息和预定房间信息*/
        book.setBookSerial(bookSerial);
        book.setDoTime(timeService.getNow());
        String bookMessage = "";//预订信息，汇总总房数，或者总房类
        for (BookRoom bookRoom : bookRoomList) {
            bookRoom.setBookSerial(bookSerial);
            bookRoom.setOpened(false);
            bookMessage += bookRoom.getRoomId() + "/";
        }
        /*生成预定房类信息*/
        for (BookRoomCategory bookRoomCategory : bookRoomCategoryList) {
            bookRoomCategory.setBookSerial(bookSerial);
            bookMessage += bookRoomCategory.getRoomCategory() + ":" + bookRoomCategory.getNum() + "/";
        }
        /*如果有订金的话，生成预订订金信息*/
        if (book.getSubscription() != null) {
            BookMoney bookMoney = new BookMoney();
            bookMoney.setBookSerial(book.getBookSerial());
            bookMoney.setDoTime(timeService.getNow());
            bookMoney.setSubscription(book.getSubscription());
            bookMoney.setUserId(userService.getCurrentUser());
            bookMoney.setCurrency(book.getCurrency());
            bookMoneyService.add(bookMoney);
        }
        bookService.add(book);
        bookRoomService.add(bookRoomList);
        bookRoomCategoryService.add(bookRoomCategoryList);
        userLogService.addUserLog("预定录入:" + bookSerial, userLogService.reception, userLogService.newBook,bookSerial);
        /*创建报表，并返回单据号
        * 1.预定名称
        * 2.订房明细
        * 3.预计来期
        * 4.预计离期
        * 5.房价协议
        * 6.单位
        * 7.电话
        * 8.备注
        * 9.订金
        * 10.币种
        * 11.订金大写
        * 12.操作员
        * 13.时间
        * 14.身份证号
        * 15.订单号
        * 16.酒店名称
        * 17.保留时间
        * */
        return reportService.generateReport(null, new String[]{book.getName(), bookMessage, timeService.dateToStringLong(book.getReachTime()), timeService.dateToStringLong(book.getLeaveTime()), book.getProtocol(), book.getCompany(), book.getPhone(), book.getMark(), ifNotNullGetString(book.getSubscription()), book.getCurrency(), util.number2CNMontrayUnit(BigDecimal.valueOf(book.getNotNullSubscription())), userService.getCurrentUser(), timeService.getNowLong(), book.getCardId(), book.getBookSerial(), otherParamService.getValueByName("酒店名称"), timeService.dateToStringLong(book.getRemainTime())}, "bookInput", "pdf");
    }

    /**
     * 补打预订单
     */
    @RequestMapping(value = "printBookAgain")
    public Integer printBookAgain(@RequestBody Book book) {
        List<BookRoom> bookRoomList = book.getBookRoomList();
        List<BookRoomCategory> bookRoomCategoryList = book.getBookRoomCategoryList();
        String bookMessage = "";//预订信息，汇总总房数，或者总房类
        for (BookRoom bookRoom : bookRoomList) {
            bookMessage += bookRoom.getRoomId() + "/";
        }
        /*生成预定房类信息*/
        for (BookRoomCategory bookRoomCategory : bookRoomCategoryList) {
            bookMessage += bookRoomCategory.getRoomCategory() + ":" + bookRoomCategory.getNum() + "/";
        }
        return reportService.generateReport(null, new String[]{book.getName(), bookMessage, timeService.dateToStringLong(book.getReachTime()), timeService.dateToStringLong(book.getLeaveTime()), book.getProtocol(), book.getCompany(), book.getPhone(), book.getMark(), ifNotNullGetString(book.getSubscription()), book.getCurrency(), util.number2CNMontrayUnit(BigDecimal.valueOf(book.getNotNullSubscription())), book.getUserId(), timeService.getNowLong(), book.getCardId(), book.getBookSerial(), otherParamService.getValueByName("酒店名称"), timeService.dateToStringLong(book.getRemainTime())}, "bookInput", "pdf");
    }

    /**
     * 更新预订信息
     */
    @RequestMapping(value = "bookUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void bookUpdate(@RequestBody BookInput bookInput) throws Exception {
        /*提取信息*/
        Book book = bookInput.getBook();
        List<BookRoom> bookRoomList = bookInput.getBookRoomList();
        List<BookRoomCategory> bookRoomCategoryList = bookInput.getBookRoomCategoryList();
        /*设置时间*/
        timeService.setNow();
        /*生成预定信息和预定房间信息*/
        book.setDoTime(timeService.getNow());
        bookService.update(book);
        /*修改的话把新增的设置上序列号*/
        for (BookRoomCategory bookRoomCategory : bookRoomCategoryList) {
            bookRoomCategory.setBookSerial(book.getBookSerial());
        }
        for (BookRoom bookRoom : bookRoomList) {
            bookRoom.setBookSerial(book.getBookSerial());
        }
        /*先把原来的删了，再把新的加进去*/
        bookRoomService.deleteBySerial(book.getBookSerial());
        bookRoomService.add(bookRoomList);
        bookRoomCategoryService.deleteBySerial(book.getBookSerial());
        bookRoomCategoryService.add(bookRoomCategoryList);
        userLogService.addUserLog("预定修改:" + book.getBookSerial(), userLogService.reception, userLogService.updateBook,book.getBookSerial());
    }

    /**
     * 重新安排房间
     */
    @RequestMapping(value = "arrangeRoom")
    @Transactional
    public void arrangeRoom(@RequestBody BookInput bookInput) throws Exception {
        List<BookRoom> bookRoomList = bookInput.getBookRoomList();
        List<BookRoomCategory> bookRoomCategoryList = bookInput.getBookRoomCategoryList();
        String bookSerial = bookRoomCategoryList.get(0).getBookSerial();
        /*生成预定房间信息*/
        for (BookRoom bookRoom : bookRoomList) {
            bookRoom.setBookSerial(bookSerial);
            bookRoom.setOpened(false);
        }
        bookRoomService.add(bookRoomList);
        bookRoomCategoryService.delete(bookRoomCategoryList);
    }
}
