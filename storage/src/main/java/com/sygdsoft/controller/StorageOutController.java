package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-11-17.
 * 出库
 */
@RestController
public class StorageOutController {
    @Autowired
    StorageOutService storageOutService;
    @Autowired
    RoomShopDetailService roomShopDetailService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    CargoService cargoService;
    @Autowired
    SzMath szMath;
    @Autowired
    StorageInDetailService storageInDetailService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    StorageInService storageInService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;

    @RequestMapping(value = "storageOutDelete")
    @Transactional(rollbackFor = Exception.class)
    public void storageOutDelete(@RequestBody List<StorageOut> storageOutList) throws Exception {
        storageOutService.delete(storageOutList);
    }

    @RequestMapping(value = "storageOutUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void storageOutUpdate(@RequestBody List<StorageOut> storageOutList) throws Exception {
        if (storageOutList.size() > 1) {
            if (storageOutList.get(0).getId().equals(storageOutList.get(storageOutList.size() / 2).getId())) {
                storageOutService.update(storageOutList.subList(0, storageOutList.size() / 2));
                return;
            }
        }
        storageOutService.update(storageOutList);
    }

    @RequestMapping(value = "storageOutGet")
    public List<StorageOut> storageOutGet(@RequestBody Query query) throws Exception {
        return storageOutService.get(query);
    }

    /**
     * 出库
     */
    @RequestMapping(value = "storageOutAdd")
    @Transactional(rollbackFor = Exception.class)
    public Integer storageOutAdd(@RequestBody StorageOutJson storageOutJson) throws Exception {
        List<StorageOutDetail> storageOutDetailList = storageOutJson.getStorageOutDetailList();
        StorageOut storageOut = storageOutJson.getStorageOut();
        return storageOutService.storageOutAdd(storageOutDetailList, storageOut);
    }

    /**
     * 自动出库，统计需要出库的品种和数量
     */
    @RequestMapping(value = "storageAutoOut")
    @Transactional(rollbackFor = Exception.class)
    public List<Integer> storageAutoOut() throws Exception {
        timeService.setNow();
        List<Integer> reportList = new ArrayList<>();//返回的报表数组
        Boolean storageOutSerial = false;//正常出库序列号
        Boolean storageOutSerialDirect = false;//直拨出库序列号
        List<StorageOutDetail> storageOutDetailListNormal = new ArrayList<>();//正常出库
        List<StorageOutDetail> storageOutDetailListDirect = new ArrayList<>();//直拨出库
        /*先统计房吧，房吧默认属于接待营业部门所以只有一个仓库，所以不需要联表查询*/
        PointOfSale pointOfSale = pointOfSaleService.getByFirst("接待");
        String house = pointOfSale.getHouse();
        List<RoomShopDetail> roomShopDetailList = roomShopDetailService.getByStorageDone();
        for (RoomShopDetail roomShopDetail : roomShopDetailList) {
            /*有退货的时候可能总和是0*/
            if(roomShopDetail.getNum()==0){
                continue;
            }
            Cargo cargo = cargoService.getByName(roomShopDetail.getItem());
            if (cargo != null) {//说明库存中没有该品种，不管它，必须不为null才可继续进行
                Double remain = storageInDetailService.getSumNumByNameHouse(house, cargo.getName());//获得当前货物的余量
                StorageOutDetail storageOutDetail = new StorageOutDetail();
                storageOutDetail.setCargo(roomShopDetail.getItem());
                storageOutDetail.setHouse(house);
                storageOutDetail.setUnit(cargo.getUnit());
                storageOutDetail.setMyUsage("自动出库-房吧销售统计");
                storageOutDetail.setCategory(cargo.getCategory());
                if (remain >= roomShopDetail.getNum()) {//库存充足
                    if (!storageOutSerial) {
                        storageOutSerial = true;
                    }
                    storageOutDetail.setNum(roomShopDetail.getNum());
                    storageOutDetail.setOldPrice(Double.valueOf(szMath.formatTwoDecimal(storageInDetailService.storageParsePrice(house, cargo.getName(), roomShopDetail.getNum()))));
                    storageOutDetail.setOldTotal(Double.valueOf(szMath.formatTwoDecimal(storageOutDetail.getNum() * storageOutDetail.getOldPrice())));
                    storageOutDetail.setTotal(roomShopDetail.getTotalMoney());
                    storageOutDetailListNormal.add(storageOutDetail);
                } else {//库存不足
                    //有库存不足的情况，首先就要增加一个直拨序列号
                    if (!storageOutSerialDirect) {
                        storageOutSerialDirect = true;
                    }
                    double num1 = roomShopDetail.getNum();
                    double totalMoneyRemain = roomShopDetail.getTotalMoney();
                    if (remain > 0) {//剩余的库存正常出库
                        if(!storageOutSerial){
                            storageOutSerial = true;
                        }
                        num1 = num1 - remain;
                        storageOutDetail.setNum(remain);
                        storageOutDetail.setOldPrice(Double.valueOf(szMath.formatTwoDecimal(storageInDetailService.storageParsePrice(house, cargo.getName(), remain))));
                        storageOutDetail.setOldTotal(storageOutDetail.getNum() * storageOutDetail.getOldPrice());
                        storageOutDetail.setTotal(Double.valueOf(szMath.formatTwoDecimal(roomShopDetail.getTotalMoney() * remain, roomShopDetail.getNum())));
                        totalMoneyRemain = totalMoneyRemain - storageOutDetail.getTotal();
                        storageOutDetailListNormal.add(storageOutDetail);
                    }
                    /*超出的数量则直拨*/
                    storageOutDetail=new StorageOutDetail(storageOutDetail);
                    storageOutDetail.setNum(num1);
                    storageOutDetail.setOldPrice(0.0);//直拨没有价格
                    storageOutDetail.setOldTotal(num1 * storageOutDetail.getOldPrice());
                    storageOutDetail.setTotal(totalMoneyRemain);
                    storageOutDetailListDirect.add(storageOutDetail);
                }
            }
        }
        roomShopDetailService.setStorageDoneTrue();
        /*出库总结，如果有剩余数量不足的，则需要两种，即是出库和直拨*/
        if (storageOutSerial) {//有数据才出库，否则不出库
            StorageOut storageOut = new StorageOut();
            storageOut.setOutTime(timeService.getNow());
            storageOut.setApprover(userService.getCurrentUser());
            storageOut.setSaveMan(userService.getCurrentUser());
            storageOut.setDeptOut("接待");
            storageOut.setRemark("自动出库-房吧");
            storageOut.setUserId(userService.getCurrentUser());
            storageOut.setCategory("出库");
            reportList.add(storageOutService.storageOutAdd(storageOutDetailListNormal, storageOut));
            if (storageOutSerialDirect ) {//如果存在库存不充足的情况，则要再提交一次
                storageOut.setId(null);
                storageOut.setRemark("自动出库-房吧");
                storageOut.setCategory("直拨");
                reportList.add(storageOutService.storageOutAdd(storageOutDetailListDirect, storageOut));
            }
        }
        /*再统计餐饮*/
        storageOutDetailListNormal=new ArrayList<>();
        storageOutDetailListDirect = new ArrayList<>();//直拨出库
        storageOutSerial = false;
        storageOutSerialDirect = false;
        List<PointOfSale> pointOfSaleList= pointOfSaleService.getCKPointOfSaleList();
        for (PointOfSale ofSale : pointOfSaleList) {
            house=ofSale.getHouse();
            if(house==null){//该销售点没有定义仓库
                continue;
            }
            List<DeskDetailHistory> deskDetailHistoryList = deskDetailHistoryService.getByStorageDone(ofSale.getFirstPointOfSale());
            for (DeskDetailHistory deskDetailHistory : deskDetailHistoryList) {
                Cargo cargo = cargoService.getByName(deskDetailHistory.getFoodSign());
                if (cargo != null) {
                    Double remain = storageInDetailService.getSumNumByNameHouse(house, cargo.getName());//获得当前货物的余量
                    StorageOutDetail storageOutDetail = new StorageOutDetail();
                    storageOutDetail.setCargo(deskDetailHistory.getFoodSign());
                    storageOutDetail.setHouse(house);
                    storageOutDetail.setUnit(cargo.getUnit());
                    storageOutDetail.setMyUsage("自动出库-房吧销售统计");
                    storageOutDetail.setCategory(cargo.getCategory());
                    if (remain >= deskDetailHistory.getNum()) {//库存充足
                        if (!storageOutSerial) {
                            storageOutSerial = true;
                        }
                        storageOutDetail.setNum(deskDetailHistory.getNum());
                        storageOutDetail.setOldPrice(Double.valueOf(szMath.formatTwoDecimal(storageInDetailService.storageParsePrice(house, cargo.getName(), deskDetailHistory.getNum()))));
                        storageOutDetail.setOldTotal(Double.valueOf(szMath.formatTwoDecimal(storageOutDetail.getNum() * storageOutDetail.getOldPrice())));
                        storageOutDetail.setTotal(deskDetailHistory.getAfterDiscount());
                        storageOutDetailListNormal.add(storageOutDetail);
                    } else {//库存不足
                        if (!storageOutSerialDirect) {
                            storageOutSerialDirect = true;
                        }
                        double num1 = deskDetailHistory.getNum();
                        double totalMoneyRemain = deskDetailHistory.getAfterDiscount();
                        //有库存不足的情况，首先就要增加一个直拨序列号
                        if (remain > 0) {//剩余的库存正常出库
                            if (!storageOutSerial) {
                                storageOutSerial = true;
                            }
                            num1 = num1 - remain;
                            storageOutDetail.setNum(remain);
                            storageOutDetail.setOldPrice(Double.valueOf(szMath.formatTwoDecimal(storageInDetailService.storageParsePrice(house, cargo.getName(), remain))));
                            storageOutDetail.setOldTotal(storageOutDetail.getNum() * storageOutDetail.getOldPrice());
                            storageOutDetail.setTotal(Double.valueOf(szMath.formatTwoDecimal(deskDetailHistory.getAfterDiscount() * remain, deskDetailHistory.getNum())));
                            totalMoneyRemain = totalMoneyRemain - storageOutDetail.getTotal();
                            storageOutDetailListNormal.add(storageOutDetail);
                        }
                    /*超出的数量则直拨*/
                        storageOutDetail = new StorageOutDetail(storageOutDetail);
                        storageOutDetail.setNum(num1);
                        storageOutDetail.setOldPrice(0.0);//直拨没有价格
                        storageOutDetail.setOldTotal(num1 * storageOutDetail.getOldPrice());
                        storageOutDetail.setTotal(totalMoneyRemain);
                        storageOutDetailListDirect.add(storageOutDetail);
                    }
                }
            }
            /*出库总结，如果有剩余数量不足的，则需要两种，即是出库和直拨*/
            if (storageOutSerial) {//有数据才出库，否则不出库
                StorageOut storageOut = new StorageOut();
                storageOut.setOutTime(timeService.getNow());
                storageOut.setApprover(userService.getCurrentUser());
                storageOut.setSaveMan(userService.getCurrentUser());
                storageOut.setDeptOut(pointOfSale.getFirstPointOfSale());
                storageOut.setRemark("自动出库-餐饮");
                storageOut.setUserId(userService.getCurrentUser());
                storageOut.setCategory("出库");
                reportList.add(storageOutService.storageOutAdd(storageOutDetailListNormal, storageOut));
                if (storageOutSerialDirect ) {//如果存在库存不充足的情况，则要再提交一次
                    storageOut.setId(null);
                    storageOut.setRemark("自动出库-餐饮");
                    storageOut.setCategory("直拨");
                    reportList.add(storageOutService.storageOutAdd(storageOutDetailListDirect, storageOut));
                }
            }
        }
        deskDetailHistoryService.setStorageDoneTrue();
        /*返回打印序列号数组*/
        return reportList;
    }
}
