package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.jsonModel.QuerySubReport;
import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
@RestController
public class DebtHistoryController {
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    GuestIntegrationService guestIntegrationService;
    @Autowired
    ReportService reportService;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;
    @Autowired
    CheckInHistoryService checkInHistoryService;
    @Autowired
    TimeService timeService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    Util util;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "debtHistoryGet")
    public List<DebtHistory> debtHistoryGet(@RequestBody Query query) throws Exception {
        return debtHistoryService.get(query);
    }

    @RequestMapping(value = "debtHistoryGetByCheckOutSerial")
    public List<DebtHistory> debtHistoryGetByCheckOutSerial(@RequestBody OnlyString string){
        String checkOutSerial=string.getData();
        return debtHistoryService.debtHistoryGetByCheckOutSerial(checkOutSerial);
    }

    /**
     * 根结账表联表查询押金，带操作员
     */
    @RequestMapping(value = "getDebtHistoryByPayUser")
    public List<DebtHistory> getDebtHistoryByPayUser(@RequestBody ReportJson reportJson){
        String userId=reportJson.getUserId();
        if("".equals(userId)){
            userId=null;
        }
        String currency=reportJson.getCurrency();
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        List<DebtHistory> debtHistoryList=debtHistoryService.getCancelDeposit(userId, currency, beginTime, endTime);
        for (DebtHistory debtHistory : debtHistoryList) {
            debtHistory.setGuestName(guestIntegrationService.getGuestNameBySelfAccount(debtHistory.getSelfAccount()));
        }
        return debtHistoryList;
    }

    /**
     * 根据结账序列号获取押金总和
     */
    @RequestMapping("getTotalDepositByPaySerial")
    public Double getTotalDepositByPaySerial(@RequestBody String paySerial){
        return debtHistoryService.getTotalDeposit(paySerial);
    }

    /**
     * 离店后补打押金单
     */
    @RequestMapping("depositDonePrintAgain")
    public Integer depositDonePrintAgain(@RequestBody DebtHistory debtHistory)throws Exception{
        try {
            CheckInHistoryLog checkInHistoryLog=checkInHistoryLogService.getOneBySelfAccount(debtHistory.getSelfAccount());
            List<CheckInHistory> checkInHistoryList = checkInHistoryService.getListBySelfAccount(checkInHistoryLog.getSelfAccount());
            CheckInHistory checkInHistory=checkInHistoryList.get(0);
            String guestNameString=checkInHistoryService.listToStringName(checkInHistoryList);
            return reportService.generateReport(null, new String[]{guestNameString, checkInHistory.getSex(), timeService.dateToStringShort(checkInHistory.getBirthdayTime()), checkInHistory.getCardType(), checkInHistory.getCardId(), timeService.dateToStringLong(checkInHistoryLog.getReachTime()), timeService.dateToStringLong(checkInHistoryLog.getLeaveTime()), checkInHistoryLog.getRoomId(), String.valueOf(checkInHistoryLog.getFinalRoomPrice()), checkInHistoryLog.getVipNumber(), checkInHistory.getAddress(), debtHistory.getCurrency(), checkInHistoryLog.getImportant(), checkInHistoryLog.getRemark(), timeService.getNowLong(), debtHistory.getUserId(), debtHistory.getSelfAccount(), String.valueOf(debtHistory.getDeposit()), checkInHistoryLog.getCompany(), checkInHistoryLog.getRoomPriceCategory(), checkInHistoryLog.getProtocol(), checkInHistoryLog.getBreakfast(), otherParamService.getValueByName("酒店名称"), util.number2CNMontrayUnit(BigDecimal.valueOf(debtHistory.getDeposit())), "补打","",checkInHistoryLog.getGuestSource(),szMath.formatTwoDecimal(szMath.nullToZero(debtHistory.getDeposit())-szMath.nullToZero(checkInHistoryLog.getFinalRoomPrice())),checkInHistoryLog.getRoomCategory()}, "deposit", "pdf");
        } catch (Exception e) {
            throw new Exception("无法获取客人信息");
        }
    }
}
