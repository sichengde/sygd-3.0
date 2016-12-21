package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.BookHistory;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.service.BookHistoryService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
@RestController
public class BookHistoryController {
    @Autowired
    BookHistoryService bookHistoryService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "bookHistoryGet")
    public List<BookHistory> bookHistoryGet(@RequestBody Query query) throws Exception {
        return bookHistoryService.get(query);
    }
    @RequestMapping("companyBookParse")
    public List<BookHistory> companyBookParse(@RequestBody ReportJson reportJson){
        Date beginTime = reportJson.getBeginTime();
        Date endTime = reportJson.getEndTime();
        List<BookHistory> bookHistoryList=bookHistoryService.getSumReachByCompany(beginTime, endTime);
        for (BookHistory bookHistory : bookHistoryList) {
            bookHistory.setMark(szMath.formatPercent(bookHistory.getBookedRoom(),bookHistory.getTotalRoom()));
        }
        return  bookHistoryList;
    }
}
