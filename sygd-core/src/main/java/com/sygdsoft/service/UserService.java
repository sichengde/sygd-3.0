package com.sygdsoft.service;

import com.sygdsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 舒展 on 2016-05-10.2
 */
@Service
@SzMapper(id = "user")
public class UserService extends BaseService<User>{
    @Autowired
    HttpServletRequest request;
    public String getCurrentUser() throws Exception {
        String userId= (String) request.getSession().getAttribute("userId");
        if(userId==null){
            throw new Exception("登录超时，需要重新登录");
        }else {
            return userId;
        }
        //return userId;
    }
    public String getCurrentIpAddr(){
        return request.getRemoteAddr();
    }
}