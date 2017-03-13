package com.sygdsoft.controller.room;

import com.sygdsoft.controller.HotelParseController;
import com.sygdsoft.model.HotelParseRow;
import com.sygdsoft.model.PointOfSale;
import com.sygdsoft.model.ReportJson;
import com.sygdsoft.model.room.DebtAndPayReturn;
import com.sygdsoft.model.room.DebtAndPayRow;
import com.sygdsoft.service.PointOfSaleService;
import com.sygdsoft.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
@RestController
public class DebtAndPayReportController {
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    HotelParseController hotelParseController;
    @RequestMapping(value = "debtAndPayReport")
    public DebtAndPayReturn debtAndPayReport(@RequestBody ReportJson reportJson) throws Exception {
        DebtAndPayReturn debtAndPayReturn=new DebtAndPayReturn();
        List<DebtAndPayRow> debtAndPayRowList=new ArrayList<>();
        DebtAndPayRow debtAndPayRow;
        /*先生成全店收入表*/
        List<HotelParseRow> hotelParseRowList=hotelParseController.hotelParse(reportJson);
        for (HotelParseRow hotelParseRow : hotelParseRowList) {
            debtAndPayRow=new DebtAndPayRow();
            debtAndPayRow.setTitle(hotelParseRow.getPointOfSale());
            debtAndPayRow.setDebtDay(hotelParseRow.getDayTotal());
            debtAndPayRow.setDebtMonth(hotelParseRow.getMonthTotal());
            debtAndPayRow.setDebtYear(hotelParseRow.getYearTotal());
        }
        debtAndPayReturn.setDebtAndPayRowList(debtAndPayRowList);
        return debtAndPayReturn;
    }
}
