package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-09-13.
 */
public class DeskDetailHistory extends BaseEntity {
    private String ckSerial;
    private String foodName;
    private Double price;
    private Double num;
    private String desk;
    private String userId;
    private String pointOfSale;
    private Date doTime;
    private Date doneTime;
    private String foodSign;
    private String unit;//单位
    private String remark;//备注
    private String cookRoom;//厨房
    private String category;//类别
    private Double total;
    private Double afterDiscount;
    private Boolean ifDiscount;
    private Boolean foodSet;//是否是套餐
    private Boolean cargo;//是否是货物
    private Boolean storageDone;//自动出库是否统计完成
    private Boolean disabled;//被取消（详细解释在deskInHistory）
    public DeskDetailHistory() {
    }

    public DeskDetailHistory(DeskDetail deskDetail) {
        this.foodName = deskDetail.getFoodName();
        this.foodSign = deskDetail.getFoodSign();
        this.price = deskDetail.getPrice();
        this.num = deskDetail.getNum();
        this.desk = deskDetail.getDesk();
        this.userId = deskDetail.getUserId();
        this.pointOfSale = deskDetail.getPointOfSale();
        this.doTime = deskDetail.getDoTime();
        this.ifDiscount = deskDetail.getIfDiscount();
        this.remark = deskDetail.getRemark();
        this.category=deskDetail.getCategory();
        this.unit=deskDetail.getUnit();
        this.cookRoom = deskDetail.getCookRoom();
        this.foodSet=deskDetail.getFoodSet();
        this.cargo = deskDetail.getCargo();
        this.storageDone = deskDetail.getStorageDone();
        if (this.price == null) {//套餐中的菜，没有价格
            this.total = 0.0;
        } else {
            this.total = this.num * this.price;
        }
    }

    public Double getNotNullPrice(){
        if(price==null){
            return 0.0;
        }else {
            return price;
        }
    }

    public Double getNotNullNum(){
        if(num==null){
            return 0.0;
        }else {
            return num;
        }
    }

    public String getCkSerial() {
        return ckSerial;
    }

    public void setCkSerial(String ckSerial) {
        this.ckSerial = ckSerial;
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

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
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

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getFoodSign() {
        return foodSign;
    }

    public void setFoodSign(String foodSign) {
        this.foodSign = foodSign;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(Double afterDiscount) {
        this.afterDiscount = afterDiscount;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
