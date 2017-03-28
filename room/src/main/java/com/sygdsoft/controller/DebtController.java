package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class DebtController {
    @Autowired
    DebtService debtService;
    @Autowired
    ReportService reportService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    SerialService serialService;
    @Autowired
    RoomService roomService;
    @Autowired
    UserService userService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    VipService vipService;
    @Autowired
    CompanyService companyService;
    @Autowired
    FreemanService freemanService;
    @Autowired
    RoomShopDetailService roomShopDetailService;
    @Autowired
    CheckInService checkInService;

    //TODO: 发现bug之后就可以删了
    private static final Logger logger = LoggerFactory.getLogger(DebtController.class);

    @RequestMapping(value = "debtGet")
    public List<Debt> debtGet(@RequestBody Query query) throws Exception {
        return debtService.get(query);
    }

    /**
     * 单退预付
     */
    @RequestMapping(value = "cancelDepositSingle")
    @Transactional(rollbackFor = Exception.class)
    public void cancelDepositSingle(@RequestBody Debt debt) throws Exception{
        timeService.setNow();
        debt.setDoTime(timeService.getNow());
        debt.setUserId(userService.getCurrentUser());
        debtService.addDebt(debt);
    }

    /**
     * 整退预付
     */
    @RequestMapping(value = "cancelDeposit")
    @Transactional(rollbackFor = Exception.class)
    public void cancelDeposit(@RequestBody List<Debt> debtList) throws Exception {
        Date now = timeService.setNow();
        for (Debt debt : debtList) {
            debt.setRemark("已退");
        }
        debtService.update(debtList);
        for (Debt debt : debtList) {
            debt.setId(null);
            debt.setDoTime(now);
            debt.setDeposit(-debt.getDeposit());
            debt.setDescription("退预付");
            debt.setCategory(debtService.cancelDeposit);
            debtService.updateGuestInMoney(debt.getRoomId(), debt.getConsume(), debt.getDeposit());
            if (currencyService.HY.equals(debt.getCurrency())) {//解冻
                vipService.depositByVip(debt.getVipNumber(), -debt.getDeposit());
            }
        }
        debtService.add(debtList);
    }

    /**
     * 房吧录入
     */
    @RequestMapping(value = "roomShopIn")
    @Transactional(rollbackFor = Exception.class)
    public Integer roomShopIn(@RequestBody RoomShopIn roomShopIn) throws Exception {
        /*解析传进来的参数*/
        String roomId = roomShopIn.getRoomId();
        String money = roomShopIn.getMoney();
        String description = roomShopIn.getDescription();
        String bed = roomShopIn.getBed();
        timeService.setNow();
        Debt debt = new Debt();
        debt.setRoomId(roomId);
        debt.setDescription(description);
        debt.setCurrency("挂账");
        debt.setPointOfSale(pointOfSaleService.FB);
        debt.setConsume(Double.valueOf(money));
        debt.setBed(bed);
        debt.setCategory(debtService.roomShopIn);
        debt.setUserId(userService.getCurrentUser());
        //TODO: 发现bug之后就可以删了
        CheckIn checkIn = checkInService.getByRoomId(roomId);
        logger.info(timeService.getNowLong()+":"+"房吧入账前checkIn消费:"+checkIn.getConsume()+"房吧消费:"+Double.valueOf(money)+"操作员:"+userService.getCurrentUser());
        debtService.addDebt(debt);
        checkIn = checkInService.getByRoomId(roomId);
        logger.info(timeService.getNowLong()+":"+"房吧入账前checkIn消费:"+checkIn.getConsume());
        /*创建房吧明细账务*/
        List<RoomShopDetail> roomShopDetailList = roomShopIn.getRoomShopDetailList();
        String selfAccount = checkInService.getSelfAccount(roomId);
        for (RoomShopDetail roomShopDetail : roomShopDetailList) {
            roomShopDetail.setDoTime(timeService.getNow());
            roomShopDetail.setUserId(userService.getCurrentUser());
            roomShopDetail.setSelfAccount(selfAccount);
        }
        roomShopDetailService.add(roomShopDetailList);
        return 1;
    }

    /**
     * 杂单冲账(客房)
     */
    @RequestMapping(value = "otherConsumeRoom")
    @Transactional(rollbackFor = Exception.class)
    public Integer addOtherConsumeRoom(@RequestBody Debt debt) throws Exception {
        String msg;
        if (debt.getConsume() > 0) {
            msg = "杂单";
            debt.setCategory(debtService.otherConsumeIn);
        } else {
            debt.setCategory(debtService.otherConsumeOut);
            msg = "冲账";
        }
        timeService.setNow();
        debtService.addDebt(debt);
        userLogService.addUserLog("杂单冲账:" + debt.getRoomId() + " 金额:" + debt.getConsume(), userLogService.reception, userLogService.ZC, debt.getSelfAccount());
        /*创建杂单报表
        * 1.操作员
        * 2.金额
        * 3.时间
        * 4.消费项目
        * 5.备注
        * 6.杂单还是冲账
        * 7.酒店名称
        * 8.房号
        * 9.床位
        * */
        return reportService.generateReport(null, new String[]{userService.getCurrentUser(), String.valueOf(debt.getConsume()), timeService.getNowLong(), debt.getPointOfSale(), debt.getDescription(), msg, otherParamService.getValueByName("酒店名称"), debt.getRoomId(), debt.getBed()}, "otherConsume", "pdf");
    }

    /**
     * 杂单冲账(单位)
     */
    @RequestMapping(value = "otherConsumeCompany")
    @Transactional(rollbackFor = Exception.class)
    public Integer addOtherConsumeCompany(@RequestBody DebtHistory debtHistory) throws Exception {
        serialService.setPaySerial();
        timeService.setNow();
        /*提取单位信息*/
        String company = debtHistory.getCurrency();
        String companyLord = debtHistory.getCurrencyAdd();
        debtHistory.setCurrency("挂账");
        debtHistory.setCurrencyAdd(null);
        debtHistory.setPaySerial(serialService.getPaySerial());
        debtHistory.setUserId(userService.getCurrentUser());
        debtHistory.setCompany(company);
        Double money = debtHistory.getConsume();
        /*生成一条结账记录*/
        DebtPay debtPay = new DebtPay();
        debtPay.setPaySerial(serialService.getPaySerial());
        debtPay.setDebtMoney(money);
        debtPay.setCurrency("转单位");
        debtPay.setCurrencyAdd(company + " " + companyLord);
        debtPay.setDoneTime(debtHistory.getDoneTime());
        debtPay.setDebtCategory(debtHistory.getCategory());
        debtPay.setDescription(debtHistory.getDescription());
        debtPay.setPointOfSale(debtHistory.getPointOfSale());
        debtPay.setUserId(debtHistory.getUserId());
        debtHistory.setPaySerial(serialService.getPaySerial());
        debtPayService.add(debtPay);
        debtHistoryService.add(debtHistory);
        timeService.setNow();
        userLogService.addUserLog("杂单冲账单位:" + company + " 金额:" + money, userLogService.reception, userLogService.ZC, serialService.getPaySerial());
        /*判断币种*/
        debtPayService.parseCurrency("转单位", company + " " + companyLord, money, null, null, debtHistory.getCategory(), serialService.getPaySerial(), "接待",debtHistory.getPointOfSale());
        /*创建杂单报表
        * 1.操作员
        * 2.金额
        * 3.时间
        * 4.消费项目
        * 5.备注
        * 6.杂单还是冲账
        * 7.酒店名称
        * 8.房号/单位
        * 9.床位
        * */
        return reportService.generateReport(null, new String[]{userService.getCurrentUser(), String.valueOf(money), timeService.getNowLong(), debtHistory.getPointOfSale(), debtHistory.getDescription(), debtHistory.getCategory(), otherParamService.getValueByName("酒店名称"), company + "/" + companyLord,}, "otherConsume", "pdf");
    }

    /**
     * 商品零售，记入账务历史,和结账列表
     */
    @RequestMapping(value = "retailIn")
    @Transactional(rollbackFor = Exception.class)
    public Integer retailIn(@RequestBody RetailIn retailIn) throws Exception {
        serialService.setPaySerial();
        timeService.setNow();
        DebtHistory debtHistory = retailIn.getDebtHistory();
        String currency = debtHistory.getCurrency();
        String currencyAdd = debtHistory.getCurrencyAdd();
        Double money = debtHistory.getConsume();
        /*判断是不是转单位*/
        if("转单位".equals(currency)){
            debtHistory.setCompany(currencyAdd.split(" ")[0]);
        }
        /*生成一条结账记录*/
        DebtPay debtPay = new DebtPay();
        debtPay.setPaySerial(serialService.getPaySerial());
        debtPay.setDebtMoney(money);
        debtPay.setCurrency(currency);
        debtPay.setCurrencyAdd(currencyAdd);
        debtPay.setDoneTime(debtHistory.getDoneTime());
        debtPay.setDebtCategory("商品零售");
        debtPay.setDescription(debtHistory.getDescription());
        debtPay.setPointOfSale(debtHistory.getPointOfSale());
        debtPay.setUserId(debtHistory.getUserId());
        debtHistory.setPaySerial(serialService.getPaySerial());
        debtPayService.add(debtPay);
        debtHistoryService.add(debtHistory);
        /*判断币种*/
        debtPayService.parseCurrency(currency, currencyAdd, money, null, null, "商品零售", serialService.getPaySerial(), "接待",null);
        /*创建房吧明细账务*/
        List<RoomShopDetail> roomShopDetailList = retailIn.getRoomShopDetailList();
        for (RoomShopDetail roomShopDetail : roomShopDetailList) {
            roomShopDetail.setDoTime(timeService.getNow());
            roomShopDetail.setPaySerial(serialService.getPaySerial());
            roomShopDetail.setRoom("零售");
            roomShopDetail.setUserId(userService.getCurrentUser());
        }
        roomShopDetailService.add(roomShopDetailList);
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
        String[] itemArray = debtHistory.getDescription().split("/");
        for (String s : itemArray) {
            FieldTemplate var = new FieldTemplate();
            var.setField1(s);
            templateList.add(var);
        }
        return reportService.generateReport(templateList, new String[]{userService.getCurrentUser(), String.valueOf(debtHistory.getConsume()), timeService.getNowLong(), debtHistory.getDescription(), serialService.getPaySerial(), debtHistory.getCurrency(), otherParamService.getValueByName("酒店名称")}, "retail", "pdf");
    }

}
