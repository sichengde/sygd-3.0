package com.sygdsoft.controller.room;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Book;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.Room;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-06-23.
 * 实时房态报表
 */
@Controller
public class RealRoomStateReport {
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

    @RequestMapping(value = "realRoomStateReport")
    public String realRoomStateReport(Model model) throws Exception {
        /*报表参数*/
        Integer nullRoom=0;
        Integer inRoom=0;
        Integer dirtyRoom=0;
        Integer repairRoom=0;
        Integer bookRoom;
        Double totalPrice=0.0;
        /*中间变量*/
        Integer sumCheckInRoom=0;
        timeService.setNow();
        List<FieldTemplate> templateList = new ArrayList<>();
        Query query=new Query();
        query.setOrderByList(new String[]{"room_id"});
        List<Room> roomList = roomService.get(query);
        roomService.setRoomDetail(roomList);
        for (Room room : roomList) {
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(room.getRoomId());
            fieldTemplate.setField2(room.getCategory());
            String roomState=room.getState();
            fieldTemplate.setField3(roomState);
            if (roomState.equals(roomService.group)||roomState.equals(roomService.guest)){
                Double var1=room.getCheckIn().getFinalRoomPrice();
                fieldTemplate.setField4(String.valueOf(var1));
                totalPrice=totalPrice+var1;
                sumCheckInRoom++;
                inRoom++;
            }else if(roomState.equals(roomService.empty)){
                nullRoom++;
            }else if(roomState.equals(roomService.repair)){
                repairRoom++;
            }
            /*脏房*/
            if(room.getNotNullDirty()){
                dirtyRoom++;
            }
            templateList.add(fieldTemplate);
        }
        /*查询今日预定房数*/
        query=new Query();
        query.setCondition("reach_time like "+util.wrapWithBrackets(timeService.getNowShort()+"%"));
        List<Book> bookList=bookService.get(query);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(templateList);
        bookRoom=bookList.size();
        Integer length=roomList.size();
        // 动态指定报表模板url
        model.addAttribute("url", "file:///C:/report/realRoomState.jasper");
        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);
        model.addAttribute("parameter1", timeService.getNowLong());
        model.addAttribute("parameter2", userService.getCurrentUser());
        model.addAttribute("parameter3", String.valueOf(nullRoom)+"/"+szMath.getPercent(nullRoom,length));
        model.addAttribute("parameter4", String.valueOf(inRoom)+"/"+szMath.getPercent(inRoom,length));
        model.addAttribute("parameter5", String.valueOf(dirtyRoom)+"/"+szMath.getPercent(dirtyRoom,length));
        model.addAttribute("parameter6", String.valueOf(repairRoom)+"/"+szMath.getPercent(repairRoom,length));
        model.addAttribute("parameter7", String.valueOf(bookRoom)+"/"+szMath.getPercent(bookRoom,length));
        model.addAttribute("parameter8", szMath.getPercent(totalPrice,sumCheckInRoom));
        model.addAttribute("parameter9", szMath.getPercent(totalPrice,length));
        model.addAttribute("parameter10", totalPrice);
        return "reportView";
    }
}
