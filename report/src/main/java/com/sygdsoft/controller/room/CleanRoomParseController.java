package com.sygdsoft.controller.room;

import com.sygdsoft.model.CleanRoom;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.CleanRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
@RestController
public class CleanRoomParseController {
    @Autowired
    CleanRoomService cleanRoomService;

    @RequestMapping(value = "cleanRoomParse")
    public List<CleanRoom> cleanRoomParse(@RequestBody ReportJson reportJson){
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        return cleanRoomService.getSumNumByDate(beginTime, endTime);
    }
}
