package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.ExchangeUser;
import com.sygdsoft.service.ExchangeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-27.
 */
@RestController
public class ExchangeUserController {
    @Autowired
    ExchangeUserService exchangeUserService;

    @RequestMapping(value = "exchangeUserAdd")
    public void exchangeUserAdd(@RequestBody ExchangeUser exchangeUser) throws Exception {
        exchangeUser.setBeginTime(exchangeUser.getRealBeginT());
        exchangeUser.setEndTime(exchangeUser.getRealEndT());
        exchangeUserService.add(exchangeUser);
    }

    @RequestMapping(value = "exchangeUserDelete")
    @Transactional(rollbackFor = Exception.class)
    public void protocolDelete(@RequestBody List<ExchangeUser> exchangeUserList) throws Exception {
        exchangeUserService.delete(exchangeUserList);
    }

    @RequestMapping(value = "exchangeUserUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void protocolUpdate(@RequestBody List<ExchangeUser> exchangeUserList) throws Exception {
        for (ExchangeUser exchangeUser : exchangeUserList) {
            exchangeUser.setBeginTime(exchangeUser.getRealBeginT());
            exchangeUser.setEndTime(exchangeUser.getRealEndT());
        }
        if (exchangeUserList.size() > 1) {
            if (exchangeUserList.get(0).getId().equals(exchangeUserList.get(exchangeUserList.size() / 2).getId())) {
                exchangeUserService.update(exchangeUserList.subList(0, exchangeUserList.size() / 2));
                return;
            }
        }
        exchangeUserService.update(exchangeUserList);
    }

    @RequestMapping(value = "exchangeUserGet")
    public List<ExchangeUser> protocolGet(@RequestBody Query query) throws Exception {
        return exchangeUserService.get(query);
    }
}
