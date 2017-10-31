package com.sygdsoft.controller.room;

import com.sygdsoft.model.CompanyPay;
import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.model.HotelParseRow;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.room.DebtAndPayReturn;
import com.sygdsoft.model.room.DebtAndPayRow;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
@RestController
public class DebtAndPayReportController {
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    HotelParseController hotelParseController;
    @Autowired
    DailyReportController dailyReportController;
    @Autowired
    SzMath szMath;
    @Autowired
    DebtHistoryService debtHistoryService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    VipIntegrationService vipIntegrationService;
    @Autowired
    CompanyPayService companyPayService;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    DebtPayService debtPayService;

    @RequestMapping(value = "debtAndPayReport")
    public DebtAndPayReturn debtAndPayReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTimeHistory = timeService.stringToDateShort("1990-01-31");
        Date beginTime = timeService.getMinTime(reportJson.getBeginTime());
        Date endTime = timeService.getMaxTime(reportJson.getEndTime());
        List<DebtAndPayRow> debtAndPayRowList = new ArrayList<>();
        DebtAndPayRow roomTotal = new DebtAndPayRow("客房总计");
        debtAndPayRowList.add(roomTotal);
        List<String> pointOfSaleList = pointOfSaleService.getRoomPointOfSaleList();
        for (String pointOfSale : pointOfSaleList) {
            DebtAndPayRow debtAndPayRow = new DebtAndPayRow();
            debtAndPayRow.setPointOfSale(pointOfSale);
            /*计算期初未结=期初之前的总账务-结算的*/
            Double generate = szMath.nullToZero(debtIntegrationService.getSumConsumeByDoTime(beginTimeHistory, beginTime, pointOfSale,false));
            Double paid = szMath.nullToZero(debtHistoryService.getHistoryConsume(beginTimeHistory, beginTime, pointOfSale,false));
            debtAndPayRow.setUndoneBefore(szMath.formatTwoDecimalReturnDouble(generate - paid));
            roomTotal.setUndoneBefore(szMath.formatTwoDecimalReturnDouble(roomTotal.getUndoneBefore() + debtAndPayRow.getUndoneBefore()));
            /*计算期间发生*/
            debtAndPayRow.setDebt(szMath.formatTwoDecimalReturnDouble(debtIntegrationService.getSumConsumeByDoTime(beginTime, endTime, pointOfSale,false)));
            roomTotal.setDebt(szMath.formatTwoDecimalReturnDouble(roomTotal.getDebt() + debtAndPayRow.getDebt()));
            /*计算期末未结*/
            generate = szMath.nullToZero(debtIntegrationService.getSumConsumeByDoTime(beginTimeHistory, endTime, pointOfSale,false));
            paid = szMath.nullToZero(debtHistoryService.getHistoryConsume(beginTimeHistory, endTime, pointOfSale,false));
            debtAndPayRow.setUndoneLast(szMath.formatTwoDecimalReturnDouble(generate - paid));
            roomTotal.setUndoneLast(szMath.formatTwoDecimalReturnDouble(roomTotal.getUndoneLast() + debtAndPayRow.getUndoneLast()));
            debtAndPayRowList.add(debtAndPayRow);
        }
        /*计算期间结算=总结算款-转房客*/
        Double totalPay = szMath.nullToZero(debtPayService.getDebtMoney(null, null, false, beginTime, endTime));
        //Double toRoomPay = szMath.nullToZero(debtPayService.getDebtMoney(null, "转房客", false, beginTime, endTime));
        roomTotal.setDebtPay(szMath.formatTwoDecimalReturnDouble(totalPay ));
        /*计算转单位*/
        roomTotal.setToCompany(szMath.formatTwoDecimalReturnDouble(szMath.nullToZero(debtPayService.getDebtMoney(null, "转单位", false, beginTime, endTime))));
        /*计算转哑房*/
        roomTotal.setLost(szMath.formatTwoDecimalReturnDouble(szMath.nullToZero(debtPayService.getDebtMoney(null, "转哑房", false, beginTime, endTime))));
        DebtAndPayReturn debtAndPayReturn = new DebtAndPayReturn();
        /*设置会员充值*/
        debtAndPayReturn.setVipPay(szMath.nullToZero(vipIntegrationService.getTotalPay(beginTime, endTime)));
        /*设置单位回款和抵用*/
        CompanyPay companyPayQuery = companyPayService.getSumPay(null, null, null, beginTime, endTime);
        if (companyPayQuery == null) {
            debtAndPayReturn.setCompanyPay(0.0);
            debtAndPayReturn.setCompanyDebt(0.0);
        } else {
            debtAndPayReturn.setCompanyPay(companyPayQuery.getPay());
            debtAndPayReturn.setCompanyDebt(companyPayQuery.getDebt());
        }
        debtAndPayReturn.setDebtAndPayRowList(debtAndPayRowList);
        return debtAndPayReturn;
    }
}
