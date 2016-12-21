package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CompanyLord;
import com.sygdsoft.service.CompanyLordService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class CompanyLordController {
    @Autowired
    CompanyLordService companyLordService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    TimeService timeService;

    @RequestMapping(value = "companyLordAdd")
    @Transactional
    public void companyLordAdd(@RequestBody CompanyLord companyLord) throws Exception {
        timeService.setNow();
        userLogService.addUserLog("单位-签单人:" + companyLord.getCompany() + '-' + companyLord.getName(), userLogService.company, userLogService.add,companyLord.getCompany() + ',' + companyLord.getName());
        companyLordService.add(companyLord);
    }

    @RequestMapping(value = "companyLordDelete")
    @Transactional(rollbackFor = Exception.class)
    public void companyLordDelete(@RequestBody List<CompanyLord> companyLordList) throws Exception {
        timeService.setNow();
        String s = "";
        for (CompanyLord companyLord : companyLordList) {
            s = s + companyLord.getCompany() + "-" + companyLord.getName() + "/";
        }
        s = s.substring(0, s.length() - 1);
        userLogService.addUserLog("删除:" + s, userLogService.company, userLogService.delete,s);
        companyLordService.delete(companyLordList);
    }

    @RequestMapping(value = "companyLordUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void companyLordUpdate(@RequestBody List<CompanyLord> companyLordList) throws Exception {
        if (companyLordList.size() > 1) {
            if (companyLordList.get(0).getId().equals(companyLordList.get(companyLordList.size() / 2).getId())) {
                timeService.setNow();
                String s = userLogService.parseListDeference(companyLordList);
                userLogService.addUserLog(s, userLogService.company, userLogService.update,null);
                companyLordService.update(companyLordList.subList(0, companyLordList.size() / 2));
                return;
            }
        }
        companyLordService.update(companyLordList);
    }

    @RequestMapping(value = "companyLordGet")
    public List<CompanyLord> companyLordGet(@RequestBody Query query) throws Exception {
        return companyLordService.get(query);
    }
}
