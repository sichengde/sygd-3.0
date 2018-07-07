package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.CheckInGuestMapper;
import com.sygdsoft.mapper.CheckInMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    RoomStateReportService roomStateReportService;
    @Autowired
    RoomSnapshotService roomSnapshotService;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    NightDateService nightDateService;
    @Autowired
    GuestSnapshotService guestSnapshotService;
    @Autowired
    CheckInGuestMapper checkInGuestMapper;
    @Autowired
    GuestIntegrationService guestIntegrationService;
    @Autowired
    CheckInSnapshotService checkInSnapshotService;
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    OldSystemReportService oldSystemReportService;

    @Transactional(rollbackFor = Exception.class)
    public void nightActionLogic() throws Exception {
        timeService.setNow();
        /*清除所有的当日锁房*/
        Query query = new Query();
        query.setOrderByList(new String[]{"category"});
        List<Room> roomList = roomService.get(query);
        for (Room room : roomList) {
            room.setTodayLock(null);
        }
        roomService.update(roomList);
        /*收房租,房态设置为脏房*/
        List<CheckIn> checkInList = checkInService.get(new Query("if_room = true"));
        for (CheckIn checkIn : checkInList) {
            if (checkIn.getRoomPriceCategory().equals(roomPriceService.hour)) {//小时房夜审不加房租
                continue;
            }
            /*空房费设置为0*/
            if (checkIn.getFinalRoomPrice() == null) {
                checkIn.setFinalRoomPrice(0.0);
                checkInService.update(checkIn);
            }
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
        /*获取过期订单，并删除*/
        List<Book> bookList = bookService.getExpired();
        bookService.bookDelete(bookList);
        /*餐饮订单过期的直接迁移至历史，没有有效无效之说，但必须是没有订金的*/
        List<DeskBook> deskBookList = deskBookService.get(new Query("remain_time<" + util.wrapWithBrackets(timeService.dateToStringLong(timeService.getNow())) + " and ifnull(subscription,0)>0"));
        List<DeskBookHistory> deskBookHistoryList = new ArrayList<>();
        for (DeskBook deskBook : deskBookList) {
            deskBookHistoryList.add(new DeskBookHistory(deskBook));
        }
        deskBookService.delete(deskBookList);
        deskBookHistoryService.add(deskBookHistoryList);
        /*生成房类统计报表*/
        Date debtDate = timeService.stringToDateShort(otherParamService.getValueByName("账务日期"));
        Date beginDateTime = nightDateService.getByDate(debtDate);
        Date endDateTime = timeService.getNow();
        roomStateReportService.deleteByDate(debtDate);//先删除该日期的（如果有的话）
        roomSnapshotService.deleteByDate(debtDate);//先删除该日期的（如果有的话）
        guestSnapshotService.deleteByDate(debtDate);//先删除该日期的（如果有的话）
        checkInSnapshotService.deleteByDate(debtDate);//先删除该日期的（如果有的话）
        oldSystemReportService.generateAllHotelReport(beginDateTime, endDateTime, debtDate);
        List<RoomStateReport> roomStateReportList = new ArrayList<>();
        List<RoomSnapshot> roomSnapshotList = new ArrayList<>();
        String oldCategory = null;
        Integer total = 0;
        Integer totalReal = 0;
        Integer empty = 0;
        Integer repair = 0;
        Integer self = 0;
        Integer backUp = 0;
        Integer rent = 0;
        Integer hourRoom = 0;
        Integer addRoom = 0;
        Integer nightRoom = 0;
        Integer allDayRoom = 0;
        Double allDayRoomConsume = 0.0;
        Double hourRoomConsume = 0.0;
        Double addRoomConsume = 0.0;
        Double nightRoomConsume = 0.0;
        for (Room room : roomList) {//roomList在之前已经根据房类排列好了
            RoomSnapshot roomSnapshot = new RoomSnapshot();
            /*新建一行*/
            if (!room.getCategory().equals(oldCategory)) {
                if (oldCategory != null) {//插入之前的
                    /*计算重复出租*/
                    /*计算总计房租*/
                    RoomStateReport roomStateReport = new RoomStateReport(oldCategory, total, totalReal, empty, repair, self, backUp, rent, allDayRoom, hourRoom, addRoom, nightRoom, allDayRoomConsume, hourRoomConsume, addRoomConsume, nightRoomConsume, debtDate);
                    roomStateReportList.add(roomStateReport);
                    total = 0;
                    totalReal = 0;
                    empty = 0;
                    repair = 0;
                    self = 0;
                    backUp = 0;
                    rent = 0;
                    hourRoom = 0;
                    addRoom = 0;
                    nightRoom = 0;
                    allDayRoom = 0;
                    allDayRoomConsume = 0.0;
                    hourRoomConsume = 0.0;
                    addRoomConsume = 0.0;
                    nightRoomConsume = 0.0;
                }
                oldCategory = room.getCategory();
            }
            roomSnapshot.setRoomId(room.getRoomId());
            roomSnapshot.setReportTime(debtDate);
            roomSnapshot.setCategory(room.getCategory());
            roomSnapshot.setArea(room.getArea());
            roomSnapshot.setState(room.getState());
            switch (room.getState()) {
                case "可用房":
                    roomSnapshot.setAvailable(true);
                case "走客房":
                    roomSnapshot.setAvailable(true);
                    empty++;
                    roomSnapshot.setEmpty(true);
                    break;
                case "团队房":
                    roomSnapshot.setAvailable(true);
                case "散客房":
                    roomSnapshot.setAvailable(true);
                    /*判断是不是全日房*/
                    CheckIn checkIn = checkInService.getByRoomId(room.getRoomId());
                    if (checkIn.getRoomPriceCategory().equals("日租房")) {
                        allDayRoom++;
                        allDayRoomConsume += checkIn.getFinalRoomPrice();
                        roomSnapshot.setAllDayRoom(true);
                        roomSnapshot.setAllDayRoomConsume(checkIn.getFinalRoomPrice());
                    }
                    rent++;
                    roomSnapshot.setRent(true);
                    roomSnapshot.setCompany(checkIn.getCompany());
                    roomSnapshot.setGuestSource(checkIn.getGuestSource());
                    roomSnapshot.setSelfAccount(checkIn.getSelfAccount());
                    roomSnapshot.setGroupAccount(checkIn.getGroupAccount());
                    if (checkIn.getNotNullFinalRoomPrice() == 0.0) {
                        roomSnapshot.setFree(true);
                    }
                    break;
                case "维修房":
                    repair++;
                    roomSnapshot.setAvailable(false);
                    roomSnapshot.setRepair(true);
                    break;
                case "自用房":
                    self++;
                    roomSnapshot.setAvailable(false);
                    roomSnapshot.setSelf(true);
                    break;
                case "备用房":
                    backUp++;
                    roomSnapshot.setAvailable(false);
                    roomSnapshot.setBackUp(true);
                    break;
            }
            if (room.getNotNullIfRoom()) {
                totalReal++;
                roomSnapshot.setRealRoom(true);
            }
            total++;
            List<DebtIntegration> debtIntegrationList;
            String dateAndRoomCondition = " and room_id=" + util.wrapWithBrackets(room.getRoomId()) + " and do_time > " + util.wrapWithBrackets(timeService.dateToStringLong(beginDateTime)) + " and do_time < " + util.wrapWithBrackets(timeService.dateToStringLong(endDateTime));
            /*计算时间段内的小时房*/
            debtIntegrationList = debtIntegrationService.get(new Query("category=\'小时房费\'" + dateAndRoomCondition));
            for (DebtIntegration debtIntegration : debtIntegrationList) {
                hourRoom++;
                hourRoomConsume += debtIntegration.getConsume();
                roomSnapshot.setHourRoom(true);
                roomSnapshot.setHourRoomConsume(debtIntegration.getConsume());
            }
            /*计算时间段内的加收房*/
            debtIntegrationList = debtIntegrationService.get(new Query("category=\'加收房租\'" + dateAndRoomCondition));
            for (DebtIntegration debtIntegration : debtIntegrationList) {
                addRoom++;
                addRoomConsume += debtIntegration.getConsume();
                roomSnapshot.setAddRoom(true);
                roomSnapshot.setAddRoomConsume(debtIntegration.getConsume());
            }
            /*计算时间段内的凌晨房*/
            debtIntegrationList = debtIntegrationService.get(new Query("category=\'凌晨房费\'" + dateAndRoomCondition));
            for (DebtIntegration debtIntegration : debtIntegrationList) {
                nightRoom++;
                nightRoomConsume += debtIntegration.getConsume();
                roomSnapshot.setNightRoom(true);
                roomSnapshot.setNightRoomConsume(debtIntegration.getConsume());
            }
            roomSnapshotList.add(roomSnapshot);
        }
        /*最后一个插入*/
        RoomStateReport roomStateReport = new RoomStateReport(oldCategory, total, totalReal, empty, repair, self, backUp, rent, allDayRoom, hourRoom, addRoom, nightRoom, allDayRoomConsume, hourRoomConsume, addRoomConsume, nightRoomConsume, debtDate);
        roomStateReportList.add(roomStateReport);
        roomStateReportService.add(roomStateReportList);
        roomSnapshotService.add(roomSnapshotList);
        /*生成当日客人快照*/
        GuestSnapshot guestSnapshot = new GuestSnapshot();
        guestSnapshot.setReportTime(debtDate);
        guestSnapshot.setExist(checkInGuestMapper.selectCount(null));
        guestSnapshot.setCome(guestIntegrationService.getSumNumByDate(debtDate, null, null));
        guestSnapshotService.add(guestSnapshot);
        /*生成在店户籍快照*/
        List<CheckIn> checkInList1 = checkInService.get(null);
        List<CheckInSnapshot> checkInSnapshotList = new ArrayList<>();
        for (CheckIn checkIn : checkInList1) {
            CheckInSnapshot checkInSnapshot = new CheckInSnapshot(checkIn);
            checkInSnapshot.setReportDate(debtDate);
            checkInSnapshot.setGuestName(checkInGuestService.listToStringName(checkInGuestService.getListByRoomId(checkIn.getRoomId())));
            checkInSnapshotList.add(checkInSnapshot);
        }
        checkInSnapshotService.add(checkInSnapshotList);
        /*设置最近一次夜审时间*/
        otherParamService.updateValueByName("上次夜审", timeService.getNowLong());
        /*设置该账务日期的夜审时间*/
        nightDateService.setNightAction(debtDate);
        /*设置账务时间加一天*/
        otherParamService.updateValueByName("账务日期", timeService.dateToStringShort(timeService.addDay(otherParamService.getValueByName("账务日期"), 1)));
        /*清除临时报表缓存*/
        reportService.clear();
    }
}
