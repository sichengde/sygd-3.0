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
    private String remark;
    private String guestSource;
    private Integer subDeskNum;//子餐桌数量，针对于婚宴大厅
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
        this.guestSource=deskInHistory.getGuestSource();
        this.subDeskNum=deskInHistory.getSubDeskNum();
    }

    public Double getNotNullConsume(){
        if(consume==null){
            return 0.0;
        }else {
            return consume;
        }
    }

    public Integer getNotNullNum(){
        if(num==null){
            return 0;
        }else {
            return num;
        }
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public Integer getSubDeskNum() {
        return subDeskNum;
    }

    public void setSubDeskNum(Integer subDeskNum) {
        this.subDeskNum = subDeskNum;
    }
}
