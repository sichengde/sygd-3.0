package com.sygdsoft.controller.room;

import com.sygdsoft.model.room.SourceCategoryParseQuery;
import com.sygdsoft.service.CheckInIntegrationService;
import com.sygdsoft.service.CheckInService;
import com.sygdsoft.service.GuestIntegrationService;
import com.sygdsoft.service.RoomStateReportService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;

/**
 * Created by 舒展 on 2017-06-12.
 */
@RestController
public class SourceCategoryParseController {
    @Autowired
    GuestIntegrationService guestIntegrationService;
    @Autowired
    CheckInIntegrationService checkInIntegrationService;
    @Autowired
    RoomStateReportService roomStateReportService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "sourceCategoryParseReport")
    public List sourceCategoryParseReport(@RequestBody SourceCategoryParseQuery sourceCategoryParseQuery) throws ParseException {
        String mode = sourceCategoryParseQuery.getMode();
        Date beginTime = sourceCategoryParseQuery.getBeginTime();
        Date endTime = sourceCategoryParseQuery.getEndTime();
        List<String> guestSourceList = sourceCategoryParseQuery.getGuestSourceList();
        List<String> roomCategoryList = sourceCategoryParseQuery.getRoomCategoryList();
        List<String> saleCountList = sourceCategoryParseQuery.getSaleCountList();
        List result = new ArrayList();
        switch (mode) {
            case "1":
                for (String category : saleCountList) {
                    switch (category) {
                        case "入住总人数":
                            for (String guestSource : guestSourceList) {
                                Map<String, String> row = new HashMap<>();
                                row.put("guestSource", guestSource);
                                for (String roomCategory : roomCategoryList) {
                                    row.put(roomCategory, szMath.ifNotNullGetString(guestIntegrationService.getSumNum(beginTime, endTime, guestSource, roomCategory)));
                                }
                                result.add(row);
                            }
                            break;
                        case "入住天数":
                            for (String guestSource : guestSourceList) {
                                Map<String, String> row = new HashMap<>();
                                row.put("guestSource", guestSource);
                                for (String roomCategory : roomCategoryList) {
                                    row.put(roomCategory, szMath.ifNotNullGetString(checkInIntegrationService.getTotalLiveDay(beginTime, endTime, guestSource, roomCategory)));
                                }
                                result.add(row);
                            }
                            break;
                        case "日均房价":
                            for (String guestSource : guestSourceList) {
                                Map<String, String> row = new HashMap<>();
                                row.put("guestSource", guestSource);
                                for (String roomCategory : roomCategoryList) {
                                    row.put(roomCategory, szMath.ifNotNullGetString(checkInIntegrationService.getAvaRoomPrice(beginTime, endTime, guestSource, roomCategory)));
                                }
                                result.add(row);
                            }
                            break;
                        case "人均价格":
                            break;
                        case "营业收入":
                            break;
                        case "杂费":
                            break;
                    }
                }
                break;
            case "2":
                for (String roomCategory : roomCategoryList) {
                    Map<String, String> row = new HashMap<>();
                    row.put("roomCategory", roomCategory);
                    for (String saleCount : saleCountList) {
                        Integer totalNum;
                        Double totalDouble;
                        switch (saleCount) {
                            case "入住总人数":
                                totalNum=0;
                                for (String guestSource : guestSourceList) {
                                    totalNum+=szMath.nullToZero(guestIntegrationService.getSumNum(beginTime, endTime, guestSource, roomCategory));
                                }
                                row.put("入住总人数", szMath.ifNotNullGetString(totalNum));
                                break;
                            case "入住天数":
                                totalNum=0;
                                for (String guestSource : guestSourceList) {
                                    totalNum+=szMath.nullToZero(checkInIntegrationService.getTotalLiveDay(beginTime, endTime, guestSource, roomCategory));
                                }
                                row.put("入住天数", szMath.ifNotNullGetString(totalNum));
                                break;
                            case "日均房价":
                                totalDouble=0.0;
                                totalNum=0;
                                for (String guestSource : guestSourceList) {
                                    Double aDouble=szMath.nullToZero(checkInIntegrationService.getAvaRoomPrice(beginTime, endTime, guestSource, roomCategory));
                                    if(aDouble>0) {
                                        totalDouble +=aDouble;
                                        totalNum++;
                                    }
                                }
                                row.put("日均房价", szMath.formatTwoDecimal(totalDouble,totalNum));
                                break;
                            case "人均价格":
                                break;
                            case "入住率":
                                row.put("入住率", szMath.formatTwoDecimal(roomStateReportService.getRentRateOnly(beginTime, endTime, roomCategory)));
                                break;
                            case "营业收入":
                                break;
                            case "杂费":
                                break;
                        }
                    }
                    result.add(row);
                }
                break;
            case "3":
                for (String guestSource : guestSourceList) {
                    Map<String, String> row = new HashMap<>();
                    row.put("guestSource", guestSource);
                    for (String saleCount : saleCountList) {
                        Integer totalNum;
                        Double totalDouble;
                        switch (saleCount) {
                            case "入住总人数":
                                totalNum=0;
                                for (String roomCategory : roomCategoryList) {
                                    totalNum+=szMath.nullToZero(guestIntegrationService.getSumNum(beginTime, endTime, guestSource, roomCategory));
                                }
                                row.put("入住总人数", szMath.ifNotNullGetString(totalNum));
                                break;
                            case "入住天数":
                                totalNum=0;
                                for (String roomCategory : roomCategoryList) {
                                    totalNum+=szMath.nullToZero(checkInIntegrationService.getTotalLiveDay(beginTime, endTime, guestSource, roomCategory));
                                }
                                row.put("入住天数", szMath.ifNotNullGetString(totalNum));
                                break;
                            case "日均房价":
                                totalDouble=0.0;
                                totalNum=0;
                                for (String roomCategory : roomCategoryList) {
                                    Double aDouble=szMath.nullToZero(checkInIntegrationService.getAvaRoomPrice(beginTime, endTime, guestSource, roomCategory));
                                    if(aDouble>0) {
                                        totalDouble +=aDouble;
                                        totalNum++;
                                    }
                                }
                                row.put("日均房价", szMath.formatTwoDecimal(totalDouble,totalNum));
                                break;
                            case "人均价格":
                                break;
                            case "营业收入":
                                break;
                            case "杂费":
                                break;
                        }
                    }
                    result.add(row);
                }
                break;
        }
        return result;
    }
}
