package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.GuestSourceMapper;
import com.sygdsoft.model.GuestSource;
import com.sygdsoft.service.GuestSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class GuestSourceController {
    @Autowired
    GuestSourceService guestSourceService;

    @RequestMapping(value = "guestSourceAdd")
    @Transactional
    public void guestSourceAdd(@RequestBody GuestSource guestSource) throws Exception {
        guestSourceService.add(guestSource);
    }

    @RequestMapping(value = "guestSourceUpdate")
    @Transactional
    public void guestSourceAdd(@RequestBody List<GuestSource> guestSourceList) throws Exception {
        if (guestSourceList.size() > 1) {
            if (guestSourceList.get(0).getId().equals(guestSourceList.get(guestSourceList.size() / 2).getId())) {
                guestSourceService.update(guestSourceList.subList(0, guestSourceList.size() / 2));
                return;
            }
        }
        guestSourceService.update(guestSourceList);
    }

    @RequestMapping(value = "guestSourceDelete")
    @Transactional(rollbackFor = Exception.class)
    public void guestSourceDelete(@RequestBody List<GuestSource> guestSourceList) throws Exception {
        guestSourceService.delete(guestSourceList);
    }


    @RequestMapping(value = "guestSourceGet")
    public List<GuestSource> guestSourceGet(@RequestBody Query query) throws Exception {
        return guestSourceService.get(query);
    }
}
