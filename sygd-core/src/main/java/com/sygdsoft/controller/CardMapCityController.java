package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CardMapCity;
import com.sygdsoft.service.CardMapCityService;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class CardMapCityController {
    @Autowired
    CardMapCityService cardMapCityService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;

    @RequestMapping(value = "cardMapCityAdd")
    public void cardMapCityAdd(@RequestBody CardMapCity cardMapCity) throws Exception {
        cardMapCityService.add(cardMapCity);
    }

    @RequestMapping(value = "cardMapCityDelete")
    @Transactional(rollbackFor = Exception.class)
    public void cardMapCityDelete(@RequestBody List<CardMapCity> cardMapCityList) throws Exception {
        cardMapCityService.delete(cardMapCityList);
    }

    @RequestMapping(value = "cardMapCityGet")
    public List<CardMapCity> cardMapCityGet(@RequestBody Query query) throws Exception {
        return cardMapCityService.get(query);
    }

    @RequestMapping(value = "cardMapCityUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void cardMapCityUpdate(@RequestBody List<CardMapCity> cardMapCityList) throws Exception {
        if (cardMapCityList.size() > 1) {
            if (cardMapCityList.get(0).getId().equals(cardMapCityList.get(cardMapCityList.size() / 2).getId())) {
                cardMapCityService.update(cardMapCityList.subList(0, cardMapCityList.size() / 2));
                return;
            }
        }
        cardMapCityService.update(cardMapCityList);
    }
}
