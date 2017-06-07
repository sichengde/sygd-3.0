package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by 舒展 on 2016-10-12.
 */
@RestController
public class HotelController {
    @Autowired
    HotelService hotelService;

    /**
     * 获取本地在云端的数据库名称，并且发送给云端(本地)
     */
    @RequestMapping(value = "getDataBaseName")
    public OnlyString getDataBaseName() throws Exception {
        return new OnlyString(hotelService.getHotelId());
    }

    /**
     * 接收数据库名称（云端）
     */
    @RequestMapping(value = "setDataBaseName")
    public void setDataBaseName(HttpSession httpSession,@RequestBody String dataBaseName){
        httpSession.setAttribute("hotelId", dataBaseName);
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "contactUs")
    public OnlyString  contactUs(){
        return new OnlyString("服务热线:18624410635,13804046634");
    }
}
