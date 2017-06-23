package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 舒展 on 2016-03-22.
 */
@RestController
public class RoomController {
    @Autowired
    RoomService roomService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckInGuestService checkInGuestService;
    @Autowired
    Util util;
    @Autowired
    DebtService debtService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    CleanRoomService cleanRoomService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    ProtocolService protocolService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    PointOfSaleService pointOfSaleService;

    @RequestMapping(value = "roomAdd")
    public void roomAdd(@RequestBody Room room) throws Exception {
        /*增加的房间可以通过' - '来指定区间*/
        String[] roomRange = room.getRoomId().split("-");
        if (roomRange.length > 1) {//这是一个区间
            String prefix = Pattern.compile("[0-9]").matcher(roomRange[0]).replaceAll("");//前缀
            String roomIdMinString = Pattern.compile("[^0-9]").matcher(roomRange[0]).replaceAll("");
            /*计算一下数字位数*/
            Integer length = roomIdMinString.length();
            Integer roomIdMin = Integer.valueOf(roomIdMinString);//最小数
            Integer roomIdMax = Integer.valueOf(Pattern.compile("[^0-9]").matcher(roomRange[1]).replaceAll(""));//最大数
            List<Room> roomList = new ArrayList<>();
            Integer range = roomIdMax - roomIdMin;
            for (int i = 0; i < range + 1; i++) {
                Room roomTmp = new Room(room);
                roomTmp.setRoomId(String.format(prefix + "%0" + length + "d", roomIdMin + i));
                roomList.add(roomTmp);
            }
            roomService.add(roomList);
        } else {
            roomService.add(room);
        }
    }

    @RequestMapping(value = "roomDelete")
    @Transactional(rollbackFor = Exception.class)
    public void roomDelete(@RequestBody List<Room> roomList) throws Exception {
        roomService.delete(roomList);
    }

    @RequestMapping(value = "roomUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void roomUpdate(@RequestBody List<Room> roomList) throws Exception {
        if (roomList.size() > 1) {
            if (roomList.get(0).getId().equals(roomList.get(roomList.size() / 2).getId())) {
                roomService.update(roomList.subList(0, roomList.size() / 2));
                return;
            }
        }
        roomService.update(roomList);
    }

    @RequestMapping(value = "roomGet")
    public List<Room> roomGet(@RequestBody Query query) throws Exception {
        timeService.setNow();
        List<Room> roomList = roomService.get(query);
        roomService.setRoomDetail(roomList);
        return roomList;
    }

    /**
     * 只获取房间数据，不设置其他内容
     */
    @RequestMapping(value = "roomGetPure")
    public List<Room> roomGetPure(@RequestBody Query query) throws Exception {
        List<Room> roomList = roomService.get(query);
        return roomList;
    }

    /**
     * 宾客换房
     */
    @RequestMapping(value = "changeRoom")
    @Transactional(rollbackFor = Exception.class)
    public void changeRoom(@RequestBody List<Room> params) throws Exception {
        Room srcRoom = params.get(0);
        Room dstRoom = params.get(1);
        String srcRoomId = srcRoom.getRoomId();//起源房号
        String dstRoomId = dstRoom.getRoomId();//目标房号
        /*更新房态*/
        roomService.updateRoomState(srcRoomId, roomService.leave);
        roomService.updateRoomState(dstRoomId, srcRoom.getState());
        /*更新在店户籍,设置为新的房价房号*/
        CheckIn checkIn = checkInService.getByRoomId(srcRoomId);
        checkIn.setRoomId(dstRoomId);
        checkIn.setFinalRoomPrice(dstRoom.getPrice());
        if (otherParamService.getValueByName("可编辑房价").equals("y")) {//如果是可编辑房价的话，还要考虑房价协议名称的问题
            /*房价协议表先更新*/
            Protocol needUpdate = protocolService.getByNameTemp(checkIn.getProtocol());
            if (needUpdate != null) {//团队开房的话就没有生成新的协议
                String newProtocol = checkIn.getProtocol().replaceFirst(srcRoomId, dstRoomId);
                needUpdate.setProtocol(newProtocol);
                needUpdate.setRoomPrice(dstRoom.getPrice());
                protocolService.update(needUpdate);
                checkIn.setProtocol(newProtocol);
            }
        }
        //不更新消费
        checkIn.setConsume(null);
        checkIn.setDeposit(null);
        checkInService.updateSelective(checkIn);
        /*更新宾客信息*/
        List<CheckInGuest> checkInGuestList = srcRoom.getCheckInGuestList();
        for (CheckInGuest checkInGuest : checkInGuestList) {
            checkInGuest.setRoomId(dstRoomId);
        }
        checkInGuestService.update(checkInGuestList);
        /*更新账务*/
        List<Debt> debtAddList=new ArrayList<>();
        List<Debt> debtList = debtService.get(new Query("room_id=" + util.wrapWithBrackets(srcRoomId)));
        for (Debt debt : debtList) {
            debt.setRoomId(dstRoomId);
            if (debt.getCategory().equals("凌晨房费")) {
                /*如果是凌晨房，补齐杂单或者冲账*/
                Double money = checkIn.getFinalRoomPrice() - srcRoom.getCheckIn().getFinalRoomPrice();
                if (money != 0.0) {
                    Debt debtAdd = new Debt();
                    debtAdd.setDoTime(timeService.getNow());
                    debtAdd.setPointOfSale(pointOfSaleService.FF);
                    debtAdd.setConsume(money);
                    debtAdd.setCurrency("挂账");
                    debtAdd.setGuestSource(checkIn.getGuestSource());
                    debtAdd.setDescription("换房补齐差价");
                    debtAdd.setSelfAccount(checkIn.getSelfAccount());
                    debtAdd.setRoomId(checkIn.getRoomId());
                    debtAdd.setProtocol(checkIn.getProtocol());
                    debtAdd.setUserId(checkIn.getUserId());
                    debtAdd.setCategory(debtService.deposit);
                    debtAdd.setCompany(checkIn.getCompany());
                    debtAddList.add(debtAdd);
                }
            }
        }
        debtService.update(debtList);
        if(debtAddList.size()>0) {
            debtService.addDebt(debtAddList);
        }
        userLogService.addUserLog("宾客换房从 " + srcRoomId + " 换至 " + dstRoomId, userLogService.reception, userLogService.changeRoom, null);
    }

    /**
     * 转入现有团队
     */
    @RequestMapping(value = "guestToGroup")
    @Transactional(rollbackFor = Exception.class)
    public void guestToGroup(@RequestBody Room room) throws Exception {
        CheckIn checkIn = room.getCheckIn();
        /*更新在店户籍*/
        checkIn.setDeposit(null);
        checkIn.setConsume(null);
        checkInService.updateSelective(checkIn);
        /*更新房间状态*/
        roomService.update(room);
        /*更新团队信息，主要是金额*/
        checkInGroupService.update(room.getCheckInGroup());
        /*更新账务信息*/
        debtService.setGroupAccountByRoomId(room.getRoomId(), room.getCheckInGroup().getGroupAccount());
    }

    /**
     * 客房清扫
     */
    @RequestMapping(value = "cleanRoom")
    @Transactional(rollbackFor = Exception.class)
    public void cleanRoom(@RequestBody CleanRoomPost cleanRoomPost) throws Exception {
        List<Room> roomList = cleanRoomPost.getRoomList();
        timeService.setNow();
        String leaveRoomId = "";//走客房打扫
        String inRoomId = "";//在住房打扫
        Integer leaveRoom = 0;
        Integer inRoom = 0;
        String roomString = "";
        for (Room room : roomList) {
            room.setDirty(false);
            if (room.getState().equals(roomService.leave)) {
                leaveRoomId += room.getRoomId() + ",";
                leaveRoom++;
                room.setState(roomService.empty);
            } else {
                inRoomId += room.getRoomId() + ",";
                inRoom++;
            }
            roomString += ",";
        }
        roomString = roomString.substring(0, roomString.length() - 1);
        roomService.update(roomList);
        CleanRoom cleanRoom = new CleanRoom();
        cleanRoom.setDoTime(timeService.getNow());
        cleanRoom.setUserId(cleanRoomPost.getUserId());
        if (leaveRoom > 0) {
            cleanRoom.setCategory(CleanRoomService.leaveRoom);
            cleanRoom.setRoom(util.removeLast(leaveRoomId));
            cleanRoom.setNum(leaveRoom);
            cleanRoomService.add(cleanRoom);
        }
        if (inRoom > 0) {
            cleanRoom.setId(null);
            cleanRoom.setCategory(CleanRoomService.inRoom);
            cleanRoom.setRoom(util.removeLast(inRoomId));
            cleanRoom.setNum(inRoom);
            cleanRoomService.add(cleanRoom);
        }
        userLogService.addUserLog("客房清扫:" + roomString, userLogService.reception, userLogService.cleanRoom, roomString);
    }
}
