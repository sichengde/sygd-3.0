/**
 * Created by 舒展 on 2016-04-27.
 */
package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.CurrencyPost;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

@RestController
public class GuestOutController {
    private static final Object lock = new Object();
    @Autowired
    SerialService serialService;
    @Autowired
    DebtService debtService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    RoomService roomService;
    @Autowired
    TimeService timeService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    CheckOutService checkOutService;
    @Autowired
    CheckOutRoomService checkOutRoomService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    VipService vipService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyLordService companyLordService;
    @Autowired
    FreemanService freemanService;
    @Autowired
    FreeDetailService freeDetailService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    ReportService reportService;
    @Autowired
    UserService userService;
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    CheckInHistoryService checkInHistoryService;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    Util util;
    @Autowired
    CheckOutGroupService checkOutGroupService;
    @Autowired
    ProtocolService protocolService;
    @Autowired
    SzMath szMath;
    @Autowired
    CheckOutPayBackService checkOutPayBackService;
    @Autowired
    GuestIntegrationService guestIntegrationService;
    @Autowired
    PayPointOfSaleService payPointOfSaleService;
    String checkOutSerial;
    GuestOut guestOutGlobal;
    List<Debt> debtList;
    List<DebtHistory> debtHistoryList;

    /**
     * 结算分为团队结算和单人结算
     */
    @RequestMapping(value = "guestOut", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Integer guestOut(@RequestBody GuestOut guestOut) throws Exception {
        synchronized (lock) {
            guestOutGlobal = guestOut;
            //离店数据校验,发现bug之后就可以删了
            Double totalTest = 0.0;
            Double totalCheckInConsume = 0.0;
            for (String roomId : guestOut.getRoomIdList()) {
                CheckIn checkIn = checkInService.getByRoomId(roomId);
                Double totalConsume = szMath.nullToZero(debtService.getTotalConsumeByRoomId(roomId));
                totalTest += szMath.nullToZero(totalConsume);
                totalCheckInConsume += szMath.nullToZero(checkIn.getConsume());
            }
            if (!Objects.equals(totalTest, totalCheckInConsume)) {
                throw new Exception("消费合计不准确，请联系厂家维护人员");
            }
            Double totalCurrency = 0.0;
            for (CurrencyPost currencyPost : guestOut.getCurrencyPayList()) {
                totalCurrency += currencyPost.getMoney();
            }
            for (Debt debt : guestOut.getDebtAddList()) {
                totalTest += debt.getConsume();
            }
            if (!Objects.equals(totalCurrency, (double) (Math.round(totalTest * 100) / 100.0))) {
                throw new Exception("结账金额有变动，请重新进入结账页面");
            }
            //离店数据校验,发现bug之后就可以删了--完毕
            Boolean real = guestOut.getNotNullReal();
            if (!real) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            /*获取有用信息*/
            String checkOutSerialCategory = guestOut.getCheckOutSerialCategory();
            timeService.setNow();//当前时间
            /*判断是否生成发票序列号*/
            if (SerialService.FA_PIAO_CO.equals(checkOutSerialCategory)) {
                checkOutSerial = serialService.setCheckOutSerialFp();//生成离店序列号发票
            } else {
                checkOutSerial = serialService.setCheckOutSerial();//生成离店序列号
            }
            serialService.setPaySerial();//生成结账序列号
            /*转换房态*/
            this.updateRoomStateGuestOut(guestOut.getRoomIdList());
            /*额外的加收房租*/
            this.debtAddIn(guestOut);
            /*离店明细单元*/
            Double checkOutMoney = this.newCheckOut(guestOut);
            /*检查是否有没退的预付，有的话自动退了*/
            //this.cancelDeposit(guestOut);//先保留，以后如果没用就删了
            /*账务明细转移到账务历史，并返回需要结算的账务*/
            debtList = this.debtToHistory(guestOut);
            /*查找中间结算，没有离店序列号的都是中间结算，找到后更新结账序列号，因为中间结算的结账明细没有结账序列号*/
            this.debtPayMiddle(guestOut);
            /*结账记录，循环分单，记录操作员挂账信息*/
            String changeDebt = this.debtPayProcess(guestOut.getCurrencyPayList(), guestOut.getRoomIdList(), guestOut.getGroupAccount(), "离店结算");
            /*判断押金币种，如果是会员则需要把钱还回去*/
            this.checkVipDeposit(guestOut);
            /*如果有单位协议，将该协议记录到单位消费之中*/
            this.checkCompany(guestOut, checkOutMoney);
            /*户籍转换，迁移到历史表*/
            GuestInfo guestInfo = this.checkInProcess(guestOut);
            /*生成离店报表*/
            Integer reportIndex = this.reportProcess(guestOut, guestInfo, changeDebt, debtList);
            /*操作员记录*/
            this.addUserLog(guestOut, changeDebt);
            /*删除账务，开房信息，团队开房信息，在店宾客（如果删早可能会导致后边的方法获取不到相关信息），房价协议（如果有）*///TODO:不能删
            this.delete(guestOut);
            /*找零信息记表*/
            for (CheckOutPayBack checkOutPayBack : guestOut.getCheckOutPayBackList()) {
                checkOutPayBack.setDoneTime(timeService.getNow());
                checkOutPayBack.setCheckOutSerial(checkOutSerial);
            }
            checkOutPayBackService.add(guestOut.getCheckOutPayBackList());
            return reportIndex;
        }
    }

    /**
     * 中间结算
     */
    @RequestMapping("guestOutMiddle")
    @Transactional(rollbackFor = Exception.class)
    public Integer guestOutMiddle(@RequestBody GuestOutMiddle guestOutMiddle) throws Exception {
        //int i=1/0;
        synchronized (lock) {
            Boolean real = guestOutMiddle.getNotNullReal();
            Boolean cancelDeposit = guestOutMiddle.getNotNullCancelDeposit();
            if (!real) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            timeService.setNow();//当前时间
            serialService.setPaySerial();//生成结账序列号
            List<Debt> debtList = guestOutMiddle.getDebtList();
            this.debtList=debtList;
            List<String> roomIdList = guestOutMiddle.getRoomIdList();
            String groupAccount = guestOutMiddle.getGroupAccount();
            Double payMoney = guestOutMiddle.getPayMoney();
            String remark = guestOutMiddle.getRemark();
            CheckIn checkIn;
            CheckInGroup checkInGroup = null;
            if (groupAccount == null) {
                checkIn = checkInService.getByRoomId(roomIdList.get(0));
            } else {
                checkInGroup = checkInGroupService.getByGroupAccount(groupAccount);
                checkIn = checkInService.getByRoomId(checkInGroup.getLeaderRoom());
            }
            String remarkAdd = "";
            if (debtList != null) {//根据明细来的中间结算
                /*账务转移到历史账务*/
                newDebtHistory(debtList);
                debtService.delete(debtList);
                remarkAdd += "指定账务";
            } else {//指定钱数的中间结算
                /*插入一条负的账务*/
                Debt debt = new Debt();
                debt.setDoTime(timeService.getNow());
                debt.setPointOfSale(pointOfSaleService.FF);
                debt.setConsume(-payMoney);
                debt.setCurrency("挂账");
                debt.setGuestSource(checkIn.getGuestSource());
                debt.setDescription("中间结算:" + remark);
                debt.setGroupAccount(groupAccount);
                debt.setUserId(userService.getCurrentUser());
                debt.setCategory(debtService.payMiddle);
                debt.setSelfAccount(checkIn.getSelfAccount());
                debt.setRoomId(checkIn.getRoomId());
                debt.setCompany(checkIn.getCompany());
                debtService.add(debt);
                DebtHistory debtHistory = new DebtHistory(debt);
                debtHistory.setConsume(payMoney);
                debtHistoryService.add(debtHistory);
                this.debtList=new ArrayList<>();
                Debt debt1=new Debt(debt);
                debt1.setConsume(payMoney);
                this.debtList.add(debt1);
                remarkAdd += "不指定账务";
            }
            /*TODO:在这插入一条退预付*/
            if (cancelDeposit) {
                Debt cancelDebt = new Debt();
                cancelDebt.setDoTime(timeService.getNow());
                cancelDebt.setPointOfSale(pointOfSaleService.FF);
                cancelDebt.setDeposit(-payMoney);
                cancelDebt.setCurrency("人民币");
                cancelDebt.setDescription("中间结算退预付");
                cancelDebt.setGroupAccount(groupAccount);
                cancelDebt.setUserId(userService.getCurrentUser());
                cancelDebt.setCategory(debtService.cancelDeposit);
                cancelDebt.setSelfAccount(checkIn.getSelfAccount());
                cancelDebt.setRoomId(checkIn.getRoomId());
                cancelDebt.setCompany(checkIn.getCompany());
                debtService.addDebt(cancelDebt);
            }
            /*更新在店户籍余额，按明细结算的话，就更新对应房间的余额，否则直接更新领队房余额*/
            if (debtList != null) {
                for (String s : roomIdList) {
                    CheckIn checkIn1 = checkInService.getByRoomId(s);
                    Double totalConsume = 0.0;
                    for (Debt debt : debtList) {
                        if (checkIn1.getSelfAccount().equals(debt.getSelfAccount())) {
                            totalConsume += debt.getConsume();
                        }
                    }
                    debtService.updateGuestInMoney(checkIn1.getRoomId(), -totalConsume, 0.0);
                }
            } else {
                debtService.updateGuestInMoney(checkIn.getRoomId(), -payMoney, 0.0);
            }
            /*生成结账信息*/
            String changeDebt = this.debtPayProcess(guestOutMiddle.getCurrencyPayList(), roomIdList, groupAccount, "中间结算" + remarkAdd);
            /*操作员记录*/
            String userAction = "中间结算:" + roomService.roomListToString(roomIdList) + changeDebt;
            userLogService.addUserLog(userAction, userLogService.reception, userLogService.guestOutMiddle, serialService.getPaySerial());
            /*生成报表*/
            String groupName = null;
            String account;
            String firstRoom;
            String guestInVip;
            if (checkInGroup != null) {
                groupName = checkInGroup.getName();
                account = checkInGroup.getGroupAccount();
                firstRoom = checkInGroup.getLeaderRoom();
                guestInVip = checkInGroup.getVipNumber();
            } else {
                account = checkIn.getSelfAccount();
                firstRoom = checkIn.getRoomId();
                guestInVip = checkIn.getVipNumber();
            }
            String[] parameters = new String[]{
                    otherParamService.getValueByName("酒店名称"),
                    checkInGuestService.getListByRoomId(guestOutMiddle.getRoomIdList()),
                    firstRoom,
                    serialService.getPaySerial(),
                    timeService.dateToStringLong(checkIn.getReachTime()),
                    timeService.getNowLong(),
                    checkIn.getCompany(),
                    groupName,
                    userService.getCurrentUser(),
                    timeService.getNowLong(),
                    ifNotNullGetString(payMoney),
                    changeDebt,
                    null,
                    account,
                    util.listToString(roomIdList),
                    ifNotNullGetString(checkIn.getFinalRoomPrice()),
                    null,
                    null,
                    null,
                    null,
                    guestInVip
            };
            List<FieldTemplate> templateList;
            if (debtList != null) {
                templateList = new ArrayList<>();
                for (Debt debt : debtList) {
                    FieldTemplate var = new FieldTemplate();
                    var.setField1(timeService.dateToStringLong(debt.getDoTime()));
                    var.setField2(debt.getRoomId());
                    var.setField3(debt.getDescription());
                    var.setField4(String.valueOf(debt.getNotNullConsume()));
                    var.setField6(String.valueOf(debt.getCurrency()));
                    var.setField7(debt.getUserId());
                    templateList.add(var);
                }
            } else {
                templateList = null;
            }
            return reportService.generateReport(templateList, parameters, "guestOutMiddle", "pdf");
        }
    }

    /**
     * 转换房态
     *
     * @param roomList 离店房号数组
     */
    private void updateRoomStateGuestOut(List<String> roomList) {
        for (String roomId : roomList) {
            roomService.updateRoomStateGuestOut(roomId);
        }
    }

    /**
     * 离店明细单元，返回离店金额（在店消费金额）
     */
    private Double newCheckOut(GuestOut guestOut) throws Exception {
        CheckOut checkOut = new CheckOut();
        checkOut.setCheckOutSerial(checkOutSerial);
        checkOut.setCheckOutTime(timeService.getNow());
        checkOut.setUserId(userService.getCurrentUser());
        checkOut.setRemark(guestOut.getRemark());
        checkOut.setFpMoney(guestOut.getFpMoney());
        checkOut.setRoomId(roomService.roomListToString(guestOut.getRoomIdList()));
        if (guestOut.getGroupAccount() == null) {
            CheckIn checkIn = checkInService.getByRoomId(guestOut.getRoomIdList().get(0));//在店户籍
            checkOut.setReachTime(checkIn.getReachTime());
            checkOut.setSelfAccount(checkIn.getSelfAccount());
            checkOut.setCompany(checkIn.getCompany());
            checkOut.setDeposit(checkIn.getDeposit());
        } else {
            CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(guestOut.getGroupAccount());
            checkOut.setReachTime(checkInGroup.getReachTime());
            checkOut.setGroupAccount(guestOut.getGroupAccount());
            checkOut.setCompany(checkInGroup.getCompany());
            checkOut.setDeposit(checkInGroup.getDeposit());
            checkOut.setGroupName(checkInGroup.getName());
            /*如果是团队的话备份checkOutGroup*/
            CheckOutGroup checkOutGroup = new CheckOutGroup(checkInGroup);
            checkOutGroup.setCheckOutSerial(checkOutSerial);
            checkOutGroupService.add(checkOutGroup);
        }
        List<String> roomList = guestOut.getRoomIdList();
        Double totalConsume = 0.0;
        for (String s : roomList) {
            CheckIn checkIn = checkInService.getByRoomId(s);
            totalConsume += checkIn.getNotNullConsume();
        }
        checkOut.setConsume(totalConsume);
        checkOutService.add(checkOut);
        return totalConsume;
    }

    /**
     * 额外的加收房租
     */
    private void debtAddIn(GuestOut guestOut) {
        if (guestOut.getDebtAddList() != null) {
            if (guestOut.getDebtAddList().size() != 0) {
                debtService.addDebt(guestOut.getDebtAddList());
            }
        }
    }

    /**
     * 检查是否有没退的预付，有的话自动退了(暂时没用)
     */
    private void cancelDeposit(GuestOut guestOut) throws Exception {
        List<Debt> debtList;
        if (guestOut.getGroupAccount() == null) {
            debtList = debtService.getListByRoomId(guestOut.getRoomIdList().get(0));
        } else {
            debtList = debtService.getListByGroupAccount(guestOut.getGroupAccount());
        }
        for (Debt debt : debtList) {//账单显示退预付
            if (debt.getNotNullDeposit() > 0 && !"已退".equals(debt.getRemark())) {
                Debt debt1 = new Debt(debt);
                debt1.setDeposit(-debt.getDeposit());
                debt1.setDescription("结账退预付");
                debtService.add(debt1);
            }
        }
    }

    /**
     * 账务明细转移到账务历史，并返回需要结算的账务
     */
    private List<Debt> debtToHistory(GuestOut guestOut) throws Exception {
        List<Debt> debtList = new ArrayList<>();
        List<String> roomList = guestOut.getRoomIdList();
        for (String s : roomList) {
            debtList.addAll(debtService.getListByRoomIdPure(s));
        }
        this.debtHistoryList = newDebtHistory(debtList);
        return debtList;
    }

    private List<DebtHistory> newDebtHistory(List<Debt> debtList) throws Exception {
        List<DebtHistory> debtHistoryList = new ArrayList<>();
        for (Debt debt : debtList) {
            if (debt.getPaySerial() == null) {
                debt.setPaySerial(serialService.getPaySerial());
            }
            DebtHistory debtHistory = new DebtHistory(debt);
            debtHistory.setDoneTime(timeService.getNow());
            debtHistoryList.add(debtHistory);
        }
        debtHistoryService.add(debtHistoryList);
        return debtHistoryList;
    }

    /**
     * 更新中间结算
     */
    private void debtPayMiddle(GuestOut guestOut) throws Exception {
        List<DebtPay> debtPayList = new ArrayList<>();
        List<String> roomList = guestOut.getRoomIdList();
        for (String s : roomList) {
            CheckIn checkIn = checkInService.getByRoomId(s);//在店户籍
            debtPayList.addAll(debtPayService.getListBySelfAccount(checkIn.getSelfAccount()));
        }
        for (DebtPay debtPay : debtPayList) {
            debtPay.setCheckOutSerial(checkOutSerial);
        }
        debtPayService.update(debtPayList);
    }

    /**
     * 生成结账记录，同时处理分单，返回分担信息字符串，供操作日志记录
     */
    private String debtPayProcess(List<CurrencyPost> currencyPayList, List<String> roomIdList, String groupAccount, String category) throws Exception {
        String changeDebt = "";
        /*结账记录，循环分单*/
        /*如果有分单自动分销售点金额*/
        int debtIndex = 0;//账务索引
        double debtAdjust = 0;//账务索引
        int currencyIndex = 0;//账务索引
        double debtSum = 0.0;//账务累计
        String lastPointOfSale = null;
        Date lastCreateTime = null;
        List<PayPointOfSale> payPointOfSaleList = new ArrayList<>();//先一条一条插，然后统一聚合
        for (; currencyIndex < currencyPayList.size(); currencyIndex++) {
            CurrencyPost currencyPost = currencyPayList.get(currencyIndex);
            String currency = currencyPost.getCurrency();
            String currencyAdd = currencyPost.getCurrencyAdd();
            Double money = currencyPost.getMoney();
            DebtPay debtPay = new DebtPay();
            debtPay.setPaySerial(serialService.getPaySerial());
            switch (category) {
                case "离店结算":
                    debtPay.setCheckOutSerial(checkOutSerial);
                    debtPay.setDebtCategory(debtPayService.ldjs);
                    debtPay.setDescription("离店结算结账记录");
                    break;
                case "中间结算指定账务":
                    debtPay.setDebtCategory(debtPayService.zjjszdzw);
                    debtPay.setDescription("中间结算结账记录");
                    break;
                case "中间结算不指定账务":
                    debtPay.setDebtCategory(debtPayService.zjjsbzdzw);
                    debtPay.setDescription("中间结算结账记录");
                    break;
            }
            debtPay.setDebtMoney(money);
            debtPay.setCurrency(currency);
            debtPay.setCurrencyAdd(currencyAdd);
            debtPay.setDoneTime(timeService.getNow());
            debtPay.setRoomId(roomService.roomListToString(roomIdList));
            debtPay.setGuestName(checkInGuestService.getListByRoomId(roomIdList));
            if (groupAccount == null) {
                CheckIn checkIn = checkInService.getByRoomId(roomIdList.get(0));//在店户籍
                debtPay.setCompany(checkIn.getCompany());
                debtPay.setSelfAccount(checkIn.getSelfAccount());
                debtPay.setGroupAccount(checkIn.getGroupAccount());
                debtPay.setVipNumber(checkIn.getVipNumber());
                debtPay.setGuestSource(checkIn.getGuestSource());
            } else {
                CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(groupAccount);
                debtPay.setCompany(checkInGroup.getCompany());
                debtPay.setGroupAccount(checkInGroup.getGroupAccount());
                debtPay.setVipNumber(checkInGroup.getVipNumber());
                debtPay.setGuestSource(checkInGroup.getGuestSource());
            }
            debtPay.setPointOfSale(pointOfSaleService.JQ);
            debtPay.setUserId(userService.getCurrentUser());
            boolean noNeedParse = false;
            if ("转哑房".equals(currency)) {
                debtPay.setLostDone(false);
                noNeedParse = true;
                for (Debt debt : debtList) {
                    Debt debtNeedInsert = new Debt(debt);
                    debtNeedInsert.setRoomId("哑房");
                    debtNeedInsert.setFromRoom(serialService.getPaySerial());
                    /*押金在历史里设置为true*/
                    if (debt.getDeposit() == null) {
                        debtNeedInsert.setNotPartIn(true);
                    }
                    debtService.add(debtNeedInsert);
                }
                for (DebtHistory debtHistory : this.debtHistoryList) {
                    if (debtHistory.getDeposit() != null) {
                        debtHistory.setNotPartIn(true);
                        debtHistoryService.update(debtHistory);
                    }
                }
            }
            debtPayService.add(debtPay);
            if (lastPointOfSale != null) {
                PayPointOfSale payPointOfSale = new PayPointOfSale();
                payPointOfSale.setDebtPayId(debtPay.getId());
                payPointOfSale.setCurrency(debtPay.getCurrency());
                payPointOfSale.setCompanyPayId(null);
                payPointOfSale.setDoTime(timeService.getNow());
                payPointOfSale.setCreateTime(lastCreateTime);
                payPointOfSale.setPointOfSale(lastPointOfSale);
                payPointOfSale.setMoney(debtAdjust);
                lastPointOfSale = null;
                lastCreateTime = null;
                payPointOfSaleList.add(payPointOfSale);
            }
            for (; debtIndex < debtList.size(); debtIndex++) {
                Debt debt = debtList.get(debtIndex);
                if (debt.getNotNullConsume() == 0.0) {//没消费就继续
                    continue;
                }
                debtSum += debt.getNotNullConsume();
                debtSum = szMath.formatTwoDecimalReturnDouble(debtSum);
                /*先把实体建上，用不用再说*/
                PayPointOfSale payPointOfSale = new PayPointOfSale();
                payPointOfSale.setDebtPayId(debtPay.getId());
                payPointOfSale.setCurrency(debtPay.getCurrency());
                payPointOfSale.setCompanyPayId(null);
                payPointOfSale.setDoTime(timeService.getNow());
                payPointOfSale.setCreateTime(debt.getDoTime());
                payPointOfSale.setPointOfSale(debt.getPointOfSale());
                payPointOfSale.setMoney(debt.getNotNullConsume());
                if (debtSum < debtPay.getDebtMoney()) {
                    payPointOfSaleList.add(payPointOfSale);
                    continue;
                }
                if (debtSum == debtPay.getDebtMoney()) {//相等最好了，不用拆，不过基本不可能
                    payPointOfSaleList.add(payPointOfSale);
                    debtSum = 0;
                    debtIndex++;
                    break;
                }
                /*不相等，需要拆分金额*/
                if (debtSum > debtPay.getDebtMoney()) {
                    debtAdjust = debtPay.getDebtMoney() - (debtSum - debt.getNotNullConsume());
                    debtAdjust = szMath.formatTwoDecimalReturnDouble(debtAdjust);
                    payPointOfSale.setMoney(debtAdjust);
                    payPointOfSaleList.add(payPointOfSale);
                    debtAdjust = debt.getNotNullConsume() - debtAdjust;
                    debtSum = debtAdjust;
                    debtSum = szMath.formatTwoDecimalReturnDouble(debtSum);
                    lastPointOfSale = debt.getPointOfSale();
                    lastCreateTime=debt.getDoTime();
                    debtIndex++;
                    break;
                }
            }
            changeDebt += " 币种:" + currency + "/" + money;
            /*检查转房客，转押金，因为只有离店时有这个选项*/
            if ("转房客".equals(currency) && guestOutGlobal.getNotNullChangeDetail()) {
                noNeedParse = true;
                CheckIn checkIn = checkInService.getByRoomId(currencyAdd);
                changeDebt += " 转房客至:" + currencyAdd;
                for (Debt debt : debtList) {
                    Debt debtNeedInsert = new Debt(debt);
                    debtNeedInsert.setPaySerial(null);
                    debtNeedInsert.setFromRoom(serialService.getPaySerial());
                    debtNeedInsert.setRemark(debtNeedInsert.getRoomId() + "->转入产生");
                    debtNeedInsert.setRoomId(currencyAdd);
                    debtNeedInsert.setSelfAccount(checkIn.getSelfAccount());
                    debtNeedInsert.setGroupAccount(checkIn.getGroupAccount());
                    debtNeedInsert.setTotalConsume(checkIn.getNotNullConsume() + debtNeedInsert.getNotNullConsume());
                    /*押金在历史里设置为true*/
                    if (debt.getDeposit() == null) {
                        debtNeedInsert.setNotPartIn(true);
                    }
                    debtService.add(debtNeedInsert);
                    debtService.updateGuestInMoney(checkIn.getRoomId(), debtNeedInsert.getConsume(), debtNeedInsert.getDeposit());
                }
                for (DebtHistory debtHistory : this.debtHistoryList) {
                    if (debtHistory.getDeposit() != null) {
                        debtHistory.setNotPartIn(true);
                        debtHistoryService.update(debtHistory);
                    }
                }
            }
            /*通过币种判断结账类型*/
            if (!noNeedParse) {
                changeDebt += debtPayService.parseCurrency(currency, currencyAdd, money, roomIdList, groupAccount, category, serialService.getPaySerial(), "接待", "接待");
            }
            /*检查会员*/
            this.checkVip(groupAccount, roomIdList, currency, currencyAdd, money);
        }
        payPointOfSaleService.add(payPointOfSaleList);
        return changeDebt;
    }

    /**
     * 判断押金币种，如果是会员则需要把钱还回去
     */
    private void checkVipDeposit(GuestOut guestOut) {
        List<Debt> debtList = debtService.getDepositListByRoomList(guestOut.getRoomIdList());
        for (Debt debt : debtList) {
            if (currencyService.HY.equals(debt.getCurrency())) {
                vipService.depositByVip(debt.getVipNumber(), -debt.getDeposit());
            }
        }
    }

    /**
     * 如果有单位协议，将该协议记录到单位消费之中
     */
    private void checkCompany(GuestOut guestOut, Double money) {
        if (guestOut.getGroupAccount() == null) {
            CheckIn checkIn = checkInService.getByRoomId(guestOut.getRoomIdList().get(0));//在店户籍
            if (checkIn.getCompany() != null) {
                companyService.addConsume(checkIn.getCompany(), money);
            }
        } else {
            CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(guestOut.getGroupAccount());
            if (checkInGroup.getCompany() != null) {
                companyService.addConsume(checkInGroup.getCompany(), money);
            }
        }
    }

    /**
     * 如果有会员号，并且结算的时候没有用会员余额或者积分计算的话，累计积分
     */
    private void checkVip(String groupAccount, List<String> roomIdList, String currency, String currencyAdd, Double money) throws Exception {
        /*转哑房不判断*/
        if ("转哑房".equals(currency)) {
            return;
        }
        String vipNumber = null;
        /*如果是会员结账，则积分记录到结账账户*/
        if ("会员".equals(currency)) {
            vipNumber = currencyAdd.split(" ")[0];
        } else if (groupAccount == null) {
            CheckIn checkIn = checkInService.getByRoomId(roomIdList.get(0));
            vipNumber = checkIn.getVipNumber();
        } else {
            CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(groupAccount);
            vipNumber = checkInGroup.getVipNumber();
        }
        if (vipNumber != null && currencyService.get(new Query("currency=" + util.wrapWithBrackets(currency))).get(0).getNotNullScore()) {
            vipService.updateVipScore(vipNumber, money);
        }
    }

    /**
     * 户籍转换，返回宾客姓名字符串
     */
    private GuestInfo checkInProcess(GuestOut guestOut) throws Exception {
        List<String> roomList = guestOut.getRoomIdList();
        GuestInfo guestInfo = new GuestInfo();
        List<CheckOutRoom> checkOutRoomList = new ArrayList<>();
        for (String s : roomList) {
            CheckIn checkIn = checkInService.getByRoomId(s);//在店户籍
            List<String> checkInGuestCardIdList = new ArrayList<>();//准备添加的宾客信息去重
            List<CheckInGuest> checkInGuestList = checkInGuestService.getListByRoomId(checkIn.getRoomId());
            List<CheckInHistory> checkInHistoryList = new ArrayList<>();
            List<CheckInHistory> checkInHistoryUpdateList = new ArrayList<>();
            CheckInHistoryLog checkInHistoryLog = new CheckInHistoryLog(checkIn);
            checkInHistoryLog.setGuestName(checkInGuestService.listToStringName(checkInGuestService.getListByRoomId(checkIn.getRoomId())));
            checkInHistoryLog.setLeaveTime(timeService.getNow());
            checkInHistoryLog.setCheckOutSerial(checkOutSerial);
            guestInfo.addAll(this.guestToHistory(checkInGuestList, checkInHistoryList, checkInHistoryUpdateList, checkInGuestCardIdList));
            checkInHistoryLogService.add(checkInHistoryLog);
            checkInHistoryService.add(checkInHistoryList);
            checkInHistoryService.update(checkInHistoryUpdateList);
            /*离店房明细*/
            CheckOutRoom checkOutRoom = new CheckOutRoom();
            checkOutRoom.setCheckOutSerial(checkOutSerial);
            checkOutRoom.setRoomId(checkIn.getRoomId());
            checkOutRoom.setSelfAccount(checkIn.getSelfAccount());
            checkOutRoom.setGroupAccount(checkIn.getGroupAccount());
            checkOutRoom.setSource(checkIn.getGuestSource());
            checkOutRoom.setRoomPriceCategory(checkIn.getRoomPriceCategory());
            checkOutRoom.setProtocol(checkIn.getProtocol());
            checkOutRoom.setReachTime(checkIn.getReachTime());
            checkOutRoom.setLeaveTime(timeService.getNow());
            checkOutRoom.setName(guestInfo.guestName);
            checkOutRoom.setCompany(checkIn.getCompany());
            checkOutRoomList.add(checkOutRoom);
        }
        checkOutRoomService.add(checkOutRoomList);
        return guestInfo;
    }

    /**
     * 宾客户籍等转换，返回在店宾客姓名字符串
     */
    private GuestInfo guestToHistory(List<CheckInGuest> checkInGuestList, List<CheckInHistory> checkInHistoryList, List<CheckInHistory> checkInHistoryUpdateList, List<String> checkInGuestCardIdList) throws Exception {
        GuestInfo guestInfo = new GuestInfo();
        for (CheckInGuest checkInGuest : checkInGuestList) {
            guestInfo.addGuestName(checkInGuest.getName());
            guestInfo.addPhone(checkInGuest.getPhone());
            guestInfo.addSex(checkInGuest.getSex());
            CheckInHistory checkInHistoryOld = checkInHistoryService.getByCardId(checkInGuest.getCardId());//之前来过的
            if (checkInHistoryOld == null) {//如果该宾客没来过，加一条
                if (checkInGuestCardIdList.indexOf(checkInGuest.getCardId()) == -1) {//信息去重，这里只考虑录入时录入错了的情况
                    CheckInHistory checkInHistory = new CheckInHistory(checkInGuest);
                    checkInHistory.setLastTime(timeService.getNow());
                    checkInHistory.setNum(1);
                    checkInHistoryList.add(checkInHistory);
                    checkInGuestCardIdList.add(checkInGuest.getCardId());
                }
            } else {//来过的话更新一下来店次数
                checkInHistoryOld.setNum(checkInHistoryOld.getNum() + 1);
                checkInHistoryUpdateList.add(checkInHistoryOld);
            }
        }
        this.idCardVipProcess(checkInGuestList);
        return guestInfo;
    }

    /**
     * 生成身份证会员
     */
    private void idCardVipProcess(List<CheckInGuest> checkInGuestList) throws Exception {
        if (otherParamService.getValueByName("客史会员").equals("n")) {
            return;
        }
        for (CheckInGuest checkInGuest : checkInGuestList) {
            if (vipService.get(new Query("vip_number=" + util.wrapWithBrackets(checkInGuest.getCardId()))).size() == 0) {
                Vip vip = new Vip();
                vip.setVipNumber(checkInGuest.getCardId());
                vip.setCardId(checkInGuest.getCardId());
                vip.setCategory(vipService.KS);
                vip.setName(checkInGuest.getName());
                vip.setSex(checkInGuest.getSex());
                vip.setAddress(checkInGuest.getAddress());
                vip.setRace(checkInGuest.getRace());
                vip.setBirthdayTime(checkInGuest.getBirthdayTime());
                vip.setPhone(checkInGuest.getPhone());
                vip.setScore(0.0);
                vip.setRemain(0.0);
                vip.setDoTime(timeService.getNow());
                vip.setUserId(userService.getCurrentUser());
                vipService.add(vip);
            }
        }
    }

    /**
     * 生成离店报表
     */
    private Integer reportProcess(GuestOut guestOut, GuestInfo guestInfo, String changeDebt, List<Debt> debtList) throws Exception {
        /*创建报表，并返回单据号
         * param:
         * 1.酒店名称                          otherParamService.getValueByName("酒店名称")
         * 2.姓名                              guestInfo.getGuestName()
         * 3.房号                              roomID
         * 4.结账序列号(流水号)                serialService.getCheckOutSerial()
         * 5.到达日期                          reachTime
         * 6.离店日期                          timeService.getNowLong()/
         * 7.公司                              company
         * 8.团队名称                          groupName
         * 9.收款员                            userService.getCurrentUser()
         * 10.结账时间                         timeService.getNowLong()
         * 11.结账总金额                       ifNotNullGetString(consume)
         * 12.结算币种信息(转账信息)           changeDebt
         * 13.补交找回金额信息                 cancelMsg
         * 14.主账号                           account
         * 15.团队房结算的话显示总房数         roomIdAll
         * 16.日租金                           finalRoomPrice
         * 17.总预付                           ifNotNullGetString(deposit)
         * 18.总房费                           ifNotNullGetString(totalRoomConsume)
         * 19.总房吧消费                       ifNotNullGetString(totalRoomShopConsume)
         * 20.其他消费                         ifNotNullGetString(otherConsume)
         * 21.开房会员                         guestInVip
         * 22.发票金额                         fpMoney
         * 23.备注                             guestOut.getRemark()
         * 24.电话                             guestInfo.getPhone()
         * 25.性别                             guestInfo.getSex()
         * 26.单位分隔1                        debtPayService.companySplit1
         * 27.单位分隔2                        debtPayService.companySplit2
         * field
         * 1.日期
         * 2.房号
         * 3.摘要
         * 4.消费
         * 5.押金
         * 6.币种
         * 7.操作员
         * 8.起始房号
         * */
        String reachTime;
        String leaveTime = timeService.getNowLong();
        String company;
        Double consume;
        Double deposit;
        String groupName = null;
        String roomID = null;
        String roomIdAll = null;
        String guestInVip = "";//开房时的会员
        List<String> roomList = guestOut.getRoomIdList();
        String account;
        String finalRoomPrice = null;
        Double totalRoomConsume;//总房费
        Double totalRoomShopConsume;//总房吧费
        Double otherConsume;
        Double fpMoney = guestOut.getFpMoney();//发票金额
        String title = otherParamService.getValueByName("酒店名称");
        if (guestOut.getAgain() == null || "中间未结补打".equals(guestOut.getAgain())) {
            if (guestOut.getGroupAccount() == null) {
                if (debtList == null) {
                    if ("中间未结补打".equals(guestOut.getAgain())) {
                        debtList = debtService.getByHistory(debtHistoryService.getListByPaySerial(guestOut.getPaySerial()));
                    } else {
                        debtList = debtService.getListByRoomId(guestOut.getRoomIdList().get(0));
                    }
                }
                CheckIn checkIn = checkInService.getByRoomId(guestOut.getRoomIdList().get(0));//在店户籍
                reachTime = timeService.dateToStringLong(checkIn.getReachTime());
                company = checkIn.getCompany();
                consume = checkInService.getNeedPay(checkIn);
                deposit = checkIn.getDeposit();
                roomID = checkIn.getRoomId();
                account = checkIn.getSelfAccount();
                guestInVip = checkIn.getVipNumber();
                finalRoomPrice = ifNotNullGetString(checkIn.getFinalRoomPrice());
                totalRoomConsume = debtService.getTotalConsumeByPointOfSaleAndSerial("房费", checkIn.getSelfAccount());
                totalRoomShopConsume = debtService.getTotalConsumeByPointOfSaleAndSerial("房吧", checkIn.getSelfAccount());
            } else {
                CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(guestOut.getGroupAccount());
                roomID = checkInGroup.getLeaderRoom();
                reachTime = timeService.dateToStringLong(checkInGroup.getReachTime());
                company = checkInGroup.getCompany();
                Double totalConsume = 0.0;
                for (String s : roomList) {
                    CheckIn checkIn = checkInService.getByRoomId(s);
                    totalConsume += checkIn.getNotNullConsume();
                    if (checkIn.getVipNumber() != null) {
                        guestInVip += checkIn.getVipNumber() + ",";
                    }
                }
                consume = totalConsume;
                deposit = checkInGroup.getDeposit();
                account = checkInGroup.getGroupAccount();
                groupName = checkInGroup.getName();
                roomIdAll = roomService.roomListToString(roomList);
                totalRoomConsume = debtService.getTotalConsumeByPointOfSaleAndSerial("房费", checkInGroup.getGroupAccount());
                totalRoomShopConsume = debtService.getTotalConsumeByPointOfSaleAndSerial("房吧", checkInGroup.getGroupAccount());
            }
            /*如果是查询结账单，需要把加收房租数组手动添加进去，并且在标题后标注明是查询单*/
            if (guestOut.getDebtAddList() != null && changeDebt == null) {//明细单的判断条件
                debtList.addAll(guestOut.getDebtAddList());
                consume += debtService.getTotalConsumeByList(guestOut.getDebtAddList());
                title += "/查询单";
            }
        } else {//补打结账单
            debtList = debtService.getByHistory(debtHistoryService.getListByPaySerial(guestOut.getPaySerial()));
            CheckOut checkOut = checkOutService.get(new Query("check_out_serial=" + util.wrapWithBrackets(guestOut.getCheckOutSerial()))).get(0);
            reachTime = timeService.dateToStringLong(checkOut.getReachTime());
            leaveTime = timeService.dateToStringLong(checkOut.getCheckOutTime());
            company = checkOut.getCompany();
            consume = checkOut.getConsume();
            deposit = checkOut.getDeposit();
            roomID = checkOut.getRoomId();
            if (guestOut.getGroupAccount() == null) {
                CheckInHistoryLog checkInHistoryLog = checkInHistoryLogService.getByCheckOutSerial(checkOut.getCheckOutSerial()).get(0);
                account = checkOut.getSelfAccount();
                guestInVip = checkInHistoryLog.getVipNumber();
                finalRoomPrice = checkOutRoomService.getByCheckOutSerial(guestOut.getCheckOutSerial()).get(0).getFinalRoomPrice();
                totalRoomConsume = debtHistoryService.getTotalConsumeByPointOfSaleAndSerial("房费", checkOut.getSelfAccount());
                totalRoomShopConsume = debtHistoryService.getTotalConsumeByPointOfSaleAndSerial("房吧", checkOut.getSelfAccount());
            } else {
                CheckOutGroup checkOutGroup = checkOutGroupService.getByCheckOutSerial(checkOut.getCheckOutSerial());
                List<CheckInHistoryLog> checkInHistoryLogList = checkInHistoryLogService.getByCheckOutSerial(checkOutGroup.getCheckOutSerial());
                for (CheckInHistoryLog checkInHistoryLog : checkInHistoryLogList) {
                    if (checkInHistoryLog.getVipNumber() != null) {
                        guestInVip += checkInHistoryLog.getVipNumber() + ",";
                    }
                }
                account = checkOut.getGroupAccount();
                groupName = checkOut.getGroupName();
                roomID = checkOutGroup.getLeaderRoom();
                roomIdAll = roomService.roomListToString(roomList);
                totalRoomConsume = debtHistoryService.getTotalConsumeByPointOfSaleAndSerial("房费", checkOut.getGroupAccount());
                totalRoomShopConsume = debtHistoryService.getTotalConsumeByPointOfSaleAndSerial("房吧", checkOut.getGroupAccount());
            }
        }
        otherConsume = nullToZero(consume) - nullToZero(totalRoomConsume) - nullToZero(totalRoomShopConsume);
        debtList = debtService.mergeDebt(debtList);
        List<FieldTemplate> templateList = new ArrayList<>();
        String cancelMsg = "";//退预付信息,计算思路，先把结账的金额至为负，然后用押金相同币种的加，最后如果不是0，就是找回或者补交的金额
        List<CurrencyPost> currencyPostList = guestOut.getCurrencyPayList();
        for (CurrencyPost currencyPost : currencyPostList) {//先把所有金额置为负
            currencyPost.setMoney(-currencyPost.getMoney());
        }
        /*押金币种map*/
        Map<String,Double> depositCurrencyMap=new HashMap<>();
        for (Debt debt : debtList) {//同时更新汇总信息
            FieldTemplate var = new FieldTemplate();
            var.setField1(timeService.dateToStringLong(debt.getDoTime()));
            var.setField2(debt.getRoomId());
            var.setField3(debt.getDescription());
            var.setField4(String.valueOf(debt.getNotNullConsume()));
            var.setField5(String.valueOf(debt.getNotNullDeposit()));
            var.setField6(String.valueOf(debt.getCurrency()));
            var.setField7(debt.getUserId());
            var.setField8(debt.getSourceRoom());
            templateList.add(var);
            if (debt.getNotNullDeposit() != 0 ) {//没有退的押金，准备考虑与结账币种的关系
                depositCurrencyMap.put(debt.getCurrency(),depositCurrencyMap.getOrDefault(debt.getCurrency(),0.0)+debt.getNotNullDeposit());
            }
        }
        List<String> currencyToRmbList=currencyService.getToRMBList();
        for (String currency : depositCurrencyMap.keySet()) {
            boolean haveSame=false;
            for (CurrencyPost currencyPost : currencyPostList) {
                if(currencyPost.getCurrency().equals(currency)){
                    haveSame=true;
                    currencyPost.setMoney(depositCurrencyMap.get(currency)+currencyPost.getMoney());
                }
            }
            if (!haveSame) {
                String currencyTemp;
                if (currencyToRmbList.indexOf(currency) > -1) {
                    currencyTemp = "人民币";
                } else {
                    currencyTemp = currency;
                }
                cancelMsg += "找回金额：" +szMath.formatTwoDecimal(depositCurrencyMap.get(currency)) + "(" + currencyTemp + ") ";
            }
        }
        for (CurrencyPost currencyPost : currencyPostList) {//看看还有没有剩下的补交信息
            if (currencyPost.getMoney() >= 0) {
                cancelMsg += "找回金额：" + currencyPost.getMoney() + "(" + currencyPost.getCurrency() + ") ";
            }
            if (currencyPost.getMoney() < 0) {
                cancelMsg += "补交金额：" + -currencyPost.getMoney() + "(" + currencyPost.getCurrency() + ")";
            }
        }
        String[] parameters = new String[]{title, guestInfo.guestName, roomID, serialService.getPaySerial(), reachTime, leaveTime, company, groupName, userService.getCurrentUser(), timeService.getNowLong(), ifNotNullGetString(consume), changeDebt, cancelMsg, account, roomIdAll, finalRoomPrice, ifNotNullGetString(deposit), ifNotNullGetString(totalRoomConsume), ifNotNullGetString(totalRoomShopConsume), ifNotNullGetString(otherConsume), guestInVip, ifNotNullGetString(fpMoney), guestOut.getRemark(), guestInfo.phone, guestInfo.sex, debtPayService.companySplit1, debtPayService.companySplit2};
        debtPayService.companySplit1 = null;
        debtPayService.companySplit2 = null;
        return reportService.generateReport(templateList, parameters, "guestOut", "pdf");
    }


    /**
     * 操作员记录
     */
    private void addUserLog(GuestOut guestOut, String changeDebt) throws Exception {
        String userAction = "离店结算:" + roomService.roomListToString(guestOut.getRoomIdList()) + changeDebt+" 离店序列号:"+serialService.getCheckOutSerial();
        if (guestOut.getGroupAccount() == null) {
            userLogService.addUserLog(userAction, userLogService.reception, userLogService.guestOut, serialService.getPaySerial());
        } else {
            userLogService.addUserLog(userAction, userLogService.reception, userLogService.guestOutGroup, serialService.getPaySerial());
        }
    }

    /**
     * 删除账务，开房信息，团队开房信息，在店宾客
     */
    private void delete(GuestOut guestOut) throws Exception {
        List<Debt> debtList = new ArrayList<>();
        List<String> roomList = guestOut.getRoomIdList();
        /*是否需要删除房价协议（自定义房价时需要删除）*/
        boolean deleteProtocol = otherParamService.getValueByName("可编辑房价").equals("y");
        for (String s : roomList) {
            debtList.addAll(debtService.getListByRoomId(s));
            CheckIn checkIn = checkInService.getByRoomId(s);//在店户籍
            List<CheckInGuest> checkInGuestList = checkInGuestService.getListByRoomId(s);
            checkInService.delete(checkIn);
            checkInGuestService.delete(checkInGuestList);
            /*删除房价协议*/
            if (deleteProtocol) {
                //先看看能不能查找到,团队房找不到，所以不用删
                Protocol protocol = protocolService.getByNameTemp(checkIn.getProtocol());
                if (protocol != null) {
                    protocolService.delete(protocol);
                }
            }
        }

        CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(guestOut.getGroupAccount());
        if (checkInGroup != null) {
            if (guestOut.getRoomIdList().size() != checkInGroup.getTotalRoom()) {//说明是结的部分房间
                CheckOutGroup checkOutGroup = checkOutGroupService.getByCheckOutSerial(serialService.getCheckOutSerial());
                checkOutGroup.setConsume(0.0);
                checkOutGroup.setDeposit(0.0);
                for (Debt debt : debtList) {
                    checkInGroup.setConsume(checkInGroup.getNotNullGroupConsume() - debt.getNotNullConsume());
                    checkInGroup.setDeposit(checkInGroup.getNotNullGroupDeposit() - debt.getNotNullDeposit());
                    checkOutGroup.setConsume(debt.getNotNullConsume());
                    checkOutGroup.setDeposit(debt.getNotNullDeposit());
                }
                checkInGroup.setTotalRoom(checkInGroup.getTotalRoom() - guestOut.getRoomIdList().size());
                checkOutGroup.setTotalRoom(guestOut.getRoomIdList().size());
                checkInGroupService.update(checkInGroup);
                checkOutGroupService.update(checkOutGroup);
            } else {
                checkInGroupService.delete(checkInGroup);
            }
        }
        debtService.delete(debtList);
        /*删除中间结算在debt_history表中产生的临时平账数据*/
        debtHistoryService.deleteMiddlePay();
    }

    /**
     * 补打结账单
     * 只有离店结算的结账记录才会传进来
     */
    @RequestMapping(value = "guestOutPrintAgain")
    public Integer guestOutPrintAgain(@RequestBody DebtPay debtPay) throws Exception {
        timeService.setNow();
        /*----------------------------先看是不是商品零售的补打----------------------------*/
        if (debtPayService.spls.equals(debtPay.getDebtCategory())) {
            /*商品零售报表
             * parameter
             * 1.操作员
             * 2.金额
             * 3.时间
             * 4.消费项目
             * 5.结账流水号
             * 6.币种
             * 7.酒店名称
             * field
             * 1.项目明细
             * */
            /*分析消费项目*/
            List<FieldTemplate> templateList = new ArrayList<>();
            List<DebtHistory> debtHistoryList = null;
            double totalConsume=0.0;
            try {
                debtHistoryList = debtHistoryService.getListByPaySerial(debtPay.getPaySerial());
            } catch (Exception e) {
                throw new Exception("结账信息丢失");
            }
            for (DebtHistory debtHistory : debtHistoryList) {
                FieldTemplate var = new FieldTemplate();
                var.setField1(debtHistory.getDescription());
                templateList.add(var);
                totalConsume+=debtHistory.getNotNullConsume();
            }
            return reportService.generateReport(templateList, new String[]{debtPay.getUserId(), String.valueOf(totalConsume), timeService.dateToStringLong(debtPay.getDoneTime()), "", "弃用", debtPay.getCurrency(), otherParamService.getValueByName("酒店名称")}, "retail", "pdf");
        }
        /*----------------------------商品零售逻辑完成----------------------------*/
        serialService.setPaySerial(debtPay.getPaySerial());
        serialService.setCheckOutSerial(debtPay.getCheckOutSerial());
        /*构造一个GuestOut*/
        GuestOut guestOut = new GuestOut();
        guestOut.setGroupAccount(debtPay.getGroupAccount());//公付账号，如果没有则是空
        GuestInfo guestInfo = new GuestInfo();
        /*获取发票金额*/
        if (debtPay.getCheckOutSerial() != null || debtPayService.yfjs.equals(debtPay.getDebtCategory())) {//中间结算的话为null不考虑
            guestOut.setAgain("补打");//注明是补打
            List<CheckOutRoom> checkOutRoomList = checkOutRoomService.getByCheckOutSerial(debtPay.getCheckOutSerial());
            guestOut.setRoomIdList(checkOutRoomService.simpleToString(checkOutRoomList));//房间字符串数组
            CheckOut checkOut = checkOutService.getByCheckOutSerial(debtPay.getCheckOutSerial());
            if ("y".equals(this.otherParamService.getValueByName("手写发票金额")) && debtPay.getCheckOutSerial() != null) {
                guestOut.setFpMoney(checkOut.getFpMoney());
            }
            guestOut.setRemark(checkOut.getRemark());
            List<CheckInHistory> checkInHistories = checkInHistoryService.getListByCheckOutSerial(debtPay.getCheckOutSerial());
            for (CheckInHistory checkInHistory : checkInHistories) {
                guestInfo.addGuestName(checkInHistory.getName());
                guestInfo.addPhone(checkInHistory.getPhone());
                guestInfo.addSex(checkInHistory.getSex());
            }
        } else {
            guestOut.setAgain("中间未结补打");//注明是补打
            guestOut.setRoomIdList(Arrays.asList(debtPay.getRoomId().split(",")));
            List<CheckInGuest> checkInGuestList = checkInGuestService.getListByRoomIdList(guestOut.getRoomIdList());
            for (CheckInGuest checkInGuest : checkInGuestList) {
                guestInfo.addGuestName(checkInGuest.getName());
                guestInfo.addPhone(checkInGuest.getPhone());
                guestInfo.addSex(checkInGuest.getSex());
            }
        }
        /*生成结账数组*/
        List<DebtPay> debtPayList = debtPayService.getListByPaySerial(debtPay.getPaySerial());
        List<CurrencyPost> currencyPostList = new ArrayList<>();
        String changeDebt = "";//转账信息
        for (DebtPay pay : debtPayList) {
            currencyPostList.add(new CurrencyPost(pay));
            changeDebt += "币种:" + pay.getCurrency() + "/" + pay.getDebtMoney();
            if ("转单位".equals(pay.getCurrency())) {
                String company;
                String lord;
                try {
                    company = pay.getCurrencyAdd().split(" ")[0];
                    lord = pay.getCurrencyAdd().split(" ")[1];
                } catch (Exception e) {
                    throw new Exception("该笔转单位没有输入单位");
                }
                changeDebt += "转单位至：" + company + " 签单人：" + lord;
                /*尝试分隔单位符号*/
                String[] afterSplit = company.split("/");
                if (afterSplit.length > 1) {
                    debtPayService.companySplit1 = afterSplit[0];
                    debtPayService.companySplit2 = afterSplit[1];
                }
                break;
            }
        }
        guestOut.setCurrencyPayList(currencyPostList);
        guestOut.setPaySerial(debtPay.getPaySerial());
        guestOut.setCheckOutSerial(debtPay.getCheckOutSerial());
        return reportProcess(guestOut, guestInfo, changeDebt, null);
    }

    /**
     * 叫回账单
     */
    @RequestMapping(value = "checkOutReverse")
    @Transactional(rollbackFor = Exception.class)
    public Integer checkOutReverse(@RequestBody DebtPay debtPay) throws Exception {
        String groupAccount = debtPay.getGroupAccount();
        String paySerial = debtPay.getPaySerial();
        String checkOutSerial = debtPay.getCheckOutSerial();
        List<String> selfAccountList = new ArrayList<>();
        if (groupAccount != null) {
            List<CheckInHistoryLog> checkInHistoryLogList = checkInHistoryLogService.getByCheckOutSerial(checkOutSerial);
            for (CheckInHistoryLog checkInHistoryLog : checkInHistoryLogList) {
                selfAccountList.add(checkInHistoryLog.getSelfAccount());
            }
        } else if (debtPay.getSelfAccount()!=null){
            selfAccountList.add(debtPay.getSelfAccount());
        }
        timeService.setNow();
        /*如果有会员标志，先获取，方便之后查看余额什么的*/
        Vip vip = null;
        if (debtPay.getVipNumber() != null) {
            vip = vipService.get(new Query("vip_number=" + util.wrapWithBrackets(debtPay.getVipNumber()))).get(0);
        }
        /*当前房间状态检验，通过的话更新房态，同时生成checkIn*/
        List<CheckOutRoom> checkOutRoomList = checkOutRoomService.getByCheckOutSerial(checkOutSerial);
        StringBuilder roomString= new StringBuilder();
        for (CheckOutRoom checkOutRoom : checkOutRoomList) {
            Room room = roomService.getByRoomId(checkOutRoom.getRoomId());
            if (room.getState().equals(roomService.group) || room.getState().equals(roomService.guest) || room.getState().equals(roomService.repair)) {
                throw new Exception("该房间在住，无法结账");
            }
            roomString.append(room.getRoomId()).append(",");
            if (groupAccount == null) {
                room.setState(roomService.guest);
            } else {
                room.setState(roomService.group);
            }
            room.setCheckOutTime(null);
            CheckIn checkIn = new CheckIn();
            checkIn.setRoomId(room.getRoomId());
            roomService.update(room);
        }
        /*离店信息删除*/
        CheckOut checkOutQuery = new CheckOut();
        checkOutQuery.setCheckOutSerial(checkOutSerial);
        checkOutService.delete(checkOutQuery);
        /*删除加收房租*/
        debtHistoryService.deleteAddDebt(paySerial);
        /*账务历史迁移*/
        List<DebtHistory> debtHistoryList = debtHistoryService.getListExcludeAddDebt(paySerial);
        List<Debt> debtList = new ArrayList<>();
        Double vipRemain = 0.0;
        if (vip != null) {
            vipRemain = vip.getNotNullVipRemain();
        }
        for (DebtHistory debtHistory : debtHistoryList) {
            if (debtHistory.getNotNullCompanyPaid()) {
                throw new Exception("该房间转单位账已经参与单位结算，无法叫回");
            }
            Debt debt = new Debt(debtHistory);
            debt.setPaySerial(null);
            debtList.add(debt);
            /*判断押金币种，如果是会员还要重新冻结*/
            if (currencyService.HY.equals(debt.getCurrency())) {
                if (vipRemain < debt.getDeposit()) {
                    throw new Exception("该房采用会员押金方式，会员押金余额不足，无法提供押金，叫回失败");
                }
                vipRemain -= debt.getDeposit();
                vipService.depositByVip(debt.getVipNumber(), debt.getDeposit());
            }
        }
        debtService.add(debtList);
        debtHistoryService.delete(debtHistoryList);
        /*处理结账币种信息*/
        List<DebtPay> debtPayList = debtPayService.getListByPaySerial(debtPay.getPaySerial());
        for (DebtPay pay : debtPayList) {
            String currency = pay.getCurrency();
            String currencyAdd = pay.getCurrencyAdd();
            Double money = pay.getDebtMoney();
            debtPayService.cancelPay(currency, currencyAdd, money, pay.getPaySerial(), "接待", "接待");
            if (debtPay.getVipNumber() != null && currencyService.get(new Query("currency=" + util.wrapWithBrackets(currency))).get(0).getNotNullScore()) {//有会员卡号并且币种是积分币种
                vipService.updateVipScore(debtPay.getVipNumber(), money);
            }
        }
        debtPayService.delete(debtPayList);
        /*判断单位协议，去掉协议消费*/
        if (debtPay.getCompany() != null) {
            companyService.addConsume(debtPay.getCompany(), -debtPay.getDebtMoney());
        }
        /*逆向户籍转换，删除宾客历史，同时生成checkIn和checkInGroup(checkInHistory可以不删，反正总是要结账的)*/
        checkOutRoomService.delete(checkOutRoomList);
        List<CheckInHistoryLog> checkInHistoryLogList = checkInHistoryLogService.get(new Query("check_out_serial=" + util.wrapWithBrackets(checkOutSerial)));
        List<CheckIn> checkInList = new ArrayList<>();
        for (CheckInHistoryLog checkInHistoryLog : checkInHistoryLogList) {
            CheckIn checkIn = new CheckIn(checkInHistoryLog);
            checkIn.setConsume(debtService.getTotalConsumeByRoomId(checkIn.getRoomId()));
            checkInList.add(checkIn);
        }
        checkInService.add(checkInList);//生成在店户籍
        if (debtPay.getGroupAccount() != null) {//如果是团队开房的话，生成团队信息
            /*先判断是不是彻底离店了*/
            CheckInGroup checkInGroupNow = checkInGroupService.getByGroupAccount(groupAccount);
            if (checkInGroupNow == null) {
                CheckInGroup checkInGroup = new CheckInGroup(checkOutGroupService.getByCheckOutSerial(checkOutSerial));
                checkInGroup.setConsume(debtService.getTotalConsumeByGroupAccount(groupAccount));
                checkInGroupService.add(checkInGroup);
            } else {
                CheckOutGroup checkOutGroup = checkOutGroupService.getByCheckOutSerial(checkOutSerial);
                checkInGroupNow.setConsume(checkInGroupNow.getConsume() + checkOutGroup.getConsume());
                checkInGroupNow.setDeposit(checkInGroupNow.getDeposit() + checkOutGroup.getDeposit());
                checkInGroupNow.setTotalRoom(checkInGroupNow.getTotalRoom() + checkOutGroup.getTotalRoom());
                checkInGroupService.update(checkInGroupNow);
                checkOutGroupService.delete(checkOutGroup);
            }
        }
        for (String selfAccount : selfAccountList) {
            List<GuestIntegration> guestIntegrationList = guestIntegrationService.getList(selfAccount);
            List<CheckInGuest> checkInGuestList = new ArrayList<>();
            for (GuestIntegration guestIntegration : guestIntegrationList) {
                CheckInHistory checkInHistory = checkInHistoryService.getByCardId(guestIntegration.getCardId());
                CheckInGuest checkInGuest = new CheckInGuest(checkInHistory);
                CheckInHistoryLog checkInHistoryLog = checkInHistoryLogService.getBySelfAccountAndCheckOutSerial(selfAccount, checkOutSerial);
                checkInGuest.setRoomId(checkInHistoryLog.getRoomId());
                checkInGuestList.add(checkInGuest);
                checkInHistory.setNum(checkInHistory.getNum() - 1);
                checkInHistoryService.update(checkInHistory);
            }
            checkInGuestService.add(checkInGuestList);
        }
        checkInHistoryLogService.delete(checkInHistoryLogList);
        /*操作员记录*/
        userLogService.addUserLog("叫回账单:" + checkOutSerial+" 房号:"+roomString.toString(), userLogService.reception, userLogService.guestOutReverse, checkOutSerial);
        return 0;
    }

    /**
     * 消费查询单
     */
    @RequestMapping(value = "guestOutQuery")
    public Integer guestOutQuery(@RequestBody GuestOut guestOut) throws Exception {
        timeService.setNow();
        List<Debt> debtList = new ArrayList<>();
        for (String s : guestOut.getRoomIdList()) {
            debtList.addAll(debtService.getListByRoomId(s));
        }

        List<CheckInGuest> checkInGuestList = checkInGuestService.getListByRoomIdList(guestOut.getRoomIdList());
        GuestInfo guestInfo = new GuestInfo();
        for (CheckInGuest checkInGuest : checkInGuestList) {
            guestInfo.addGuestName(checkInGuest.getName());
            guestInfo.addPhone(checkInGuest.getPhone());
            guestInfo.addSex(checkInGuest.getSex());
        }
        return this.reportProcess(guestOut, guestInfo, null, debtList);
    }

    /**
     * 传给账单打印函数用的实体类
     */
    private class GuestInfo {
        private String guestName = "";
        private String sex = "";
        private String phone = "";

        public void addAll(GuestInfo guestInfo) {
            if (guestName == null) {
                guestName = "";
            }
            this.guestName += guestInfo.guestName;
            if (sex == null) {
                sex = "";
            }
            this.sex += guestInfo.sex;
            if (phone == null) {
                phone = "";
            }
            this.phone += guestInfo.phone;
        }

        public void addGuestName(String guestName) {
            if (guestName == null) {
                guestName = "";
            }
            this.guestName += guestName + ",";
        }

        public void addSex(String sex) {
            if (sex == null) {
                sex = "";
            }
            this.sex += sex + ",";
        }

        public void addPhone(String phone) {
            if (phone == null) {
                phone = "";
            }
            this.phone += phone + ",";
        }
    }
}
