package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.User;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-16.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    TimeService timeService;
    @Autowired
    HotelService hotelService;
    @Autowired
    RegisterService registerService;

    /*增*/
    @RequestMapping(value = "userAdd")
    public void userAdd(@RequestBody User user) throws Exception {
        userService.add(user);
    }

    /*删*/
    @RequestMapping(value = "userDelete")
    @Transactional(rollbackFor = Exception.class)
    public void userDeleteList(@RequestBody List<User> userList) throws Exception {
        userService.delete(userList);
    }

    /*改*/
    @RequestMapping(value = "userUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void userUpdateList(@RequestBody List<User> userList) throws Exception {
        if (userList.size() > 1) {
            if (userList.get(0).getId().equals(userList.get(userList.size() / 2).getId())) {
                userService.update(userList.subList(0, userList.size() / 2));
                return;
            }
        }
        userService.update(userList);
    }

    /*查*/
    @RequestMapping(value = "userGet")
    public List<User> userGet(HttpServletRequest request, Device device, @RequestBody Query query) throws Exception {
        if(this.registerService.getPass()==0) {
            return userService.get(query);
        }else {
            return null;
        }
    }

    /*手机用户登录，需要设置数据源为base_hotel*/
    /*@RequestMapping(value = "userLoginPhone")
    public boolean userLoginPhone(HttpSession httpSession,@RequestBody Query query) throws Exception {
        if (DynamicDataSourceContextHolder.containsDataSource("base_hotel")) {
            DynamicDataSourceContextHolder.setDataSourceType("base_hotel");
        }
            List<User> userList = userService.get(query);
            if (userList.size() > 0) {//云端数据库permission_array就是酒店的唯一标识(为了一致性，要不然还得单独创建)
                httpSession.setAttribute("hotelId", userList.get(0).getPermissionArray());
                httpSession.setAttribute("userId", userList.get(0).getUserId());
                return true;
            } else {
                return false;
            }
    }*/

    /**
     * 登陆成功回记session
     */
    @RequestMapping(value = "userSet")
    public OnlyString userSet(HttpSession httpSession, @RequestBody List<String> paramList) throws Exception {
        timeService.setNow();
        String user = paramList.get(0);
        /*if(userLogService.logUser.indexOf(user)>-1){//取消单一站点登录限制
            return false;
        }else {*/
        httpSession.setAttribute("userId", user);
        userLogService.addUserLog("登录:" + user, paramList.get(1), userLogService.login,null);
        //userLogService.logUser.add(user);
        return new OnlyString(userService.getCurrentIpAddr());
        //}
    }

    @RequestMapping(value = "ipGet")
    public String ipGet(){
        return userService.getCurrentIpAddr();
    }

    /**
     * 操作员退出
     */
    @RequestMapping(value = "userOut")
    public Integer userOut(HttpSession httpSession, String user, String module) throws Exception {
        timeService.setNow();
        if (user != null) {
            //userLogService.logUser.remove(user);
            return userLogService.addUserLog("退出:" + user, module, userLogService.logOut,null);
        }
        return 0;
    }

    /**
     * 刷新判断，如果是刷新，则删除之前的操作员退出日志
     */
    @RequestMapping(value = "refreshCheck")
    public void refreshCheck(@RequestBody Integer index) {
        userLogService.deleteByPrimaryKey(index);
    }
}

