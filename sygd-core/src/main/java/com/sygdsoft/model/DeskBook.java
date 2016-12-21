package com.sygdsoft.model;

import com.sygdsoft.util.NullJudgement;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 舒展 on 2016-09-13.
 */
public class DeskBook extends BaseEntity {
    private String desk;
    private Date reachTime;
    private Date remainTime;
    private String guestName;
    private String phone;
    private Double subscription;
    private String userId;
    private String pointOfSale;
    private String currency;
    private String deskBookSerial;
    private String remark;
    @Transient
    private List<BookMoney> bookMoneyList;

    public DeskBook() {
    }

    public Double getNotNullSubscription() {
        return NullJudgement.nullToZero(subscription);
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
    }

    public Date getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Date remainTime) {
        this.remainTime = remainTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getSubscription() {
        return subscription;
    }

    public void setSubscription(Double subscription) {
        this.subscription = subscription;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeskBookSerial() {
        return deskBookSerial;
    }

    public void setDeskBookSerial(String deskBookSerial) {
        this.deskBookSerial = deskBookSerial;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BookMoney> getBookMoneyList() {
        return bookMoneyList;
    }

    public void setBookMoneyList(List<BookMoney> bookMoneyList) {
        this.bookMoneyList = bookMoneyList;
    }
}
