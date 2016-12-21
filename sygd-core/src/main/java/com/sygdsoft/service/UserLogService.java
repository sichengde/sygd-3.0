package com.sygdsoft.service;

import com.sygdsoft.mapper.UserLogMapper;
import com.sygdsoft.model.UserLog;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-19.
 * 操作员日志类型，增加操作员日志
 */
@Service
@SzMapper(id = "userLog")
public class UserLogService extends BaseService<UserLog> {
    /*-----------------------------接待-----------------------------*/
    public String guestIn = "散客开房";
    public String guestInGroup = "团队开房";
    public String guestOut = "散客结账";
    public String guestOutGroup = "团队结账";
    public String ZC = "杂单冲账";
    public String joinRoom = "联房";
    public String changeRoom = "宾客换房";
    public String lord = "单位签单人";
    public String freeman = "宴请签单人";
    public String night = "夜审";
    public String guestOutReverse = "结账叫回";
    public String lostRoomCheckOut = "哑房结算";
    public String cleanRoom = "客房清扫";
    /*-----------------------------预定-----------------------------*/
    public String newBook = "新增订单";
    public String addSubscription = "订金修改";
    public String deleteBook = "删除订单";
    public String updateBook = "更新订单";
    /*-----------------------------餐饮-----------------------------*/
    public String deskDetailIn = "点菜";
    public String deskOut = "结算";
    public String deskOutReverse = "结算叫回";
    public String deskBookUpdate = "订单修改";
    /*-----------------------------桑拿-----------------------------*/
    public String saunaIn = "开牌";
    public String saunaDetail = "录消费";
    public String saunaPay = "结算";
    /*-----------------------------公共-----------------------------*/
    public String login = "登录";
    public String logOut = "退出";
    public String delete = "删除";
    public String add = "增加";
    public String update = "修改";
    public String book = "预定";
    /*-----------------------------会员-----------------------------*/
    public String recharge = "充值";
    public String refund = "退款";
    public String cancel = "注销";
    public String vipCreate = "开卡";
    /*-----------------------------单位-----------------------------*/
    public String companyCreate = "单位创建";
    public String companyPay = "结算";
    public String companyDeposit = "预付";
    /*-----------------------------所有的module-----------------------------*/
    public String reception = "接待";
    public String desk = "餐饮";
    public String sauna = "桑拿";
    public String storage = "库存";
    public String parameter = "系统维护";
    public String vip = "会员";
    public String company = "单位";

    //public List<String> logUser = new ArrayList<>();//已经登录的用户，禁止重复登录，已移除


    @Autowired
    UserLogMapper userLogMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    Util util;


    /**
     * 增加一条操作员记录
     */
    public int addUserLog(String action, String module, String category,String keyWord) throws Exception {
        UserLog userLog = new UserLog();
        userLog.setUserId(userService.getCurrentUser());
        userLog.setAction(action);
        userLog.setModule(module);
        userLog.setDoTime(timeService.getNow());
        userLog.setCategory(category);
        userLog.setIpAddress(userService.getCurrentIpAddr());
        userLog.setKeyWord(keyWord);
        userLogMapper.insert(userLog);
        return userLog.getId();
    }

    /**
     * 增加一条操作员记录
     */
    public int addUserLogWithoutUserIp(String action, String module, String category) throws Exception {
        UserLog userLog = new UserLog();
        userLog.setAction(action);
        userLog.setModule(module);
        userLog.setDoTime(timeService.getNow());
        userLog.setCategory(category);
        userLogMapper.insert(userLog);
        return userLog.getId();
    }

    /**
     * 增加一条操作员记录（指定操作员）
     */
    public int addUserLog(String action, String module, String category,String user,String keyWord){
        UserLog userLog = new UserLog();
        userLog.setUserId(user);
        userLog.setAction(action);
        userLog.setModule(module);
        userLog.setDoTime(timeService.getNow());
        userLog.setCategory(category);
        userLog.setIpAddress(userService.getCurrentIpAddr());
        userLog.setKeyWord(keyWord);
        userLogMapper.insert(userLog);
        return userLog.getId();
    }


    /**
     * 分析更新数组的不同
     */
    public String parseListDeference(List<?> objectList) {
        Integer step = objectList.size() / 2;
        String message = "";
        for (int i = 0; i < step; i++) {
            String s = util.classOfSrc(objectList.get(i + step), objectList.get(i));
            message = message + s;
        }
        return message;
    }
/*啊啊啊*/

    /**
     * 根据主键删除
     */
    public void deleteByPrimaryKey(Integer index) {
        userLogMapper.deleteByPrimaryKey(index);
    }
}
