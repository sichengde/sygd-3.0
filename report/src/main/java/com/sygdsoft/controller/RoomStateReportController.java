package com.sygdsoft.controller;

import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.RoomStateReportRow;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-01-23.
 * 客房统计表，按照辽阳宾馆老版本改的
 */
@RestController
public class RoomStateReportController {
    @RequestMapping("roomStateReport")
    public List<RoomStateReportRow> roomStateReport(@RequestBody ReportJson reportJson){

    }
}
