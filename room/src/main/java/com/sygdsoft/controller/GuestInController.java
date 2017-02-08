package com.sygdsoft.controller;

import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by 舒展 on 2016-04-19.
 * 前台开房控制器，包括散客开放，团队开房
 */
@RestController
public class GuestInController {
    @Autowired
    RoomPriceService roomPriceService;
    @Autowired
    SerialService serialService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    TimeService timeService;
    @Autowired
    RoomService roomService;
    @Autowired
    DebtService debtService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    ReportService reportService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    VipService vipService;
    @Autowired
    UserService userService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    Util util;
    @Autowired
    BookService bookService;
    @Autowired
    BookMoneyService bookMoneyService;
    @Autowired
    BookRoomService bookRoomService;
    @Autowired
    ProtocolService protocolService;

    /**
     * 散客开房操作步骤
     * 1：更新房态。2：生成在店户籍。3：生成宾客信息（数组）。4：生成账务（开房押金）。5：生成操作员日志
     */
    @RequestMapping(value = "guestIn", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Integer guestIn(@RequestBody GuestIn guestIn) throws Exception {
        /*获取传递进来的两个对象*/
        /*初始化设置*/
        timeService.setNow();
        /*生成房价协议*/
        this.addProtocol(guestIn);
        /*生成在店户籍*/
        this.checkInProcess(guestIn);
        /*创建账务明细*/
        this.debtProcess(guestIn);
        /*转换房态*/
        this.updateRoomStateGuestOut(guestIn);
        /*生成宾客信息*/
        checkInGuestService.add(guestIn.getCheckInGuestList());
        /*如果有预订信息，则更新预订信息*/
        this.bookProcess(guestIn);
        /*创建操作员日志*/
        this.userLogProcess(guestIn);
        return this.reportProcess(guestIn);
    }

    /**
     * 转换房态
     */
    private void updateRoomStateGuestOut(GuestIn guestIn) {
        if (guestIn.getCheckInGroup() == null) {
            for (CheckIn checkIn : guestIn.getCheckInList()) {
                roomService.updateRoomState(checkIn.getRoomId(), roomService.guest);
            }
        } else {
            for (CheckIn checkIn : guestIn.getCheckInList()) {
                roomService.updateRoomState(checkIn.getRoomId(), roomService.group);
            }
        }
    }

    /**
     * 处理在店户籍，看情况设置自付公付账号
     */
    private void checkInProcess(GuestIn guestIn) throws Exception {
        List<CheckIn> checkInList = guestIn.getCheckInList();
        if (guestIn.getCheckInGroup()!=null) {//团队开房
            /*如果是转入现有团队*/
            if(guestIn.getCheckInGroup().getGroupAccount()!=null){
                serialService.setGroupAccount(guestIn.getCheckInGroup().getGroupAccount());
            }else {
                serialService.setGroupAccount();
            }
            for (CheckIn checkIn : checkInList) {
                /*提取一个公付账号*/
                serialService.setSelfAccount();
                checkIn.setSelfAccount(serialService.getSelfAccount());
                checkIn.setGroupAccount(serialService.getGroupAccount());
            }
        } else {
            for (CheckIn checkIn : checkInList) {
                serialService.setSelfAccount();
                checkIn.setSelfAccount(serialService.getSelfAccount());
            }
        }
        checkInService.add(checkInList);
    }

    private void addProtocol(GuestIn guestIn) throws Exception {
        Protocol protocol = guestIn.getProtocol();
        if (protocol != null) {
            protocol.setId(null);
            protocolService.add(protocol);
        }
    }

    /**
     * 处理账务，押金和凌晨房，冻结会员押金
     */
    private void debtProcess(GuestIn guestIn) throws Exception {
        /*凌晨房判断*/
        String limit = otherParamService.getValueByName("凌晨房时段");
        String nowTime = timeService.getNowTimeString();
        String nightTime = otherParamService.getValueByName("夜审时间");
        if (guestIn.getCheckInGroup() == null) {//散客开房
            CheckIn checkIn = guestIn.getCheckInList().get(0);
            /*创建账务明细*/
            Debt debt = new Debt();
            debt.setDoTime(timeService.getNow());
            debt.setPointOfSale(pointOfSaleService.FF);
            debt.setDeposit(checkIn.getDeposit());
            debt.setCurrency(checkIn.getCurrency());
            debt.setGuestSource(checkIn.getGuestSource());
            debt.setDescription("开房押金录入");
            debt.setSelfAccount(serialService.getSelfAccount());
            debt.setRoomId(checkIn.getRoomId());
            debt.setProtocol(checkIn.getProtocol());
            debt.setUserId(checkIn.getUserId());
            debt.setCategory(debtService.deposit);
            debtService.add(debt);
            if (nowTime.compareTo(limit) < 0 && nowTime.compareTo(nightTime) > 0) {//需要直接产生一笔房费
                debt = new Debt();
                debt.setDoTime(timeService.getNow());
                debt.setPointOfSale(pointOfSaleService.FF);
                debt.setConsume(checkIn.getFinalRoomPrice());
                debt.setCurrency("挂账");
                debt.setDescription("凌晨房费");
                debt.setSelfAccount(serialService.getSelfAccount());
                debt.setRoomId(checkIn.getRoomId());
                debt.setProtocol(checkIn.getProtocol());
                debt.setUserId(checkIn.getUserId());
                debt.setCategory(debtService.allDayPrice);
                debtService.addDebt(debt);
            }
            /*如果押金币种是会员，则需要先冻结这部分资金*/
            if (currencyService.HY.equals(checkIn.getCurrency())) {
                vipService.depositByVip(checkIn.getVipNumber(), checkIn.getDeposit());
            }
        } else {
            CheckInGroup checkInGroup = guestIn.getCheckInGroup();
            if (checkInGroup.getGroupAccount() == null) {//新开团队
                checkInGroup.setGroupAccount(serialService.getGroupAccount());
                checkInGroupService.add(checkInGroup);
            /*创建账务明细*/
                Debt debt = new Debt();
                debt.setDoTime(timeService.getNow());
                debt.setPointOfSale(pointOfSaleService.FF);
                debt.setGuestSource(checkInGroup.getGuestSource());
                debt.setDeposit(checkInGroup.getDeposit());
                debt.setCurrency(checkInGroup.getCurrency());
                debt.setDescription("团队开房押金录入");
                debt.setGroupAccount(checkInGroup.getGroupAccount());
                debt.setUserId(checkInGroup.getUserId());
                /*有领队房就录入到领队房里，没有就录入到第一个房间里*/
                debt.setRoomId(checkInGroup.getLeaderRoom());
                debt.setCategory(debtService.deposit);
                debtService.add(debt);
            }
            /*凌晨房判断*/
            if (nowTime.compareTo(limit) < 0 && nowTime.compareTo(nightTime) > 0) {//需要直接产生一笔房费
                for (CheckIn checkIn : guestIn.getCheckInList()) {
                    Debt debt = new Debt();
                    debt.setDoTime(timeService.getNow());
                    debt.setPointOfSale(pointOfSaleService.FF);
                    debt.setConsume(checkIn.getFinalRoomPrice());
                    debt.setCurrency("挂账");
                    debt.setDescription("凌晨房费");
                    debt.setSelfAccount(serialService.getSelfAccount());
                    debt.setGroupAccount(serialService.getGroupAccount());
                    debt.setRoomId(checkIn.getRoomId());
                    debt.setProtocol(checkIn.getProtocol());
                    debt.setUserId(checkIn.getUserId());
                    debtService.addDebt(debt);
                }
            }
            /*如果押金币种是会员，则需要先冻结这部分资金*/
            if (currencyService.HY.equals(checkInGroup.getCurrency())) {
                vipService.depositByVip(checkInGroup.getVipNumber(), checkInGroup.getDeposit());
            }
        }
    }

    private void bookProcess(GuestIn guestIn) throws Exception {
        Book book = guestIn.getBook();
        List<CheckIn> checkInList = guestIn.getCheckInList();
        /*如果有预订信息，则更新预订信息*/
        if (book != null) {
            bookRoomService.setOpened(book.getBookSerial(), checkInService.getTotalRoomString(checkInList));
            book.setBookedRoom(checkInList.size() + book.getBookedRoom());
            if (Objects.equals(book.getTotalRoom(), book.getBookedRoom())) {
                book.setState(bookService.cancel);
            }
            book.setReachTime(timeService.getNow());
            bookService.update(book);
            /*如果有订金，则退掉*/
            if (book.getSubscription() != null) {
                bookMoneyService.addSubscription(book.getBookSerial(), -book.getSubscription(), book.getCurrency());
            }
        }
    }

    private void userLogProcess(GuestIn guestIn) throws Exception {
        if (guestIn.getCheckInGroup() == null) {
            CheckIn checkIn = guestIn.getCheckInList().get(0);
            userLogService.addUserLog("散客开房:" + checkIn.getRoomId() + "押金:" + checkIn.getDeposit() + "币种:" + checkIn.getCurrency() + "房价:" + checkIn.getFinalRoomPrice() + "房价协议:" + checkIn.getProtocol() + "房租方式:" + checkIn.getRoomPriceCategory(), userLogService.reception, userLogService.guestIn,serialService.getSelfAccount());
        } else {
            CheckInGroup checkInGroup = guestIn.getCheckInGroup();
            userLogService.addUserLog("团队开房:" + checkInGroup.getName() + "押金:" + checkInGroup.getDeposit() + "币种:" + checkInGroup.getCurrency() + "房价协议:" + checkInGroup.getProtocol() + "房租方式:" + checkInGroup.getRoomPriceCategory(), userLogService.reception, userLogService.guestInGroup,serialService.getGroupAccount());
        }
    }

    private Integer reportProcess(GuestIn guestIn) throws Exception {
        /*创建散客押金单报表,并返回单据号
        * parameter：
        * 1.宾客姓名            checkInGuest.getName()
        * 2.宾客性别            checkInGuest.getSex()
        * 3.生日                timeService.dateToStringShort(checkInGuest.getBirthdayTime())
        * 4.证件类型            checkInGuest.getCardType()
        * 5.证件号码            checkInGuest.getCardId()
        * 6.抵店日期            timeService.dateToStringLong(checkIn.getReachTime())
        * 7.预计离店            timeService.dateToStringLong(checkIn.getLeaveTime())
        * 8.房间号码            checkIn.getRoomId()
        * 9.房价                String.valueOf(checkIn.getFinalRoomPrice())
        * 10.vip卡号            checkIn.getVipNumber()
        * 11.地址               checkInGuest.getAddress()
        * 12.付款方式           checkIn.getCurrency()
        * 13.特殊要求           checkIn.getImportant()
        * 14.备注饭店名称       checkIn.getRemark()
        * 15.打印时间           timeService.getNowLong()
        * 16.接待人员           userService.getCurrentUser()
        * 17.主账号             serialService.getSelfAccount()
        * 18.押金值             String.valueOf(checkIn.getNotNullDeposit())
        * 19.公司               checkIn.getCompany()
        * 20.房租方式           checkIn.getRoomPriceCategory()
        * 21.房价协议           checkIn.getProtocol()
        * 22.早餐份数           checkIn.getBreakfast()
        * 23.酒店名称           otherParamService.getValueByName("酒店名称")
        * 24.大写押金值         util.number2CNMontrayUnit(BigDecimal.valueOf(checkIn.getDeposit()))
        * 25.补打标志           guestIn.getAgain()
        * */
        if (guestIn.getCheckInGroup() == null) {
            CheckInGuest checkInGuest = guestIn.getCheckInGuestList().get(0);
            CheckIn checkIn = guestIn.getCheckInList().get(0);
            return reportService.generateReport(null, new String[]{checkInGuest.getName(), checkInGuest.getSex(), timeService.dateToStringShort(checkInGuest.getBirthdayTime()), checkInGuest.getCardType(), checkInGuest.getCardId(), timeService.dateToStringLong(checkIn.getReachTime()), timeService.dateToStringLong(checkIn.getLeaveTime()), checkIn.getRoomId(), String.valueOf(checkIn.getFinalRoomPrice()), checkIn.getVipNumber(), checkInGuest.getAddress(), checkIn.getCurrency(), checkIn.getImportant(), checkIn.getRemark(), timeService.getNowLong(), userService.getCurrentUser(), serialService.getSelfAccount(), String.valueOf(checkIn.getNotNullDeposit()), checkIn.getCompany(), checkIn.getRoomPriceCategory(), checkIn.getProtocol(), checkIn.getBreakfast(), otherParamService.getValueByName("酒店名称"), util.number2CNMontrayUnit(BigDecimal.valueOf(checkIn.getDeposit())), guestIn.getAgain()}, "deposit", "pdf");
        } else {
        /*创建团队押金单报表,并返回单据号
        * parameter：
        * 1.团名              checkInGroup.getName()
        * 2.抵店日期          timeService.dateToStringLong(checkInGroup.getReachTime())
        * 3.预计离店          timeService.dateToStringLong(checkInGroup.getLeaveTime())
        * 4.房间数量          String.valueOf(checkInGroup.getTotalRoom())
        * 5.vip卡号           checkInGroup.getVipNumber()
        * 6.付款方式          checkInGroup.getCurrency()
        * 7.备注              checkInGroup.getRemark()
        * 8.打印时间          timeService.getNowLong()
        * 9.接待人员          userService.getCurrentUser()
        * 10.主账号           serialService.getGroupAccount()
        * 11.押金值           String.valueOf(checkInGroup.getDeposit())
        * 12.公司             checkInGroup.getCompany()
        * 13.房租方式         checkInGroup.getRoomPriceCategory()
        * 14.房价协议         checkInGroup.getProtocol()
        * 15.酒店名称         otherParamService.getValueByName("酒店名称")
        * 16.大写押金值       util.number2CNMontrayUnit(BigDecimal.valueOf(checkInGroup.getNotNullGroupDeposit()))
        * 17.房号明细         checkInService.getTotalRoomString(guestIn.getCheckInList())
        * 18.补打标志         guestIn.getAgain()
        * */
            CheckInGroup checkInGroup = guestIn.getCheckInGroup();
            return reportService.generateReport(null, new String[]{checkInGroup.getName(), timeService.dateToStringLong(checkInGroup.getReachTime()), timeService.dateToStringLong(checkInGroup.getLeaveTime()), String.valueOf(checkInGroup.getTotalRoom()), checkInGroup.getVipNumber(), checkInGroup.getCurrency(), checkInGroup.getRemark(), timeService.getNowLong(), userService.getCurrentUser(), serialService.getGroupAccount(), String.valueOf(checkInGroup.getDeposit()), checkInGroup.getCompany(), checkInGroup.getRoomPriceCategory(), checkInGroup.getProtocol(), otherParamService.getValueByName("酒店名称"), util.number2CNMontrayUnit(BigDecimal.valueOf(checkInGroup.getNotNullGroupDeposit())), checkInService.getTotalRoomString(guestIn.getCheckInList()), guestIn.getAgain()}, "depositGroup", "pdf");
        }
    }

    /**
     * 补打押金单
     */
    @RequestMapping(value = "depositPrintAgain")
    @Transactional(rollbackFor = Exception.class)
    public Integer depositPrintAgain(@RequestBody Debt debt) throws Exception {
        /*构造一个GuestIn用于打印*/
        GuestIn guestIn = new GuestIn();
        CheckIn checkIn = checkInService.getByRoomId(debt.getRoomId());
        checkIn.setCurrency(debt.getCurrency());
        checkIn.setDeposit(debt.getDeposit());
        List<CheckIn> checkInList = new ArrayList<>();
        checkInList.add(checkIn);
        guestIn.setCheckInList(checkInList);
        if (debt.getGroupAccount() == null) {
            List<CheckInGuest> checkInGuestList = checkInGuestService.getListByRoomId(debt.getRoomId());
            guestIn.setCheckInGuestList(checkInGuestList);
        } else {
            CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(debt.getGroupAccount());
            List<CheckInGuest> checkInGuestList = checkInGuestService.getListByRoomId(debt.getRoomId());
            guestIn.setCheckInGroup(checkInGroup);
            guestIn.setCheckInGuestList(checkInGuestList);
        }
        guestIn.setAgain("补打");
        return this.reportProcess(guestIn);
    }

    /**
     * 单纯收取预付
     */
    @RequestMapping(value = "depositIn")
    @Transactional(rollbackFor = Exception.class)
    public Integer depositIn(@RequestBody DepositIn depositIn) throws Exception {
        /*解析传进来的三个参数*/
        String roomId = depositIn.getRoomId();
        String money = depositIn.getMoney();
        String currency = depositIn.getCurrency();
        String currencyAdd = depositIn.getCurrencyAdd();
        String bed = depositIn.getBed();
        Date leaveTime = depositIn.getLeaveTime();
        timeService.setNow();
        Debt debt = new Debt();
        debt.setRoomId(roomId);
        debt.setCurrency(currency);
        debt.setPointOfSale(pointOfSaleService.YF);
        debt.setDescription("押金单独补录");
        debt.setDeposit(Double.valueOf(money));
        debt.setVipNumber(currencyAdd);
        debt.setBed(bed);
        debt.setCategory(debtService.deposit);
        debt.setUserId(userService.getCurrentUser());
        /*补打押金单*/
        Integer reportIndex = this.depositPrintAgain(debt);
        /*如果押金币种是会员，则需要先冻结这部分资金*/
        if (currencyService.HY.equals(currency)) {
            vipService.depositByVip(currencyAdd, Double.valueOf(money));
        }
        debtService.addDebt(debt);
        /*如果修改了离店时间则需要更新在店户籍*/
        if (leaveTime != null) {
            CheckIn checkIn = checkInService.getByRoomId(roomId);
            checkIn.setLeaveTime(leaveTime);
            checkInService.update(checkIn);
        }
        return reportIndex;
    }
}
