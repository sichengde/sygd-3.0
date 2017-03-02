package com.sygdsoft.service;

import com.sygdsoft.mapper.DeskBookMapper;
import com.sygdsoft.model.DeskBook;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
@Service
@SzMapper(id = "deskBook")
public class DeskBookService extends BaseService<DeskBook> {
    @Autowired
    DeskBookMapper deskBookMapper;
    @Autowired
    Util util;
    @Autowired
    TimeService timeService;


    /**
     * 查询房号该天是否有预定
     */
    List<DeskBook> getByDate(String desk, Date date) {
        return deskBookMapper.getByDate(desk, timeService.dateToStringShort(date)+"%");
    }

    /**
     * 补交/退订金
     */
    public void addSubscription(String deskBookSerial, Double subscription) {
        deskBookMapper.addSubscription(deskBookSerial, subscription);
    }

}
