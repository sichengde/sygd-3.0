package logicTest;

import application.ApplicationTest;
import com.google.zxing.WriterException;
import com.sygdsoft.controller.NightController;
import com.sygdsoft.controller.SzTableReportController;
import com.sygdsoft.mapper.BookRoomMapper;
import com.sygdsoft.mapper.CalendarMapper;
import com.sygdsoft.model.Calendar;
import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.service.*;
import com.sygdsoft.util.QrCodeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-08-24.
 */
public class NightActionTest extends ApplicationTest{
    @Autowired
    Night night;
    @Autowired
    BookRoomMapper bookRoomMapper;
    @Autowired
    RegisterService registerService;
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    CheckInHistoryService checkInHistoryService;
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    ReportService reportService;
    @Autowired
    NightController nightController;
    @Autowired
    SzTableReportController szTableReportController;
    @Autowired
    QrCodeUtil qrCodeUtil;
    @Autowired
    TimeService timeService;

    @Test
    public void nightAction() throws Exception {
        night.autoNightAction();
        //nightController.testNight();
    }

    @Test
    public void testBarcodeUtil() throws IOException, WriterException {
        qrCodeUtil.generate("哈哈好使了");
    }
    @Test
    public void testQuarter()throws Exception{
        /*Date beginTime=timeService.getMinMonth(new Date());
        Date endTime=timeService.getMaxMonth(new Date() );
        System.out.println(beginTime);
        System.out.println(endTime);*/
        System.out.println(timeService.getMonthCN(new Date()));
    }

    @Test
    public void testMapper() throws Exception {
        deskPayService.getDeskMoneyByCurrencyDateUser(null,null,null,null);
    }

    @Test
    public void testRegisterService() throws Exception{
        registerService.sendMessage();
    }

    @Test
    public void testGetListByCheckOutSerial(){
        List<CheckInHistory> checkInHistoryList=checkInHistoryService.getListByCheckOutSerial("160911005");
    }

    @Test
    public void getAllPrinter() throws Exception {
        reportService.ping("192.168.0.123");
    }

    @Autowired
    CalendarMapper calendarMapper;
    @Test
    public void generateCalendar()throws Exception{
        Calendar calendar=new Calendar();
        calendar.setDate(new Date("2015-01-01"));
    }


}
