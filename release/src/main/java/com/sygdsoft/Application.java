package com.sygdsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author liuzh
 * @since 2015-12-12 18:22
 */
@SpringBootApplication
@EnableScheduling
//@Import({DynamicDataSourceRegister.class})
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        if (args.length>0) {
            System.out.println(args[0]);
        }
        try {
            new ServerSocket(8080);
        } catch (IOException e) {
            return;
        }
        SpringApplication.run(Application.class, args);
    }
}
