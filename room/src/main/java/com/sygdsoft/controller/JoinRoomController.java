package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGroup;
import com.sygdsoft.model.Debt;
import com.sygdsoft.model.JoinRoomPost;
import com.sygdsoft.service.*;
import com.sygdsoft.util.ListToSting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-04.
 */
@RestController
public class JoinRoomController {

    @Autowired
    DebtService debtService;
    @Autowired
    SerialService serialService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    RoomService roomService;

    /**
     * 散客联房
     * @param joinRoomPost
     * @throws Exception
     */
    @RequestMapping(value = "joinRoom")
    @Transactional(rollbackFor = Exception.class)
    public void joinRoom(@RequestBody JoinRoomPost joinRoomPost) throws Exception {
        /*获取/设置公付账号*/
        String groupAccount=serialService.setGroupAccount();
        Double totalDeposit=0.0;
        Double totalConsume=0.0;
        Double totalPay=0.0;
        List<String> roomList=joinRoomPost.getRoomAddList();
        String remark=joinRoomPost.getRemark();
        Date firstTime=null;
        String lastCompany="NULL";
        boolean sameCompany=true;
        for (String roomId : roomList) {
            /*更新房间状态*/
            roomService.updateRoomState(roomId,roomService.group);
            /*为每一条在店户籍设置公付账号*/
            CheckIn checkIn = checkInService.getByRoomId(roomId);
            /*检查所有散客是否是一个单位*/
            if(lastCompany==null||(!lastCompany.equals(checkIn.getCompany())&&!lastCompany.equals("NULL"))){
                sameCompany=false;
            }else {
                lastCompany=checkIn.getCompany();
            }
            if(firstTime==null){
                firstTime=checkIn.getReachTime();
            }else {
                if(firstTime.compareTo(checkIn.getReachTime()) > 0){
                    firstTime=checkIn.getReachTime();
                }
            }
            checkIn.setGroupAccount(groupAccount);
            debtService.setGroupAccountByRoomId(roomId, groupAccount);
            /*合计押金*/
            totalDeposit = totalDeposit + checkIn.getNotNullDeposit();
            /*合计消费*/
            totalConsume = totalConsume + checkIn.getNotNullConsume();
            /*合计支付（中间）*/
            totalPay = totalPay + checkIn.getNotNullPay();
            checkIn.setDeposit(null);
            checkIn.setConsume(null);
            checkInService.updateSelective(checkIn);
        }
        /*新建一个团队开房信息*/
        CheckIn checkInLeader = checkInService.getByRoomId(roomList.get(0));
        CheckInGroup checkInGroup=new CheckInGroup();
        if(sameCompany){
            checkInGroup.setCompany(lastCompany);
        }
        checkInGroup.setGroupAccount(groupAccount);
        checkInGroup.setName("联房" + groupAccount);
        checkInGroup.setLeader("联房");
        checkInGroup.setLeaderRoom(roomList.get(0));
        checkInGroup.setVipNumber(checkInLeader.getVipNumber());
        checkInGroup.setDeposit(totalDeposit);
        checkInGroup.setConsume(totalConsume);
        checkInGroup.setPay(totalPay);
        checkInGroup.setTotalRoom(roomList.size());
        checkInGroup.setRemark(remark);
        checkInGroup.setReachTime(firstTime);
        checkInGroupService.add(checkInGroup);
        String roomString=ListToSting.valueOf(roomList);
        userLogService.addUserLog("联房:" + roomString, userLogService.reception,userLogService.joinRoom,roomString);
    }

    /**
     * 散客加入团队
     */
    @RequestMapping(value = "joinRoomGroup")
    @Transactional(rollbackFor = Exception.class)
    public void joinRoomGroup(@RequestBody JSONObject jsonObject) throws Exception {
        /*获取/设置公付账号*/
        String groupAccount=jsonObject.getString("groupAccount");
        JSONArray roomList=jsonObject.getJSONArray("roomAddList");
        Double totalDeposit=0.0;
        Double totalConsume=0.0;
        Double totalPay=0.0;
        StringBuilder roomString= new StringBuilder();
        for (Object roomIdObj : roomList) {
            String roomId=(String) roomIdObj;
            roomString.append(roomId).append(",");
            /*更新房间状态*/
            roomService.updateRoomState(roomId,roomService.group);
            /*为每一条在店户籍设置公付账号*/
            CheckIn checkIn = checkInService.getByRoomId(roomId);
            checkIn.setGroupAccount(groupAccount);
            debtService.setGroupAccountByRoomId(roomId, groupAccount);
            /*合计押金*/
            totalDeposit = totalDeposit + checkIn.getNotNullDeposit();
            /*合计消费*/
            totalConsume = totalConsume + checkIn.getNotNullConsume();
            /*合计支付（中间）*/
            totalPay = totalPay + checkIn.getNotNullPay();
            checkIn.setDeposit(null);
            checkIn.setConsume(null);
            checkInService.updateSelective(checkIn);
        }
        /*更新团队信息*/
        CheckInGroup checkInGroup=checkInGroupService.getByGroupAccount(groupAccount);
        checkInGroup.setDeposit(checkInGroup.getNotNullGroupDeposit()+totalDeposit);
        checkInGroup.setConsume(checkInGroup.getNotNullGroupConsume()+totalConsume);
        checkInGroup.setPay(checkInGroup.getNotNullGroupPay()+totalPay);
        checkInGroup.setTotalRoom(checkInGroup.getTotalRoom()+roomList.size());
        checkInGroupService.update(checkInGroup);
        userLogService.addUserLog("散转团:" + roomString, userLogService.reception,userLogService.joinRoom, roomString.toString());
    }

    /**
     * 分为完全解除和部分解除，完全解除直接删除掉checkInGroup即可，部分解除则需要更新其中的消费等条目
     */
    @RequestMapping(value = "unBindRoom")
    @Transactional(rollbackFor = Exception.class)
    public void unBindRoom(@RequestBody List<String> roomList) throws Exception {
        Double totalDeposit=0.0;
        Double totalConsume=0.0;
        Double totalPay=0.0;
        String groupAccount=checkInService.getGroupAccount(roomList.get(0));
        for (String roomId : roomList) {
            roomService.updateRoomState(roomId,roomService.guest);
            /*清除在店户籍里的公付账号*/
            CheckIn checkIn = checkInService.getByRoomId(roomId);
            checkIn.setGroupAccount(null);
            checkInService.update(checkIn);
            /*清除账务明细里的公付账号*/
            List<Debt> debtList = debtService.getListByRoomId(roomId);
            for (Debt debt : debtList) {
                debt.setGroupAccount(null);
                debtService.update(debt);
            }
            /*合计押金*/
            totalDeposit = totalDeposit + checkIn.getNotNullDeposit();
            /*合计消费*/
            totalConsume = totalConsume + checkIn.getNotNullConsume();
            /*合计支付（中间）*/
            totalPay = totalPay + checkIn.getNotNullPay();
        }
        CheckInGroup checkInGroup= checkInGroupService.getByGroupAccount(groupAccount);
        if (checkInGroup.getTotalRoom()==roomList.size()){
            checkInGroupService.deleteByGroupAccount(groupAccount);
        }else {
            checkInGroup.setDeposit(checkInGroup.getNotNullGroupDeposit()-totalDeposit);
            checkInGroup.setConsume(checkInGroup.getNotNullGroupConsume()-totalConsume);
            checkInGroup.setPay(checkInGroup.getNotNullGroupPay()-totalPay);
            checkInGroup.setTotalRoom(checkInGroup.getTotalRoom()-roomList.size());
            checkInGroupService.update(checkInGroup);
        }
    }
}
