package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 舒展 on 2016-09-13.
 */
public class DeskDetail extends BaseEntity {
    private String foodName;
    private Double price;
    private Integer num;
    private String desk;
    private String userId;
    private String pointOfSale;
    private Date doTime;
    private String foodSign;//菜品标志，用于区分退菜，fooName有可能被加上/退菜导致联表查询时找不到类别定义
    private String category;//菜品类别
    private Boolean waitCall;//等叫
    private Boolean callUp;//叫起
    private String remark;//备注
    private String unit;//单位
    private String cookRoom;//厨房
    private Boolean ifDiscount;//是否参与折扣
    private Boolean foodSet;//是否是套餐
    private Boolean cargo;//是否是货物
    private Boolean storageDone;//自动出库是否统计完成
    @Transient
    private boolean needUpdate;//更新的菜品
    @Transient
    private boolean needInsert;//新加的菜品
    @Transient
    private Integer people;//用餐人数

    public DeskDetail() {
    }

    public DeskDetail(DeskDetailHistory deskDetailHistory) {
        this.foodName=deskDetailHistory.getFoodName();
        this.foodSign=deskDetailHistory.getFoodSign();
        this.price=deskDetailHistory.getPrice();
        this.num=deskDetailHistory.getNum();
        this.desk=deskDetailHistory.getDesk();
        this.userId=deskDetailHistory.getUserId();
        this.pointOfSale=deskDetailHistory.getPointOfSale();
        this.doTime=deskDetailHistory.getDoTime();
        this.ifDiscount=deskDetailHistory.getIfDiscount();
        this.remark=deskDetailHistory.getRemark();
        this.cookRoom=deskDetailHistory.getCookRoom();
        this.foodSet=deskDetailHistory.getFoodSet();
        this.cargo = deskDetailHistory.getCargo();
        this.storageDone = deskDetailHistory.getStorageDone();
    }

    public DeskDetail(DeskDetail deskDetail) {
        this.foodName = deskDetail.getFoodName();
        this.price = deskDetail.getPrice();
        this.num = deskDetail.getNum();
        this.desk = deskDetail.getDesk();
        this.userId = deskDetail.getUserId();
        this.pointOfSale = deskDetail.getPointOfSale();
        this.doTime = deskDetail.getDoTime();
        this.foodSign = deskDetail.getFoodSign();
        this.category = deskDetail.getCategory();
        this.waitCall = deskDetail.getWaitCall();
        this.callUp = deskDetail.getCallUp();
        this.remark = deskDetail.getRemark();
        this.unit = deskDetail.getUnit();
        this.ifDiscount = deskDetail.getIfDiscount();
        this.foodSet = deskDetail.getFoodSet();
        this.cookRoom = deskDetail.getCookRoom();
        this.cargo = deskDetail.getCargo();
        this.storageDone = deskDetail.getStorageDone();
        this.needUpdate = deskDetail.isNeedUpdate();
        this.needInsert = deskDetail.isNeedInsert();
        this.people = deskDetail.getPeople();
    }

    public Boolean getNotNullWaitCall() {
        if (waitCall == null) {
            return false;
        } else {
            return waitCall;
        }
    }

    public Boolean getNotNullFoodSet() {
        if (foodSet == null) {
            return false;
        } else {
            return foodSet;
        }
    }
    public Double getNotNullPrice() {
        if (price == null) {
            return 0.0;
        } else {
            return price;
        }
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getFoodSign() {
        return foodSign;
    }

    public void setFoodSign(String foodSign) {
        this.foodSign = foodSign;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public boolean isNeedInsert() {
        return needInsert;
    }

    public void setNeedInsert(boolean needInsert) {
        this.needInsert = needInsert;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getWaitCall() {
        return waitCall;
    }

    public void setWaitCall(Boolean waitCall) {
        this.waitCall = waitCall;
    }

    public Boolean getCallUp() {
        return callUp;
    }

    public Boolean getNotNullCallUp() {
        if (callUp == null) {
            return false;
        } else {
            return callUp;
        }
    }


    public void setCallUp(Boolean callUp) {
        this.callUp = callUp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getIfDiscount() {
        return ifDiscount;
    }

    public void setIfDiscount(Boolean ifDiscount) {
        this.ifDiscount = ifDiscount;
    }

    public Boolean getFoodSet() {
        return foodSet;
    }

    public void setFoodSet(Boolean foodSet) {
        this.foodSet = foodSet;
    }

    public String getCookRoom() {
        return cookRoom;
    }

    public void setCookRoom(String cookRoom) {
        this.cookRoom = cookRoom;
    }

    public Boolean getCargo() {
        return cargo;
    }

    public void setCargo(Boolean cargo) {
        this.cargo = cargo;
    }

    public Boolean getStorageDone() {
        return storageDone;
    }

    public void setStorageDone(Boolean storageDone) {
        this.storageDone = storageDone;
    }
}
