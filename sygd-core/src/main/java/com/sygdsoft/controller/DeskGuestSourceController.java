package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.DeskGuestSource;
import com.sygdsoft.service.DeskGuestSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class DeskGuestSourceController {
    @Autowired
    DeskGuestSourceService deskGuestSourceService;

    @RequestMapping(value = "deskGuestSourceAdd")
    public void deskGuestSourceAdd(@RequestBody DeskGuestSource deskGuestSource) throws Exception {
        deskGuestSourceService.add(deskGuestSource);
    }

    @RequestMapping(value = "deskGuestSourceDelete")
    @Transactional(rollbackFor = Exception.class)
    public void deskGuestSourceDelete(@RequestBody List<DeskGuestSource> deskGuestSourceList) throws Exception {
        deskGuestSourceService.delete(deskGuestSourceList);
    }

    @RequestMapping(value = "deskGuestSourceUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void deskGuestSourceUpdate(@RequestBody List<DeskGuestSource> deskGuestSourceList) throws Exception {
        if (deskGuestSourceList.size() > 1) {
            if (deskGuestSourceList.get(0).getId().equals(deskGuestSourceList.get(deskGuestSourceList.size() / 2).getId())) {
                deskGuestSourceService.update(deskGuestSourceList.subList(0, deskGuestSourceList.size() / 2));
                return;
            }
        }
        deskGuestSourceService.update(deskGuestSourceList);
    }

    @RequestMapping(value = "deskGuestSourceGet")
    public List<DeskGuestSource> deskGuestSourceGet(@RequestBody Query query) throws Exception {
        return deskGuestSourceService.get(query);
    }
}
