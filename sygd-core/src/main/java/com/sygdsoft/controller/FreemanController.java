package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.FreeDetail;
import com.sygdsoft.model.Freeman;
import com.sygdsoft.service.FreeDetailService;
import com.sygdsoft.service.FreemanService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-07-26.
 */
@RestController
public class FreemanController {
    @Autowired
    FreemanService freemanService;
    @Autowired
    FreeDetailService freeDetailService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    Util util;

    @RequestMapping(value = "freemanAdd")
    public void freemanAdd(@RequestBody Freeman freeman) throws Exception {
        timeService.setNow();
        freemanService.add(freeman);
        userLogService.addUserLog("宴请签单人:" + freeman.getFreeman(), userLogService.company, userLogService.add, freeman.getFreeman());
    }

    @RequestMapping(value = "freemanDelete")
    @Transactional(rollbackFor = Exception.class)
    public void freemanDelete(@RequestBody List<Freeman> freemanList) throws Exception {
        timeService.setNow();
        String s = "";
        List<FreeDetail> freeDetailList = new ArrayList<>();
        for (Freeman freeman : freemanList) {
            s = s + freeman.getFreeman() + ":" + freeman.getConsume() + "元/";
            /*获取消费记录freeDetail并且删除*/
            freeDetailList.addAll(freeDetailService.get(new Query("freeman=" + util.wrapWithBrackets(freeman.getFreeman()))));
        }
        s = s.substring(0, s.length() - 1);
        userLogService.addUserLog("删除:" + s, userLogService.company, userLogService.delete, s);
        freemanService.delete(freemanList);
        freeDetailService.delete(freeDetailList);
    }

    @RequestMapping(value = "freemanUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void freemanUpdate(@RequestBody List<Freeman> freemanList) throws Exception {
        timeService.setNow();
        if (freemanList.size() > 1) {
            if (freemanList.get(0).getId().equals(freemanList.get(freemanList.size() / 2).getId())) {
                String s = userLogService.parseListDeference(freemanList);
                userLogService.addUserLog(s, userLogService.company, userLogService.update, null);
                freemanService.update(freemanList.subList(0, freemanList.size() / 2));
                return;
            }
        }
        freemanService.update(freemanList);
    }

    @RequestMapping(value = "freemanGet")
    public List<Freeman> freemanGet(@RequestBody Query query) throws Exception {
        return freemanService.get(query);
    }

}
