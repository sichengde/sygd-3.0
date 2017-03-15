package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.service.RegisterService;
import org.apache.tomcat.jni.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by 舒展 on 2016-09-01.
 */
@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @RequestMapping(value = "getNumber")
    public OnlyString getNumber() throws Exception {
        return new OnlyString(registerService.sendMessage());
    }

    @RequestMapping(value = "getRegisterNumber")
    public OnlyString getRegisterNumber()throws Exception{
        String s=registerService.getLocalSerial();
        return new OnlyString(s);
    }
}
