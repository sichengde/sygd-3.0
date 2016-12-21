package com.sygdsoft.model;

import javax.swing.text.DefaultEditorKit;
import java.util.Date;

/**
 * Created by 舒展 on 2016-09-13.
 */
public class DeskInHistory extends BaseEntity {
    private String ckSerial;
    private String desk;
    private Date doTime;
    private Date doneTime;
    private Double totalPrice;
    private Double finalPrice;//结算价格，为以后计算折扣预留
    private Double discount;//优惠价格，为以后计算折扣预留
    private String userId;
    private String pointOfSale;
    private Integer num;//就餐人数
    private Boolean disabled;//该账单作废，自助餐是被冲减，正常桌台是被叫回

    public DeskInHistory() {
    }

    public DeskInHistory(DeskIn deskIn) {
        this.desk=deskIn.getDesk();
        this.doTime=deskIn.getDoTime();
        this.num=deskIn.getNum();
        this.totalPrice=deskIn.getConsume();
        this.userId=deskIn.getUserId();
        this.pointOfSale=deskIn.getPointOfSale();
    }

    public Integer getNotNullNum(){
        if(num==null){
            return 0;
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

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
