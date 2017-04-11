package com.sygdsoft;

import com.sygdsoft.conf.dataSource.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author liuzh
 * @since 2015-12-12 18:22
 */
@SpringBootApplication
@EnableScheduling
@Import({DynamicDataSourceRegister.class})
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        if (args.length>0) {
            System.out.println(args[0]);
        }
        try {
            InetAddress theAddress = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(theAddress,8080);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return;
        }
        SpringApplication.run(Application.class, args);
    }

}
