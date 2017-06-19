package com.sygdsoft.controller.room;

import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.RoomStateReport;
import com.sygdsoft.service.RoomStateReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-06-19.
 */
@RestController
public class RoomStateReportController {
    @Autowired
    RoomStateReportService roomStateReportService;
    @RequestMapping("RoomStateReportGetChart")
    public List<RoomStateReport> RoomStateReportGetChart(@RequestBody ReportJson reportJson){
        return roomStateReportService.RoomStateReportGetChart(reportJson.getBeginTime(),reportJson.getEndTime());
    }
}
