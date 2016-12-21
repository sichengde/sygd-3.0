package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.SaunaUser;
import com.sygdsoft.service.SaunaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
@RestController
public class SaunaUserController {
    @Autowired
    SaunaUserService saunaUserService;

    @RequestMapping(value = "saunaUserAdd")
    public void saunaUserAdd(@RequestBody SaunaUser saunaUser) throws Exception {
        saunaUserService.add(saunaUser);
    }

    @RequestMapping(value = "saunaUserDelete")
    @Transactional(rollbackFor = Exception.class)
    public void saunaUserDelete(@RequestBody List<SaunaUser> saunaUserList) throws Exception {
        saunaUserService.delete(saunaUserList);
    }

    @RequestMapping(value = "saunaUserUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void saunaUserUpdate(@RequestBody List<SaunaUser> saunaUserList) throws Exception {
        if (saunaUserList.size() > 1) {
            if (saunaUserList.get(0).getId().equals(saunaUserList.get(saunaUserList.size() / 2).getId())) {
                saunaUserService.update(saunaUserList.subList(0, saunaUserList.size() / 2));
                return;
            }
        }
        saunaUserService.update(saunaUserList);
    }

    @RequestMapping(value = "saunaUserGet")
    public List<SaunaUser> saunaUserGet(@RequestBody Query query) throws Exception {
        return saunaUserService.get(query);
    }
}
