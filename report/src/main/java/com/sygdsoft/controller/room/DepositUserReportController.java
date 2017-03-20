package com.sygdsoft.controller.room;

import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.room.DepositUserRow;
import com.sygdsoft.service.DebtIntegrationService;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-03-20.
 */
@RestController
public class DepositUserReportController {
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "depositUserReport")
    List<DepositUserRow> depositUserReport(@RequestBody ReportJson reportJson){
        Date beginTime=reportJson.getBeginTime();
        Date endTime=reportJson.getEndTime();
        List<DebtIntegration> debtIntegrationList=debtIntegrationService.getSumDepositByDate(beginTime, endTime);
        List<DepositUserRow> depositUserRowList=new ArrayList<>();
        for (DebtIntegration debtIntegration : debtIntegrationList) {
            DepositUserRow depositUserRow=new DepositUserRow();
            depositUserRow.setCurrency(debtIntegration.getCurrency());
            depositUserRow.setDeposit(szMath.formatTwoDecimal(debtIntegration.getDeposit()));
            depositUserRow.setUser(debtIntegration.getUserId());
            depositUserRowList.add(depositUserRow);
        }
        return depositUserRowList;
    }
}
