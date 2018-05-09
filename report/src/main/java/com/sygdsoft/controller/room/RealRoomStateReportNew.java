package com.sygdsoft.controller.room;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Book;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.Room;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/5/20.
 */
@RestController
public class RealRoomStateReportNew {
    @Autowired
    TimeService timeService;
    @Autowired
    RoomService roomService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    Util util;
    @Autowired
    SzMath szMath;
    @Autowired
    HotelService hotelService;
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "realRoomStateReportNew")
    public Integer realRoomStateReportNew() throws Exception {
        /*报表参数*/
        Integer nullRoom = 0;
        Integer inRoom = 0;
        Integer dirtyRoom = 0;
        Integer repairRoom = 0;
        Integer bookRoom;
        Double totalPrice = 0.0;
        /*中间变量*/
        Integer sumCheckInRoom = 0;
        timeService.setNow();
        List<FieldTemplate> templateList = new ArrayList<>();
        Query query = new Query();
        query.setOrderByList(new String[]{"room_id"});
        List<Room> roomList = roomService.get(query);
        roomService.setRoomDetail(roomList);
        for (Room room : roomList) {
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(room.getRoomId());
            fieldTemplate.setField2(room.getCategory());
            String roomState = room.getState();
            fieldTemplate.setField3(roomState);
            if (roomState.equals(roomService.group) || roomState.equals(roomService.guest)) {
                CheckIn checkIn=room.getCheckIn();
                if(checkIn!=null) {
                    Double finalRoomPrice = room.getCheckIn().getFinalRoomPrice();
                    fieldTemplate.setField4(String.valueOf(finalRoomPrice));
                    fieldTemplate.setField5(String.valueOf(checkIn.getBreakfast()));
                    totalPrice = totalPrice + finalRoomPrice;
                    sumCheckInRoom++;
                    inRoom++;
                }
            } else if (roomState.equals(roomService.empty)) {
                nullRoom++;
            } else if (roomState.equals(roomService.repair)) {
                repairRoom++;
            }
            /*脏房*/
            if (room.getNotNullDirty()) {
                dirtyRoom++;
            }
            templateList.add(fieldTemplate);
        }
        /*查询今日预定房数*/
        query = new Query();
        query.setCondition("reach_time like " + util.wrapWithBrackets(timeService.getNowShort() + "%"));
        List<Book> bookList = bookService.get(query);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(templateList);
        bookRoom = bookList.size();
        Integer length = roomList.size();
        // 动态指定报表模板url
        String[] param = new String[]{timeService.getNowLong(),
                userService.getCurrentUser(),
                String.valueOf(nullRoom) + "/" + szMath.formatPercent(nullRoom, length),
                String.valueOf(inRoom) + "/" + szMath.formatPercent(inRoom, length),
                String.valueOf(dirtyRoom) + "/" + szMath.formatPercent(dirtyRoom, length),
                String.valueOf(repairRoom) + "/" + szMath.formatPercent(repairRoom, length),
                String.valueOf(bookRoom) + "/" + szMath.formatPercent(bookRoom, length),
                szMath.formatPercent(totalPrice, sumCheckInRoom),
                szMath.formatPercent(totalPrice, length),
                szMath.formatTwoDecimal(totalPrice)};
        return reportService.generateReport(templateList, param, "realRoomState", "pdf");
    }
}
