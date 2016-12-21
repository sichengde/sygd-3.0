package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.BookMoney;
import com.sygdsoft.model.DeskBook;
import com.sygdsoft.service.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-09-18.
 */
@RestController
public class DeskBookController {
    @Autowired
    DeskBookService deskBookService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    ReportService reportService;
    @Autowired
    SerialService serialService;
    @Autowired
    UserService userService;
    @Autowired
    BookMoneyService bookMoneyService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    Util util;

    @RequestMapping(value = "deskBookGet")
    public List<DeskBook> deskBookGet(@RequestBody Query query) throws Exception {
        List<DeskBook> deskBookList=deskBookService.get(query);
        for (DeskBook deskBook : deskBookList) {
            deskBook.setBookMoneyList(bookMoneyService.getByDeskBookSerial(deskBook.getDeskBookSerial()));
        }
        return deskBookList;
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "deskBookDelete")
    @Transactional(rollbackFor = Exception.class)
    public void deskBookDelete(@RequestBody List<DeskBook> deskBookList) throws Exception {
        deskBookService.delete(deskBookList);
    }

    /**
     * 修改订单
     */
    @RequestMapping(value = "deskBookUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void deskBookUpdate(@RequestBody List<DeskBook> deskBookList) throws Exception {
        if (deskBookList.size() > 1) {
            if (deskBookList.get(0).getId().equals(deskBookList.get(deskBookList.size() / 2).getId())) {
                timeService.setNow();
                String s = userLogService.parseListDeference(deskBookList);
                userLogService.addUserLog(s, userLogService.desk, userLogService.deskBookUpdate,null);
                deskBookService.update(deskBookList.subList(0, deskBookList.size() / 2));
                return;
            }
        }
        deskBookService.update(deskBookList);
    }

    /**
     * 录入订单
     */
    @RequestMapping(value = "deskBookIn")
    @Transactional(rollbackFor = Exception.class)
    public Integer deskBookIn(@RequestBody DeskBook deskBook) throws Exception {
        serialService.setDeskBookSerial();
        deskBook.setDeskBookSerial(serialService.setDeskBookSerial());
        deskBookService.add(deskBook);
        userLogService.addUserLog("餐饮订单录入" + deskBook.getDesk(), userLogService.desk, userLogService.newBook,serialService.getDeskBookSerial());
        /*如果有订金的话，生成预订订金信息*/
        if (deskBook.getSubscription() != null) {
            BookMoney bookMoney = new BookMoney();
            bookMoney.setBookSerial(deskBook.getDeskBookSerial());
            bookMoney.setDoTime(timeService.getNow());
            bookMoney.setSubscription(deskBook.getSubscription());
            bookMoney.setUserId(userService.getCurrentUser());
            bookMoney.setCurrency(deskBook.getCurrency());
            bookMoneyService.add(bookMoney);
        }
        /*报表参数
        * 1.酒店名称
        * 2.流水号
        * 3.来店时间
        * 4.保留时间
        * 5.宾客姓名
        * 6.联系电话
        * 7.订金
        * 8.付款方式
        * 9.订金大写
        * 10.备注
        * */
        return reportService.generateReport(null, new String[]{otherParamService.getValueByName("酒店名称"),serialService.getDeskBookSerial(),timeService.dateToStringLong(deskBook.getReachTime()),timeService.dateToStringLong(deskBook.getRemainTime()),deskBook.getGuestName(),deskBook.getPhone(),ifNotNullGetString(deskBook.getSubscription()),deskBook.getCurrency(),util.number2CNMontrayUnit(BigDecimal.valueOf(deskBook.getNotNullSubscription())),deskBook.getRemark()}, "deskBook", "pdf");
    }

    /**
     * 补打预订单
     */
    @RequestMapping(value = "printDeskBookAgain")
    public Integer printDeskBookAgain(@RequestBody DeskBook deskBook){
        return reportService.generateReport(null, new String[]{otherParamService.getValueByName("酒店名称"),serialService.getDeskBookSerial(),timeService.dateToStringLong(deskBook.getReachTime()),timeService.dateToStringLong(deskBook.getRemainTime()),deskBook.getGuestName(),deskBook.getPhone(),ifNotNullGetString(deskBook.getSubscription()),deskBook.getCurrency(),util.number2CNMontrayUnit(BigDecimal.valueOf(deskBook.getNotNullSubscription())),deskBook.getRemark()}, "deskBook", "pdf");
    }

    /**
     * 增加/退订金
     */
    @RequestMapping(value = "addSubscriptionDesk")
    @Transactional(rollbackFor = Exception.class)
    public void addSubscriptionDesk(@RequestBody DeskBook deskBook) throws Exception {
        timeService.setNow();
        deskBookService.addSubscription(deskBook.getDeskBookSerial(), deskBook.getSubscription());
        BookMoney bookMoney = new BookMoney();
        bookMoney.setBookSerial(deskBook.getDeskBookSerial());
        bookMoney.setDoTime(timeService.getNow());
        bookMoney.setSubscription(deskBook.getSubscription());
        bookMoney.setUserId(userService.getCurrentUser());
        bookMoney.setCurrency(deskBook.getCurrency());
        bookMoneyService.add(bookMoney);
        String action;
        if (deskBook.getSubscription() > 0) {
            action = "补交";
        } else {
            action = "退";
        }
        userLogService.addUserLog("预订单" + deskBook.getDeskBookSerial() + action + "订金" + deskBook.getSubscription(), userLogService.desk, userLogService.addSubscription,null);
    }
}
