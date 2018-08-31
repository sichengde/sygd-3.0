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

    /**
     * field1 房号
     * field2 房类
     * field3 状态
     * field4 价格
     * field5 早餐数量
     * parameter1  时间
     * parameter2  操作员
     * parameter3  空房
     * parameter4  入住
     * parameter5  脏房
     * parameter6  维修房
     * parameter7  预定
     * parameter8  平均房租
     * parameter9  总平均房租
     * parameter10  消费合计
     * parameter11  早餐份数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "realRoomStateReportNew")
    public Integer realRoomStateReportNew() throws Exception {
        /*报表参数*/
        Integer nullRoom = 0;
        Integer inRoom = 0;
        Integer dirtyRoom = 0;
        Integer repairRoom = 0;
        Integer bookRoom;
        Double totalPrice = 0.0;
        int totalBreakfast = 0;
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
                CheckIn checkIn = room.getCheckIn();
                if (checkIn != null) {
                    Double finalRoomPrice = room.getCheckIn().getFinalRoomPrice();
                    fieldTemplate.setField4(String.valueOf(finalRoomPrice));
                    fieldTemplate.setField5(String.valueOf(checkIn.getBreakfast()));
                    totalPrice = totalPrice + finalRoomPrice;
                    sumCheckInRoom++;
                    inRoom++;
                    String breakfast = checkIn.getBreakfast();
                    if (breakfast != null) {
                        totalBreakfast += Integer.valueOf(breakfast);
                    }
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
        String[] param = new String[]{
                timeService.getNowLong(),//时间
                userService.getCurrentUser(),//操作员
                String.valueOf(nullRoom) + "/" + szMath.formatPercent(nullRoom, length),//空房
                String.valueOf(inRoom) + "/" + szMath.formatPercent(inRoom, length),//入住
                String.valueOf(dirtyRoom) + "/" + szMath.formatPercent(dirtyRoom, length),//脏房
                String.valueOf(repairRoom) + "/" + szMath.formatPercent(repairRoom, length),//维修房
                String.valueOf(bookRoom) + "/" + szMath.formatPercent(bookRoom, length),//预定
                szMath.formatPercent(totalPrice, sumCheckInRoom),//平均房租
                szMath.formatPercent(totalPrice, length),//总平均房租
                szMath.formatTwoDecimal(totalPrice),//消费合计
                String.valueOf(totalBreakfast)//早餐份数
        };
        return reportService.generateReport(templateList, param, "realRoomState", "pdf");
    }
}
