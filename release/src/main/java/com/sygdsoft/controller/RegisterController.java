package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by 舒展 on 2016-09-01.
 * 注册，校验
 */
@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @RequestMapping(value = "getRegisterNumber")
    public OnlyString getRegisterNumber()throws Exception{
        String s=registerService.getLocalSerialShow();
        return new OnlyString(s);
    }

    @RequestMapping(value = "getRegisterDate")
    public Date registerDateCheck(){
        return registerService.getLimitTime();
    }

    @RequestMapping(value = "getAlertType")
    public Integer getAlertType(){
        return registerService.getAlertType();
    }

    /**
     * 获取时间
     * @return
     */
    @RequestMapping(value = "time")
    public Date getNowTime() throws Exception {
        return new Date();
    }

    /**
     * 设置秘钥
     */
    @RequestMapping(value = "setSecurityStr")
    public Integer setSecurityStr() throws Exception {
        Date now=new Date();
        String s=String.valueOf(now.getTime()).substring(7,13);
        registerService.securityStr.add(String.valueOf(Integer.valueOf(s)*277^277));
        return Integer.valueOf(s);
    }
}
