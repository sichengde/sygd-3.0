package com.sygdsoft.conf;

import com.sygdsoft.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016-05-23.
 */
public class RegisterConfig {
    @Autowired
    RegisterService registerService;

    /**
     * 初始化注册码，没有用了
     */
    @PostConstruct
    public void init() throws IOException {
        registerService.check();
        registerService.checkCK();
        registerService.checkSN();
    }
}
