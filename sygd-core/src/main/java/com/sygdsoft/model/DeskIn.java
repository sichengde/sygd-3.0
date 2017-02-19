package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
public class DeskIn extends BaseEntity{
    private String desk;
    private Date doTime;
    private Integer num;//人数
    private Double consume;//消费
    private String userId;
    private String pointOfSale;
    @Transient
    private List<DeskDetail> deskDetailList;

    public DeskIn() {
    }
    public DeskIn(DeskInHistory deskInHistory) {
        this.desk=deskInHistory.getDesk();
        this.doTime=deskInHistory.getDoTime();
        this.num=deskInHistory.getNum();
        this.consume=deskInHistory.getTotalPrice();
        this.userId=deskInHistory.getUserId();
        this.pointOfSale=deskInHistory.getPointOfSale();
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
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

    public List<DeskDetail> getDeskDetailList() {
        return deskDetailList;
    }

    public void setDeskDetailList(List<DeskDetail> deskDetailList) {
        this.deskDetailList = deskDetailList;
    }
}
