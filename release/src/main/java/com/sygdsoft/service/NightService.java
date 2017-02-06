package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.CheckInMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-11-07.
 */
@Service
public class NightService {
    @Autowired
    Night night;
    @Autowired
    DebtService debtService;
    @Autowired
    CheckInMapper checkInMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    BookService bookService;
    @Autowired
    BookHistoryService bookHistoryService;
    @Autowired
    RoomService roomService;
    @Autowired
    SerialService serialService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    DeskBookService deskBookService;
    @Autowired
    DeskBookHistoryService deskBookHistoryService;
    @Autowired
    RoomPriceService roomPriceService;
    @Autowired
    Util util;
    @Autowired
    ReportService reportService;

    @Transactional(rollbackFor = Exception.class)
    public void nightActionLogic()throws Exception{
        timeService.setNow();
        /*清除所有的当日锁房*/
        List<Room> roomList = roomService.get(null);
        for (Room room : roomList) {
            room.setTodayLock(null);
        }
        roomService.update(roomList);
        /*收房租,房态设置为脏房*/
        List<CheckIn> checkInList = checkInMapper.selectAll();
        for (CheckIn checkIn : checkInList) {
            if (checkIn.getRoomPriceCategory().equals(roomPriceService.hour)) {//小时房夜审自动转换为日租房
                checkIn.setRoomPriceCategory(roomPriceService.day);
            }
            /*如果是当日来的凌晨房，则不加收房费*/
            Debt debt = new Debt();
            debt.setPointOfSale(pointOfSaleService.FF);
            debt.setDescription("过夜审加收房费");
            debt.setConsume(checkIn.getFinalRoomPrice());
            debt.setCurrency("挂账");
            debt.setRoomId(checkIn.getRoomId());
            debt.setProtocol(checkIn.getProtocol());
            debt.setCategory(debtService.allDayPrice);
            debtService.addDebt(debt);
            roomService.dirtyRoom(checkIn.getRoomId());
        }
        /*清除团队信息（针对团队房按照单人结算，团队信息会被遗留下来）*/
        List<CheckInGroup> checkInGroupList = checkInGroupService.get(null);
        List<CheckInGroup> deleteList = new ArrayList<>();
        for (CheckInGroup checkInGroup : checkInGroupList) {
            if (checkInGroup.getTotalRoom() == 0) {
                deleteList.add(checkInGroup);
            }
        }
        checkInGroupService.delete(deleteList);
        /*失效订单保留一天，第二天转移到历史订单中*/
        List<Book> bookList = bookService.get(new Query("state=\'失效\'"));
        List<BookHistory> bookHistoryList = new ArrayList<>();
        for (Book book : bookList) {
            bookHistoryList.add(new BookHistory(book));
        }
        bookService.bookDelete(bookList);
        /*过期订单,已开房数=总预定房数的订单失效，当天夜审设置为失效的订单可以保存到第二天*/
        bookService.updateExpired();
        bookHistoryService.add(bookHistoryList);
        /*餐饮订单过期的直接迁移至历史，没有有效无效之说，但必须是没有订金的*/
        List<DeskBook> deskBookList = deskBookService.get(new Query("remain_time<" + util.wrapWithBrackets(timeService.dateToStringLong(timeService.getNow()))+" and ifnull(subscription,0)>0"));
        List<DeskBookHistory> deskBookHistoryList = new ArrayList<>();
        for (DeskBook deskBook : deskBookList) {
            deskBookHistoryList.add(new DeskBookHistory(deskBook));
        }
        deskBookService.delete(deskBookList);
        deskBookHistoryService.add(deskBookHistoryList);
        /*设置最近一次夜审时间*/
        otherParamService.updateValueByName("上次夜审", timeService.getNowLong());
        /*设置账务时间加一天*/
        otherParamService.updateValueByName("账务日期",timeService.dateToStringShort(timeService.addDay(otherParamService.getValueByName("账务日期"),1)));
        /*生成房类统计报表*/
        /*清除临时报表缓存*/
        reportService.clear();
    }
}
