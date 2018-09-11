package com.sygdsoft.controller.especially;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class HuayuanDailyParseController {
    @Autowired
    ReportService reportService;
    @Autowired
    GuestSourceService guestSourceService;
    @Autowired
    DeskGuestSourceService deskGuestSourceService;
    @Autowired
    TimeService timeService;
    @Autowired
    ReportStoreService reportStoreService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    SzMath szMath;
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    HuaYuanService huaYuanService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    RoomSnapshotService roomSnapshotService;
    @Autowired
    PayPointOfSaleService payPointOfSaleService;

    @RequestMapping(value = "huayuanDailyParseReport")
    public int huayuanDailyParseReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime1 = timeService.getMinTime(reportJson.getBeginTime());
        Date beginTime2 = timeService.getMinMonth(beginTime1);
        Date beginTime3 = timeService.getMinYear(beginTime1);
        Date endTime1 = timeService.getMaxTime(reportJson.getBeginTime());
        Date endTime2 = timeService.getMaxMonth(endTime1);
        Date endTime3 = timeService.getMaxYear(endTime1);
        JSONArray jsonArrayAll = new JSONArray();
        JSONObject row;
        JSONObject rowBack1;
        JSONObject rowBack2;
        JSONObject rowBack3;
        List<GuestSource> guestSourceList = guestSourceService.get();
        List<DeskGuestSource> deskGuestSourceList = deskGuestSourceService.get();
        //------------------------------客源-------------------------------------
        boolean firstRow = true;
        JSONArray jsonArrayNow = new JSONArray();
        for (GuestSource guestSource : guestSourceList) {
            row = new JSONObject();
            if (firstRow) {
                firstRow = false;
                row.put("1", "客源");
            }
            row.put("2", guestSource.getGuestSource());

            row.put("3", checkInIntegrationService.getSumCount(beginTime1, endTime1, guestSource.getGuestSource(), null));
            row.put("6", checkInIntegrationService.getSumCount(beginTime2, endTime2, guestSource.getGuestSource(), null));
            row.put("9", checkInIntegrationService.getSumCount(beginTime3, endTime3, guestSource.getGuestSource(), null));

            row.put("4", debtIntegrationService.getSumConsumeByDoTime(beginTime1, endTime1, null, guestSource.getGuestSource(), true));
            row.put("7", debtIntegrationService.getSumConsumeByDoTime(beginTime2, endTime2, null, guestSource.getGuestSource(), true));
            row.put("10", debtIntegrationService.getSumConsumeByDoTime(beginTime3, endTime3, null, guestSource.getGuestSource(), true));

            row.put("5", szMath.formatTwoDecimal(row.getDouble("4"), row.getDouble("3")));
            row.put("8", szMath.formatTwoDecimal(row.getDouble("7"), row.getDouble("6")));
            row.put("11", szMath.formatTwoDecimal(row.getDouble("10"), row.getDouble("9")));

            jsonArrayNow.add(row);
        }
        row = new JSONObject();
        row.put("2", "客房未选择客源");

        row.put("3", checkInIntegrationService.getSumCount(beginTime1, endTime1, "NULL", null));
        row.put("6", checkInIntegrationService.getSumCount(beginTime2, endTime2, "NULL", null));
        row.put("9", checkInIntegrationService.getSumCount(beginTime3, endTime3, "NULL", null));

        row.put("4", debtIntegrationService.getSumConsumeByDoTime(beginTime1, endTime1, null, "NULL", true));
        row.put("7", debtIntegrationService.getSumConsumeByDoTime(beginTime2, endTime2, null, "NULL", true));
        row.put("10", debtIntegrationService.getSumConsumeByDoTime(beginTime3, endTime3, null, "NULL", true));

        row.put("5", szMath.formatTwoDecimal(row.getDouble("4"), row.getDouble("3")));
        row.put("8", szMath.formatTwoDecimal(row.getDouble("7"), row.getDouble("6")));
        row.put("11", szMath.formatTwoDecimal(row.getDouble("10"), row.getDouble("9")));

        jsonArrayNow.add(row);
        rowBack1 = calculateSum("客房小计", jsonArrayNow, jsonArrayAll);
        jsonArrayNow = new JSONArray();
        for (DeskGuestSource deskGuestSource : deskGuestSourceList) {
            row = new JSONObject();
            row.put("2", deskGuestSource.getName());

            row.put("3", huaYuanService.getDeskNum(beginTime1, endTime1, deskGuestSource.getName()));
            row.put("6", huaYuanService.getDeskNum(beginTime2, endTime2, deskGuestSource.getName()));
            row.put("9", huaYuanService.getDeskNum(beginTime3, endTime3, deskGuestSource.getName()));

            row.put("4", huaYuanService.getEatGuestSourceConsume(beginTime1, endTime1, deskGuestSource.getName()));
            row.put("7", huaYuanService.getEatGuestSourceConsume(beginTime2, endTime2, deskGuestSource.getName()));
            row.put("10", huaYuanService.getEatGuestSourceConsume(beginTime3, endTime3, deskGuestSource.getName()));

            row.put("5", szMath.formatTwoDecimal(row.getDouble("4"), row.getDouble("3")));
            row.put("8", szMath.formatTwoDecimal(row.getDouble("7"), row.getDouble("6")));
            row.put("11", szMath.formatTwoDecimal(row.getDouble("10"), row.getDouble("9")));

            jsonArrayNow.add(row);
        }

        row = new JSONObject();
        row.put("2", "餐饮未选择客源");

        row.put("3", huaYuanService.getDeskNum(beginTime1, endTime1, "NULL"));
        row.put("6", huaYuanService.getDeskNum(beginTime2, endTime2, "NULL"));
        row.put("9", huaYuanService.getDeskNum(beginTime3, endTime3, "NULL"));

        row.put("4", huaYuanService.getEatGuestSourceConsume(beginTime1, endTime1, "NULL"));
        row.put("7", huaYuanService.getEatGuestSourceConsume(beginTime2, endTime2, "NULL"));
        row.put("10", huaYuanService.getEatGuestSourceConsume(beginTime3, endTime3, "NULL"));

        row.put("5", szMath.formatTwoDecimal(row.getDouble("4"), row.getDouble("3")));
        row.put("8", szMath.formatTwoDecimal(row.getDouble("7"), row.getDouble("6")));
        row.put("11", szMath.formatTwoDecimal(row.getDouble("10"), row.getDouble("9")));

        jsonArrayNow.add(row);

        row = calculateSum("餐厅小计", jsonArrayNow, jsonArrayAll);

        jsonArrayNow = new JSONArray();
        jsonArrayNow.add(row);
        jsonArrayNow.add(rowBack1);

        rowBack2 = addConsume("合计", jsonArrayNow, jsonArrayAll);
        //------------------------------房态-------------------------------------
        jsonArrayNow = new JSONArray();
        row = new JSONObject();
        row.put("1", "房态");
        row.put("2", "房间总数");
        row.put("3", roomSnapshotService.getCount(beginTime1, endTime1, null, null));
        row.put("6", roomSnapshotService.getCount(beginTime2, endTime2, null, null));
        row.put("9", roomSnapshotService.getCount(beginTime3, endTime3, null, null));
        jsonArrayNow.add(row);
        row = new JSONObject();
        row.put("2", "可用房间");
        row.put("3", roomSnapshotService.getCount(beginTime1, endTime1, null, true));
        row.put("6", roomSnapshotService.getCount(beginTime2, endTime2, null, true));
        row.put("9", roomSnapshotService.getCount(beginTime3, endTime3, null, true));
        jsonArrayNow.add(row);
        row = new JSONObject();
        row.put("2", "维修房");
        row.put("3", roomSnapshotService.getCount(beginTime1, endTime1, "维修房", null));
        row.put("6", roomSnapshotService.getCount(beginTime2, endTime2, "维修房", null));
        row.put("9", roomSnapshotService.getCount(beginTime3, endTime3, "维修房", null));
        jsonArrayNow.add(row);

        RoomSnapshot roomSnapshotDay = roomSnapshotService.getSumByDate(beginTime1, endTime1, null);
        RoomSnapshot roomSnapshotMonth = roomSnapshotService.getSumByDate(beginTime2, endTime2, null);
        RoomSnapshot roomSnapshotYear = roomSnapshotService.getSumByDate(beginTime3, endTime3, null);

        row = new JSONObject();
        row.put("2", "平均房价");
        row.put("4", szMath.formatTwoDecimalReturnDouble(roomSnapshotDay.getAllDayRoomConsume() + roomSnapshotDay.getNightRoomConsume(), roomSnapshotDay.getSumAllDayRoom() + roomSnapshotDay.getSumNightRoom()));
        row.put("7", szMath.formatTwoDecimalReturnDouble(roomSnapshotMonth.getAllDayRoomConsume() + roomSnapshotMonth.getNightRoomConsume(), roomSnapshotMonth.getSumAllDayRoom() + roomSnapshotMonth.getSumNightRoom()));
        row.put("10", szMath.formatTwoDecimalReturnDouble(roomSnapshotYear.getAllDayRoomConsume() + roomSnapshotYear.getNightRoomConsume(), roomSnapshotYear.getSumAllDayRoom() + roomSnapshotYear.getSumNightRoom()));
        jsonArrayNow.add(row);

        row = new JSONObject();
        row.put("2", "平均出租率");
        row.put("4", szMath.formatTwoDecimalReturnDouble((double) (roomSnapshotDay.getSumAllDayRoom() + roomSnapshotDay.getSumNightRoom()), roomSnapshotDay.getSumRealRoom()));
        row.put("7", szMath.formatTwoDecimalReturnDouble((double) (roomSnapshotMonth.getSumAllDayRoom() + roomSnapshotMonth.getSumNightRoom()), roomSnapshotMonth.getSumRealRoom()));
        row.put("10", szMath.formatTwoDecimalReturnDouble((double) (roomSnapshotYear.getSumAllDayRoom() + roomSnapshotYear.getSumNightRoom()), roomSnapshotYear.getSumRealRoom()));
        jsonArrayNow.add(row);

        row = new JSONObject();
        row.put("2", "REVPAR");
        row.put("4", szMath.formatTwoDecimalReturnDouble(roomSnapshotDay.getAllDayRoomConsume() + roomSnapshotDay.getNightRoomConsume(), roomSnapshotDay.getSumRealRoom()));
        row.put("7", szMath.formatTwoDecimalReturnDouble(roomSnapshotMonth.getAllDayRoomConsume() + roomSnapshotMonth.getNightRoomConsume(), roomSnapshotMonth.getSumRealRoom()));
        row.put("10", szMath.formatTwoDecimalReturnDouble(roomSnapshotYear.getAllDayRoomConsume() + roomSnapshotYear.getNightRoomConsume(), roomSnapshotYear.getSumRealRoom()));
        jsonArrayNow.add(row);

        //------------------------------营业点-------------------------------------
        firstRow = true;
        List<ReportStore> reportStoreList = reportStoreService.getList("fieldTemplate", "全店收入表", timeService.dateToStringShort(beginTime1));
        for (ReportStore reportStore : reportStoreList) {
            row = new JSONObject();
            if (firstRow) {
                firstRow = false;
                row.put("1", "营业部门");
            }
            row.put("2", reportStore.getColumn_1());
            row.put("4", szMath.parseBigDecimal(reportStore.getColumn_2()));
            row.put("7", szMath.parseBigDecimal(reportStore.getColumn_3()));
            row.put("10", szMath.parseBigDecimal(reportStore.getColumn_4()));
            jsonArrayNow.add(row);
        }
        //------------------------------付款方式-------------------------------------
        jsonArrayAll.addAll(jsonArrayNow);
        jsonArrayNow = new JSONArray();
        List<Currency> currencyList = currencyService.get();
        row = new JSONObject();
        row.put("1", "付款方式");
        row.put("2", "客账");
        jsonArrayNow.add(row);
        //客账备份起来最后算=rowBack2-rowBack3
        rowBack1 = row;
        for (Currency currency : currencyList) {
            row = new JSONObject();
            row.put("2", currency.getCurrency());
            row.put("4", payPointOfSaleService.getDebtMoneyWithCreate(beginTime1, endTime1,currency.getCurrency(),false));
            row.put("7", payPointOfSaleService.getDebtMoneyWithCreate(beginTime2, endTime2,currency.getCurrency(),false));
            row.put("10", payPointOfSaleService.getDebtMoneyWithCreate(beginTime3, endTime3,currency.getCurrency(),false));
            row.put("5", payPointOfSaleService.getDebtMoneyWithCreate(beginTime1, endTime1,currency.getCurrency(),true));
            row.put("8", payPointOfSaleService.getDebtMoneyWithCreate(beginTime2, endTime2,currency.getCurrency(),true));
            row.put("11", payPointOfSaleService.getDebtMoneyWithCreate(beginTime3, endTime3,currency.getCurrency(),true));
            jsonArrayNow.add(row);
        }
        rowBack3 = calculateSum("小计", jsonArrayNow, jsonArrayAll);
        rowBack1.put("4",rowBack2.getDoubleValue("4")-rowBack3.getDoubleValue("4"));
        rowBack1.put("7",rowBack2.getDoubleValue("7")-rowBack3.getDoubleValue("7"));
        rowBack1.put("10",rowBack2.getDoubleValue("10")-rowBack3.getDoubleValue("10"));

        List<FieldTemplate> fieldTemplateList = new ArrayList<>();
        for (Object o : jsonArrayAll) {
            JSONObject rowItem = (JSONObject) o;
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(rowItem.getString("1"));
            fieldTemplate.setField2(rowItem.getString("2"));
            fieldTemplate.setField3(szMath.ifNotNullGetString(rowItem.getInteger("3")));
            fieldTemplate.setField4(szMath.formatTwoDecimal(rowItem.getDouble("4")));
            fieldTemplate.setField5(szMath.formatTwoDecimal(rowItem.getDouble("5")));
            fieldTemplate.setField6(szMath.ifNotNullGetString(rowItem.getInteger("6")));
            fieldTemplate.setField7(szMath.formatTwoDecimal(rowItem.getDouble("7")));
            fieldTemplate.setField8(szMath.formatTwoDecimal(rowItem.getDouble("8")));
            fieldTemplate.setField9(szMath.ifNotNullGetString(rowItem.getInteger("9")));
            fieldTemplate.setField10(szMath.formatTwoDecimal(rowItem.getDouble("10")));
            fieldTemplate.setField11(szMath.formatTwoDecimal(rowItem.getDouble("11")));
            fieldTemplateList.add(fieldTemplate);
        }
        return reportService.generateReport(fieldTemplateList, new String[]{}, "huayuanDailyParse", "pdf");
    }

    private JSONObject calculateSum(String title, JSONArray jsonArrayNow, JSONArray jsonArrayAll) {
        int totalDayRoom = 0;
        double totalDayConsume = 0.0;
        int totalMonthRoom = 0;
        double totalMonthConsume = 0.0;
        int totalYearRoom = 0;
        double totalYearConsume = 0.0;
        JSONObject row = new JSONObject();
        row.put("2", title);

        for (Object o : jsonArrayNow) {
            JSONObject rowItem = (JSONObject) o;
            totalDayRoom += rowItem.getIntValue("3");
            totalMonthRoom += rowItem.getIntValue("6");
            totalYearRoom += rowItem.getIntValue("9");
            totalDayConsume += rowItem.getDoubleValue("4");
            totalMonthConsume += rowItem.getDoubleValue("7");
            totalYearConsume += rowItem.getDoubleValue("10");
        }
        row.put("3", totalDayRoom);
        row.put("6", totalMonthRoom);
        row.put("9", totalYearRoom);

        row.put("4", totalDayConsume);
        row.put("7", totalMonthConsume);
        row.put("10", totalYearConsume);

        row.put("5", szMath.formatTwoDecimal(row.getDouble("3"), row.getDouble("4")));
        row.put("8", szMath.formatTwoDecimal(row.getDouble("6"), row.getDouble("7")));
        row.put("11", szMath.formatTwoDecimal(row.getDouble("9"), row.getDouble("10")));
        jsonArrayNow.add(row);
        jsonArrayAll.addAll(jsonArrayNow);
        return row;
    }

    private JSONObject addConsume(String title, JSONArray jsonArrayNow, JSONArray jsonArrayAll) {
        double totalDayConsume = 0.0;
        double totalMonthConsume = 0.0;
        double totalYearConsume = 0.0;
        JSONObject row = new JSONObject();
        row.put("2", title);

        for (Object o : jsonArrayNow) {
            JSONObject rowItem = (JSONObject) o;
            totalDayConsume += rowItem.getDoubleValue("4");
            totalMonthConsume += rowItem.getDoubleValue("7");
            totalYearConsume += rowItem.getDoubleValue("10");
        }

        row.put("4", totalDayConsume);
        row.put("7", totalMonthConsume);
        row.put("10", totalYearConsume);

        jsonArrayAll.add(row);
        return row;
    }
}
