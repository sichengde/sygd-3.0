package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.RoomMapper;
import com.sygdsoft.model.Book;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGuest;
import com.sygdsoft.model.Room;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SzMapper(id = "room")
public class RoomService extends BaseService<Room> {
    public String empty = "可用房";//
    public String group = "团队房";//
    public String guest = "散客房";//
    public String leave = "走客房";//
    public String repair = "维修房";//
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    BookService bookService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    Util util;
    @Autowired
    OtherParamService otherParamService;

    /**
     * 更新房态
     */
    public void updateRoomState(List<String> roomList, String state) {
        for (String s : roomList) {
            roomMapper.updateRoomState(s, state);
        }
    }

    public void updateRoomState(String roomId, String state) {
        roomMapper.updateRoomState(roomId, state);
    }
    /**
     * 将房间设置为脏房
     */
    public void dirtyRoom(String roomId){
        roomMapper.dirtyRoom(roomId);
    }

    /**
     * 离店结算时通过房号更新房态
     * 1.房态改为走客房
     * 2.设置离店时间
     * 3.设置为脏房
     */
    public void updateRoomStateGuestOut(String roomId) {
        roomMapper.updateRoomStateGuestOut(roomId, leave,timeService.getNow());
    }

    /**
     * 设置在店户籍，在店宾客，团队信息，预订信息
     */
    public void setRoomDetail(List<Room> roomList) throws Exception {
        for (Room room : roomList) {
            Query query1=new Query("room_id="+util.wrapWithBrackets(room.getRoomId()));
            List<CheckIn> checkInList=checkInService.get(query1);
            Long longRoomDay= Long.valueOf(otherParamService.getValueByName("长包房天数"));
            if(checkInList.size()>0){
                CheckIn checkIn=checkInList.get(0);
                room.setCheckIn(checkIn);
                if(longRoomDay!=null&&checkIn.getLeaveTime()!=null){
                    if(checkIn.getLeaveTime().getTime()-checkIn.getReachTime().getTime()>longRoomDay*24*60*60*1000){
                        room.setLongRoom(true);
                    }
                }
                room.setCheckInGuestList(checkInGuestService.get(query1));
                room.setCheckInGroup(checkInGroupService.getByGroupAccount(checkIn.getGroupAccount()));
                /*判断是不是预离*/
                if(timeService.dateToStringShort(checkIn.getLeaveTime()).equals(timeService.getNowShort())){
                    room.setTodayLeave(true);
                }
                /*判断是不是生日*/
                List<CheckInGuest> checkInGuestList=room.getCheckInGuestList();
                for (CheckInGuest checkInGuest : checkInGuestList) {
                    if(timeService.dateToStringShort(checkInGuest.getBirthdayTime()).equals(timeService.getNowShort())){
                        room.setBirthday(true);
                    }
                }
            }
            List<Book> bookList=bookService.getBookByRoomId(room.getRoomId());
            if(bookList.size()>0) {
                room.setBookList(bookList);
            }
        }
    }
    /**
     * 房间列表转字符串
     */
    public String roomListToString(List<String> roomList){
        if(roomList==null){
            return "";
        }
        String out="";
        for (String s : roomList) {
            out+=s+",";
        }
        return out.substring(0,out.length()-1);
    }

    /**
     * 获得该房类的全部房数
     */
    public String getTotalCategoryNum(String category){
        return roomMapper.getTotalCategoryNum(category);
    }

    /**
     * 通过房号字符串获得房间对象数组
     */
    public List<Room> getListByRoomIdString(List<String> roomIdList){
        return roomMapper.getListByRoomIdString(util.listToString(roomIdList));
    }
}
