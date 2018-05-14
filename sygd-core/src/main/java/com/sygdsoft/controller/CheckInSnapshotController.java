package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckInSnapshot;
import com.sygdsoft.service.CheckInSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckInSnapshotController {
    @Autowired
    CheckInSnapshotService checkInSnapshotService;

    @RequestMapping("checkInSnapshotGet")
    public List<CheckInSnapshot> checkInSnapshotGet(@RequestBody Query query) throws Exception {
        return checkInSnapshotService.get(query);
    }
}
