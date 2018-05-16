package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @RequestMapping("futureRoomStateCategory")
    public List<JSONObject> futureRoomStateCategory(@RequestBody ReportJson reportJson)throws Exception{
        Date beginTime = timeService.getMinTime(reportJson.getBeginTime());
        Date endTime = timeService.getMinTime(reportJson.getEndTime());
        List<JSONObject> jsonObjectList=new ArrayList<>();
        while (beginTime.getTime()<=endTime.getTime()){
            List<RoomCategory> roomCategoryList=getRoomCategoryRemain(beginTime,timeService.addDay(beginTime,1));
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("date",timeService.dateToStringShort(beginTime));
            for (RoomCategory roomCategory : roomCategoryList) {
                jsonObject.put(roomCategory.getCategory(),roomCategory.getNotNullRemain()+"("+roomCategory.getNotNullTodayLeave()+")");
            }
            jsonObjectList.add(jsonObject);
            beginTime=timeService.addDay(beginTime,1);
        }
        return jsonObjectList;
    }

    private List<RoomCategory> getRoomCategoryRemain(Date beginTime,Date endTime) throws Exception {
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
            roomCategory.setTodayLeave(0);
        }
        /*先分析有没有这个时间段在店的*/
        List<CheckIn> checkInList = checkInService.get(null);
        for (CheckIn checkIn : checkInList) {
            if (checkIn.getLeaveTime().compareTo(beginTime) >= 0) {
                String category = checkIn.getRoomCategory();
                for (RoomCategory roomCategory : roomCategoryList) {
                    if (roomCategory.getCategory().equals(category)) {
                        if(checkIn.getLeaveTime().getTime()<endTime.getTime()){//只是预离
                            roomCategory.setTodayLeave(roomCategory.getNotNullTodayLeave()+1);
                        }else {//确实冲突
                            roomCategory.setRemain(roomCategory.getRemain() - 1);
                        }
                    }
                }
            }
        }
        /*再分析其他订单*/
        List<BookRoomCategory> bookRoomCategoryList = bookRoomCategoryService.getViolenceRoomCategory(beginTime, endTime);
        for (BookRoomCategory bookRoomCategory : bookRoomCategoryList) {
            for (RoomCategory roomCategory : roomCategoryList) {
                if (roomCategory.getCategory().equals(bookRoomCategory.getRoomCategory())) {
                    roomCategory.setRemain(roomCategory.getRemain() - bookRoomCategory.getNum());
                }
            }
        }
        return roomCategoryList;
    }

    @RequestMapping(value = "futureRoomState")
    public List<JSONObject> futureRoomState(@RequestBody Query query) throws Exception {
        List<Room> roomList = roomService.get(query);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (Room room : roomList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roomId", room.getRoomId());
            jsonObject.put("roomCategory", room.getCategory());
            List<Book> bookList = bookService.getBookByRoomId(room.getRoomId());
            for (Book book : bookList) {
                Date beginTime = book.getReachTime();
                jsonObject.put(timeService.getReportFormat(beginTime), true);
                String endTime = timeService.dateToStringShort(book.getLeaveTime());
                while (true) {
                    beginTime = timeService.addDay(beginTime, 1);
                    if (endTime.equals(timeService.dateToStringShort(beginTime))) {
                        break;
                    }
                    jsonObject.put(timeService.getReportFormat(beginTime), true);
                }
            }
            jsonObjectList.add(jsonObject);
        }
        return jsonObjectList;
    }
}
