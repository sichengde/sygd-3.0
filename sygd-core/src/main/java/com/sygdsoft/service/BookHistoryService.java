package com.sygdsoft.service;

import com.sygdsoft.mapper.BookHistoryMapper;
import com.sygdsoft.model.BookHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-07-29.
 */
@Service
@SzMapper(id = "bookHistory")
public class BookHistoryService extends BaseService<BookHistory> {
    @Autowired
    BookHistoryMapper bookHistoryMapper;

    /**
     * 获得单位聚合后的订单到达率
     */
    public List<BookHistory> getSumReachByCompany(Date beginTime, Date endTime) {
        if (beginTime == null && endTime == null) {
            return bookHistoryMapper.getSumReachByCompany();
        } else {
            return bookHistoryMapper.getSumReachByCompanyDate(beginTime, endTime);
        }
    }
}
