package com.sygdsoft.service;

import com.sygdsoft.mapper.UserMapper;
import com.sygdsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.2
 */
@Service
@SzMapper(id = "user")
public class UserService extends BaseService<User> {
    @Autowired
    UserMapper userMapper;
    @Autowired
    HttpServletRequest request;

    public String getCurrentUser() throws Exception {
        String userId= (String) request.getSession().getAttribute("userId");
        if(userId==null){
            userId=URLDecoder.decode(request.getHeader("userid"),"UTF-8");
        }
        if(userId==null){
            throw new Exception("登录超时，需要重新登录");
        }else {
            return userId;
        }
        //return userId;
    }

    public String getCurrentIpAddr() {
        return request.getRemoteAddr();
    }

    public User getByName(String name) {
        User userQuery = new User();
        userQuery.setUserId(name);
        return userMapper.selectOne(userQuery);
    }

    public String getNameStringByGroup(String groupName) {
        User user = new User();
        user.setGroupBy(groupName);
        List<User> userList = userMapper.select(user);
        StringBuilder out = new StringBuilder("");
        int length = userList.size();
        for (int i = 0; i < length; i++) {
            out.append(userList.get(i).getUserId());
            if (i + 1 < length) {
                out.append(",");
            }
        }
        return out.toString();
    }
}