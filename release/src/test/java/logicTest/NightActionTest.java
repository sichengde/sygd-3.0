package logicTest;

import application.ApplicationTest;
import com.alibaba.fastjson.JSON;
import com.google.zxing.WriterException;
import com.sygdsoft.controller.DeskDetailController;
import com.sygdsoft.controller.NightController;
import com.sygdsoft.controller.StorageOutController;
import com.sygdsoft.controller.common.SzTableReportController;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.*;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.QrCodeUtil;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-08-24.
 */
public class NightActionTest extends ApplicationTest {
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
    @Autowired
    DebtIntegrationService debtIntegrationService;
    @Autowired
    GuestMapCheckInMapper guestMapCheckInMapper;
    @Autowired
    CheckInHistoryMapper checkInHistoryMapper;
    @Autowired
    StorageOutController storageOutController;
    @Autowired
    UserLogMapper userLogMapper;
    @Autowired
    UserLogService userLogService;
    @Autowired
    DeskCategoryService deskCategoryService;
    @Autowired
    DeskDetailController deskDetailController;

    @Test
    public void testdc() throws Exception {
        DeskDetail deskDetail=JSON.<DeskDetail>parseObject("{\"foodName\":\"厅饮料\",\"num\":\"1\",\"desk\":\"散台1\",\"price\":5,\"category\":\"酒水饮料\",\"pointOfSale\":\"餐饮部\",\"needInsert\":true,\"foodSign\":\"厅饮料\",\"foodSet\":false,\"ifDiscount\":null,\"cookroom\":\"吧台间\",\"cargo\":null,\"people\":1}",DeskDetail.class);
        List<DeskDetail> deskDetails= new ArrayList<>();
        deskDetails.add(deskDetail);
        deskDetailController.deskAction(deskDetails);
    }

    @Test
    public void testMapper2() throws Exception {
        UserLog userLog=new UserLog();
        RowBounds rowBounds = RowBounds.DEFAULT;
        rowBounds = new RowBounds(0, 1);
        Query query=new Query();
        query.setOrderByList(new String[]{"id"});
        List<UserLog> userLogList1=userLogService.get(query);
        Example example = new Example(UserLog.class);
        example.createCriteria().andCondition("id>1");
        List<UserLog> userLogList=userLogMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

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
    public void testQuarter() throws Exception {
        /*Date beginTime=timeService.getMinMonth(new Date());
        Date endTime=timeService.getMaxMonth(new Date() );
        System.out.println(beginTime);
        System.out.println(endTime);*/
        Date a = timeService.getMinMonth(new Date());
        timeService.setAdjustDay(20);
        System.out.println(timeService.adjustDay(a));
    }

    @Test
    public void testMapper() throws Exception {
        //List<String> a = guestMapCheckInMapper.getHistoryRoomPriceByCardId("3123123123123123", "普通标间");
    }

    @Test
    public void testGetListByCheckOutSerial() {
        List<CheckInHistory> checkInHistoryList = checkInHistoryService.getListByCheckOutSerial("160911005");
    }

    @Test
    public void getAllPrinter() throws Exception {
        reportService.ping("192.168.0.123");
    }

    @Autowired
    CalendarMapper calendarMapper;

    @Test
    public void generateCalendar() throws Exception {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat1.parse("2017-10-11");
        for (int i = 0; i < 1000; i++) {
            Calendar calendar = new Calendar();
            calendar.setDate(date);
            calendarMapper.insert(calendar);
            timeService.addDay(date, 1);
        }
    }

}
