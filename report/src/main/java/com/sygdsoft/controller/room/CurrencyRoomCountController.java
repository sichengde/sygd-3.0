package com.sygdsoft.controller.room;

import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.RoomSnapshot;
import com.sygdsoft.service.RoomSnapshotService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class CurrencyRoomCountController {
    @Autowired
    TimeService timeService;
    @Autowired
    RoomSnapshotService roomSnapshotService;
    /**
     * 结束日期时间要设置为24:00:00
     */
    @RequestMapping(value = "currencyRoomCountReport")
    public List<RoomSnapshot> roomCategorySaleReport(@RequestBody ReportJson reportJson) throws Exception {
        Date beginTime = timeService.getMinTime(reportJson.getBeginTime());
        Date endTime = timeService.getMinTime(reportJson.getEndTime());
        /*效验roomStateReport表中有没有数据*/
        List<RoomSnapshot> roomCategoryRowList=roomSnapshotService.getPaidRoom(beginTime, endTime);
        RoomSnapshot roomStateReport=roomSnapshotService.getSumByDate(beginTime, endTime);
        roomCategoryRowList.add(roomStateReport);
        return roomCategoryRowList;
    }
}
