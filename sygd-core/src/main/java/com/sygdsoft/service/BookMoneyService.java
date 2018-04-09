package com.sygdsoft.service;

import com.sygdsoft.mapper.BookMoneyMapper;
import com.sygdsoft.model.BookMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-08-24.
 */
@Service
@SzMapper(id = "bookMoney")
public class BookMoneyService extends BaseService<BookMoney> {
    @Autowired
    BookMoneyMapper bookMoneyMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;

    /**
     * 计算该时间段内的订金总数
     */
    public Double getTotalBookSubscription(String userId, String currency, Date beginTime, Date endTime) {
            return bookMoneyMapper.getMoney(userId, currency, false,beginTime, endTime);
    }

    /**
     * 计算该时间段内的退订金总数
     */
    public Double getTotalCancelBookSubscription(String userId, String currency, Date beginTime, Date endTime) {
        return bookMoneyMapper.getMoney(userId, currency, true,beginTime, endTime);
    }

    /**
     * 增加一笔订金记录
     */
    public void addSubscription(String bookSerial, Double subscription, String currency) throws Exception {
        BookMoney bookMoney = new BookMoney();
        bookMoney.setBookSerial(bookSerial);
        bookMoney.setDoTime(timeService.getNow());
        bookMoney.setSubscription(subscription);
        bookMoney.setUserId(userService.getCurrentUser());
        bookMoney.setCurrency(currency);
        this.add(bookMoney);
    }

    /**
     * 获取各个币种的押金，需要聚合，通过订单号获取
     */
    public List<BookMoney> getByDeskBookSerial(String bookSerial) {
        return bookMoneyMapper.getByDeskBookSerial(bookSerial);
    }
}
