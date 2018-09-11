package com.sygdsoft.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
@RestController
public class DeskDetailController {
    private static final Logger logger = LoggerFactory.getLogger(DeskDetailController.class);
    @Autowired
    DeskDetailService deskDetailService;
    @Autowired
    DeskInService deskInService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    SaleCountService saleCountService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    FoodSetService foodSetService;
    @Autowired
    CookRoomService cookRoomService;
    @Autowired
    MenuService menuService;


    @RequestMapping(value = "deskDetailGet")
    public List<DeskDetail> deskDetailGet(@RequestBody Query query) throws Exception {
        /*判断菜品是否聚合*/
        String[] var1=query.getOtherParam();
        if(var1!=null){
            return deskDetailService.getGroupByQuery(query);
        }else {
            return deskDetailService.get(query);
        }
    }

    @RequestMapping("deskDetailGetBySetName")
    public List<DeskDetail> deskDetailGetBySetName(@RequestBody DeskDetail deskDetail){
        return foodSetService.getDeskDetailBySetName(deskDetail);
    }

    /**
     * 设置已做未做（电子划菜）
     * @param deskDetail
     * @throws Exception
     */
    @RequestMapping(value = "deskDetailCooked")
    @Transactional(rollbackFor = Exception.class)
    public void deskDetailUpdate(@RequestBody DeskDetail deskDetail)throws Exception{
        DeskDetail deskDetail1=deskDetailService.getById(deskDetail.getId());
        deskDetail1.setCooked(deskDetail.getCooked());
        deskDetailService.update(deskDetail1);
    }

    /**
     * 更新菜品（一步增删改）
     * 传进来的是该桌的所有菜品
     * 等叫和叫起在厨打印单上判断
     * 返回值
     * 0成功
     * 1厨房打印机ip不通
     * 2传菜打印机ip不通
     * 3都不通
     */
    @RequestMapping(value = "deskDetailAction")
    @Transactional(rollbackFor = Exception.class)
    public synchronized void deskAction(@RequestBody List<DeskDetail> deskDetailList) throws Exception {
        //logger.info(JSON.toJSONString(deskDetailList));
        timeService.setNow();
        List<DeskDetail> deskDetailUpdate = new ArrayList<>();//需要更新的
        List<DeskDetail> deskDetailInsert = new ArrayList<>();//需要查入的
        List<DeskDetail> deskDetailPrint = new ArrayList<>();//需要打印的
        Double consume = 0.0;
        for (DeskDetail deskDetail : deskDetailList) {
            if (deskDetail.getNum() == 0) {//数量为0，没有意义
                continue;
            }
            if (deskDetail.isNeedInsert()) {//如果有插入标志则忽略更新标志
                deskDetail.setDoTime(timeService.getNow());
                deskDetailInsert.add(deskDetail);
                /*套餐明细改为在前端生成*/
                /*if (deskDetail.getNotNullFoodSet()) {//如果是套餐则需要打印所有明细菜品
                    List<DeskDetail> foodSubSetList = foodSetService.getDeskDetailBySetName(deskDetail);
                    deskDetailPrint.addAll(foodSubSetList);
                    deskDetailInsert.addAll(foodSubSetList);
                } else {*/
                    deskDetailPrint.add(deskDetail);
                //}
            } else if (deskDetail.isNeedUpdate()) {
                deskDetail.setDoTime(timeService.getNow());
                deskDetailUpdate.add(deskDetail);
                if (deskDetail.getNotNullCallUp()||deskDetail.getNotNullWaitCall()) {//叫起的菜
                    deskDetailPrint.add(deskDetail);
                }
                if(deskDetail.getChangeNum()!=null){
                    DeskDetail old=deskDetailService.getById(deskDetail.getId());
                    deskDetail.setChangeAdd(",数量:"+old.getNum()+"=>"+deskDetail.getNum());
                    deskDetailPrint.add(deskDetail);
                }
                if(deskDetail.getChangeNum()!=null){
                    DeskDetail old=deskDetailService.getById(deskDetail.getId());
                    deskDetail.setChangeAdd(",单位:"+old.getUnit()+"=>"+deskDetail.getUnit());
                    deskDetailPrint.add(deskDetail);
                }
                if(deskDetail.getChangeName()!=null){
                    DeskDetail old=deskDetailService.getById(deskDetail.getId());
                    deskDetail.setChangeAdd(",菜名:"+old.getFoodName()+"=>"+deskDetail.getFoodName());
                    deskDetailPrint.add(deskDetail);
                }
            }
            consume += deskDetail.getNotNullPrice() * deskDetail.getNum();
        }
        String logAction;
        DeskDetail deskDetail = deskDetailList.get(0);//用于提取桌台信息
        DeskIn deskIn = deskInService.getByDesk(deskDetail.getDesk(), deskDetail.getPointOfSale());
        if (deskIn == null) {//说明是新开的台，生成deskIn信息
            deskIn = new DeskIn();
            deskIn.setDoTime(timeService.getNow());
            deskIn.setDesk(deskDetail.getDesk());
            if(deskDetail.getPeople()!=null) {
                deskIn.setNum(deskDetail.getPeople());
            }
            deskIn.setSubDeskNum(deskDetail.getSubDeskNum());
            deskIn.setCompany(deskDetail.getCompany());
            deskIn.setRemark(deskDetail.getGlobalRemark());
            deskIn.setConsume(consume);
            deskIn.setPointOfSale(deskDetail.getPointOfSale());
            deskIn.setUserId(userService.getCurrentUser());
            logAction = "开台点菜：" + deskDetail.getDesk();
            deskInService.add(deskIn);
        } else {
            deskIn.setConsume(consume);
            deskIn.setCompany(deskDetail.getCompany());
            if(deskDetail.getPeople()!=null) {
                deskIn.setNum(deskDetail.getPeople());
            }
            deskIn.setRemark(deskDetail.getGlobalRemark());
            deskIn.setSubDeskNum(deskDetail.getSubDeskNum());
            deskInService.update(deskIn);
            logAction = "修改菜品：" + deskDetail.getDesk();
        }
        deskDetailService.add(deskDetailInsert);
        deskInService.updateConsume(deskIn.getDesk(),deskIn.getPointOfSale());
        /*统计沽清*/
        menuService.setRemain(deskDetailInsert);
        deskDetailService.update(deskDetailUpdate);
        userLogService.addUserLog(logAction, userLogService.desk, userLogService.deskDetailIn,deskIn.getDesk());
        /*进行厨房打印*/
        Map<String, List<DeskDetail>> deskDetailMap = new ManagedMap<>();//打印机名-连打不切纸数据
        Map<String, Integer> printNumMap = new ManagedMap<>();//打印机名-连打不切纸数据
        for (DeskDetail detail : deskDetailPrint) {
            if (detail.getCookRoom() != null) {//有厨房才进行厨打
                List<CookRoom> cookRoomList = cookRoomService.getByCookName(detail.getCookRoom().split(","));
                for (CookRoom cookRoom : cookRoomList) {
                    if (cookRoom.getNotNullCut()) {//有cut就不是传菜
                        for (int i = 0; i < cookRoom.getNotNullNum(); i++) {
                            reportService.printCook(cookRoom.getPrinter(), detail,deskIn);
                        }
                    } else {
                        if (deskDetailMap.containsKey(cookRoom.getPrinter())) {//如果有这个厨房
                            deskDetailMap.get(cookRoom.getPrinter()).add(detail);
                        } else {
                            deskDetailMap.put(cookRoom.getPrinter(), new ArrayList<DeskDetail>());
                            printNumMap.put(cookRoom.getPrinter(), cookRoom.getNotNullNum());
                            deskDetailMap.get(cookRoom.getPrinter()).add(detail);
                        }
                    }
                }
            }
        }
        for (String s : deskDetailMap.keySet()) {
            for (int i = 0; i < printNumMap.get(s); i++) {
                reportService.printPassFood(s, deskDetailMap.get(s),deskIn);
            }
        }
    }

    /**
     * 菜品换台
     */
    @RequestMapping("menuChangeDesk")
    @Transactional(rollbackFor = Exception.class)
    public void menuChangeDesk(@RequestBody DeskDetail deskDetail) throws Exception {
        String targetDesk=deskDetail.getGlobalRemark();
        /*删除菜品*/
        deskDetailService.delete(deskDetail);
        /*更新deskIn*/
        deskInService.updateConsume(deskDetail.getDesk(),deskDetail.getPointOfSale());
        /*新桌点菜*/
        deskDetail.setNeedInsert(true);
        deskDetail.setRemark(deskDetail.getDesk()+"转入");
        deskDetail.setDesk(targetDesk);
        List<DeskDetail> deskDetailList=new ArrayList<>();
        deskDetailList.add(deskDetail);
        this.deskAction(deskDetailList);
    }
}
