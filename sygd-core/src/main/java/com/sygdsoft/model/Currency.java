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
    private Boolean payTotal;//参与结算款统计
    private Boolean payBackRmb;//退预付转人民币

    public Currency() {
    }

    public boolean getNotNullScore(){
        if(score==null){
            return false;
        }else {
            return score;
        }
    }

    public boolean getNotNullPayTotal() {
        if (payTotal==null){
            return false;
        }else {
            return payTotal;
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

    public Boolean getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Boolean payTotal) {
        this.payTotal = payTotal;
    }

    public Boolean getPayBackRmb() {
        return payBackRmb;
    }

    public void setPayBackRmb(Boolean payBackRmb) {
        this.payBackRmb = payBackRmb;
    }
}
