package com.sygdsoft.controller.room;

import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.RoomSnapshot;
import com.sygdsoft.service.DebtIntegrationService;
import com.sygdsoft.service.RoomSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentParseDailyReportController {
    @Autowired
    RoomSnapshotService roomSnapshotService;

    @RequestMapping(value = "rentParseDailyReport")
    public List<RoomSnapshot> rentParseDailyReport(@RequestBody ReportJson reportJson) throws Exception {
        return roomSnapshotService.getListGroupByCategory(reportJson.getBeginTime(),reportJson.getEndTime());
    }
}
