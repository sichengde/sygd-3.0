package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.VipCategory;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import com.sygdsoft.service.VipCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.omg.IOP.TAG_ORB_TYPE.value;

/**
 * Created by 舒展 on 2016-07-21.
 */
@RestController
public class VipCategoryController {
    @Autowired
    VipCategoryService vipCategoryService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "vipCategoryGet")
    public List<VipCategory> vipCategoryGet(@RequestBody Query query) throws Exception {
        return vipCategoryService.get(query);
    }

    @RequestMapping(value = "vipCategoryAdd")
    public void vipCategoryAdd(@RequestBody VipCategory vipCategory) throws Exception {
        vipCategoryService.add(vipCategory);
    }

    @RequestMapping(value = "vipCategoryDelete")
    public void vipCategoryDelete(@RequestBody List<VipCategory> vipCategoryList) throws Exception {
        vipCategoryService.delete(vipCategoryList);
    }

    @RequestMapping(value = "vipCategoryUpdate")
    public void vipCategoryUpdate(@RequestBody List<VipCategory> vipCategoryList) throws Exception {
        timeService.setNow();
        if (vipCategoryList.size() > 1) {
            if (vipCategoryList.get(0).getId().equals(vipCategoryList.get(vipCategoryList.size() / 2).getId())) {
                String s = userLogService.parseListDeference(vipCategoryList);
                userLogService.addUserLog(s, userLogService.vip, userLogService.update, null);
                vipCategoryService.update(vipCategoryList.subList(0, vipCategoryList.size() / 2));
                return;
            }
        }
        vipCategoryService.update(vipCategoryList);
    }
}
