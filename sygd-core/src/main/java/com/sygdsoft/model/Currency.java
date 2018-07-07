package com.sygdsoft.model;

import com.sygdsoft.util.NullJudgement;

/**
 * Created by 舒展 on 2016-04-13.
 * 币种
 */
public class Currency extends BaseEntity{
    private String currency;//币种代码
    private Boolean score;//参与积分
    private Boolean checkOut;//结账币种
    private Boolean checkIn;//押金币种
    private Boolean payTotalRoom;//参与结算款统计
    private Boolean payTotalDesk;//参与结算款统计
    private Boolean payBackRmb;//退预付转人民币
    private Boolean showInReport;//账单上是否显示(暂时只有餐饮)
    private Boolean checkRemain;//统计结余时是否参与

    public Currency() {
    }

    public boolean getNotNullScore(){
        if(score==null){
            return false;
        }else {
            return score;
        }
    }

    public boolean getNotNullShowInReport(){
        if(showInReport==null){
            return false;
        }else {
            return showInReport;
        }
    }

    public boolean getNotNullPayTotalRoom() {
        if (payTotalRoom==null){
            return false;
        }else {
            return payTotalRoom;
        }
    }

    public boolean getNotNullPayTotalDesk() {
        if (payTotalDesk==null){
            return false;
        }else {
            return payTotalDesk;
        }
    }

    public boolean getNotNullCheckRemain() {
        if (checkRemain==null){
            return false;
        }else {
            return checkRemain;
        }
    }

    public Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getScore() {
        return score;
    }

    public void setScore(Boolean score) {
        this.score = score;
    }

    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Boolean getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Boolean checkOut) {
        this.checkOut = checkOut;
    }

    public Boolean getPayTotalRoom() {
        return payTotalRoom;
    }

    public void setPayTotalRoom(Boolean payTotalRoom) {
        this.payTotalRoom = payTotalRoom;
    }

    public Boolean getPayTotalDesk() {
        return payTotalDesk;
    }

    public void setPayTotalDesk(Boolean payTotalDesk) {
        this.payTotalDesk = payTotalDesk;
    }

    public Boolean getPayBackRmb() {
        return payBackRmb;
    }

    public void setPayBackRmb(Boolean payBackRmb) {
        this.payBackRmb = payBackRmb;
    }

    public Boolean getShowInReport() {
        return showInReport;
    }

    public void setShowInReport(Boolean showInReport) {
        this.showInReport = showInReport;
    }

    public Boolean getCheckRemain() {
        return checkRemain;
    }

    public void setCheckRemain(Boolean checkRemain) {
        this.checkRemain = checkRemain;
    }
}
