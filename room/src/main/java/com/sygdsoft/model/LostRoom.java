package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-29.
 */
public class LostRoom {
    private List<CurrencyPost> currencyPostList;
    private DebtPay debtPay;
    private Boolean changeDetail;

    public LostRoom() {
    }

    public boolean getNotNullChangeDetail(){
        if(changeDetail==null){
            return false;
        }else {
            return changeDetail;
        }
    }

    public List<CurrencyPost> getCurrencyPostList() {
        return currencyPostList;
    }

    public void setCurrencyPostList(List<CurrencyPost> currencyPostList) {
        this.currencyPostList = currencyPostList;
    }

    public DebtPay getDebtPay() {
        return debtPay;
    }

    public void setDebtPay(DebtPay debtPay) {
        this.debtPay = debtPay;
    }

    public Boolean getChangeDetail() {
        return changeDetail;
    }

    public void setChangeDetail(Boolean changeDetail) {
        this.changeDetail = changeDetail;
    }
}
