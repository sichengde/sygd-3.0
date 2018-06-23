package com.sygdsoft.controller;

import com.sygdsoft.conf.dataSource.DynamicDataSourceContextHolder;
import com.sygdsoft.model.User;
import com.sygdsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "testDataSource/{ds}")
    public String testDataSource(@PathVariable("ds") String ds) throws Exception {
        DynamicDataSourceContextHolder.setDataSourceType(ds);
        List<User> userList = userService.get(null);
        StringBuilder user = new StringBuilder();
        for (User user1 : userList) {
            user.append(user1.getUserId());
        }
        return user.toString();
    }

    @RequestMapping(value = "hotelSet")
    public void userSet(HttpSession httpSession, @RequestBody String domain) throws Exception {
        System.out.println(domain);
        httpSession.setAttribute("domain",domain);
    }
}
