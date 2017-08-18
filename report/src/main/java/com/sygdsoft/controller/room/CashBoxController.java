package com.sygdsoft.controller.room;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CashBox;
import com.sygdsoft.service.CashBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CashBoxController {
    @Autowired
    CashBoxService cashBoxService;

    @RequestMapping(value = "cashBoxGet")
    public List<CashBox> cashBoxGet(@RequestBody Query query)throws Exception{
        query.setOrderByListDesc(new String[]{"id"});
        return cashBoxService.get(query);
    }

    @RequestMapping(value = "cashBoxGetLast")
    public CashBox cashBoxGetLast()throws Exception{
        return cashBoxService.cashBoxGetLast();
    }

    @RequestMapping(value = "cashBoxAdd")
    public void cashBoxAdd(@RequestBody CashBox cashBox)throws Exception{
        cashBoxService.add(cashBox);
    }

}
