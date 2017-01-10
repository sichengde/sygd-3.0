package com.sygdsoft.controller;

import com.sygdsoft.model.GuestOutBed;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/1/8 0008.
 */
@RestController
public class GuestOutOtherController {
    @RequestMapping(value = "guestOutBed")
    public Integer guestOutBed(@RequestBody GuestOutBed guestOutBed){
        return 0;
    }
}
