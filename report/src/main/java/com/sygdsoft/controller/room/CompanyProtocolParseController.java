package com.sygdsoft.controller.room;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.model.CompanyProtocolParseQuery;
import com.sygdsoft.service.CheckInHistoryLogService;
import com.sygdsoft.service.CompanyService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CompanyProtocolParseController {
    @Autowired
    CompanyService companyService;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "companyProtocolParseReport")
    public List<JSONObject> companyProtocolParseReport(@RequestBody CompanyProtocolParseQuery companyProtocolParseQuery) {
        List<JSONObject> resultList = new ArrayList<>();
        Date beginTime = companyProtocolParseQuery.getBeginTime();
        Date endTime = companyProtocolParseQuery.getEndTime();
        String type = companyProtocolParseQuery.getType();
        List<String> companyList = companyProtocolParseQuery.getChooseCompanies();
        JSONObject lastRow = new JSONObject();
        lastRow.put("company", "总计");
        for (String companyName : companyList) {
            JSONObject resultRow = new JSONObject();
            resultRow.put("company", companyName);
            List<CheckInHistoryLog> checkInHistoryLogList;
            if ("全部".equals(type)) {
                checkInHistoryLogList = checkInHistoryLogService.getConsumeGroupByProtocol(beginTime, endTime, companyName);
            }else {
                checkInHistoryLogList = checkInHistoryLogService.getConsumeGroupByProtocol(beginTime, endTime, companyName);
            }
            for (CheckInHistoryLog checkInHistoryLog : checkInHistoryLogList) {
                resultRow.put(checkInHistoryLog.getRealProtocol(),szMath.formatTwoDecimal(checkInHistoryLog.getConsume()));
                lastRow.put(checkInHistoryLog.getRealProtocol(),szMath.formatTwoDecimal(szMath.nullToZero(lastRow.getDouble(checkInHistoryLog.getRealProtocol()))+szMath.nullToZero(checkInHistoryLog.getConsume())));
            }
            resultList.add(resultRow);
        }
        resultList.add(lastRow);
        return resultList;
    }
}
