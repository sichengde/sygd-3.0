package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24.
 */
@RestController
public class BookParseController {
    @Autowired
    RoomService roomService;
    @Autowired
    RoomCategoryService roomCategoryService;
    @Autowired
    BookService bookService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    TimeService timeService;
    @Autowired
    BookRoomCategoryService bookRoomCategoryService;

    @RequestMapping(value = "getRoomCategoryRemain")
    public List<RoomCategory> getRoomCategoryRemain(@RequestBody ReportJson reportJson) throws Exception {
        /*获取时间范围*/
        Date beginTime = timeService.getMinTime(reportJson.getBeginTime());
        Date endTime = timeService.getMinTime(reportJson.getEndTime());
        /*先获取所有房间种类*/
        List<RoomCategory> roomCategoryList = roomCategoryService.get(null);
        /*每个房间种类的可用房*/
        /*分析每个类型房间在该时间段内的可用房数量*/
        for (RoomCategory roomCategory : roomCategoryList) {
            String category = roomCategory.getCategory();
            /*先获取这个房类总数*/
            Integer total = roomService.getTotalCategoryNum(category);
            /*初始化每个房间种类的可用房*/
            roomCategory.setRemain(total);
        }
        /*先分析有没有这个时间段在店的*/
        List<CheckIn> checkInList = checkInService.get(null);
        for (CheckIn checkIn : checkInList) {
            if(checkIn.getLeaveTime().compareTo(beginTime)==1){
                String category=checkIn.getRoomCategory();
                for (RoomCategory roomCategory : roomCategoryList) {
                    if(roomCategory.getCategory().equals(category)){
                        roomCategory.setRemain(roomCategory.getRemain()-1);
                    }
                }
            }
        }
        /*再分析其他订单*/
        List<BookRoomCategory> bookRoomCategoryList=bookRoomCategoryService.getViolenceRoomCategory(beginTime, endTime);
        for (BookRoomCategory bookRoomCategory : bookRoomCategoryList) {
            for (RoomCategory roomCategory : roomCategoryList) {
                if(roomCategory.getCategory().equals(bookRoomCategory.getRoomCategory())){
                    roomCategory.setRemain(roomCategory.getRemain()-bookRoomCategory.getNum());
                }
            }
        }
        return roomCategoryList;
    }

    @RequestMapping(value = "futureRoomState")
    public List<JSONObject> futureRoomState(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        List<Room> roomList=roomService.get(null);
        return null;
    }
}
