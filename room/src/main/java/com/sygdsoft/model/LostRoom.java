package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-29.
 */
public class LostRoom {
    private List<CurrencyPost> currencyPostList;
    private DebtPay debtPay;

    public LostRoom() {
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
}
