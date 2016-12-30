package com.sygdsoft.json;

import com.sygdsoft.jsonModel.CurrencyPost;

/**
 * Created by 舒展 on 2016-12-30.
 */
public class VipRecharge {
    private String vipNumber;
    private Double money;
    private Double deserve;
    private CurrencyPost currencyPost;

    public VipRecharge() {
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getDeserve() {
        return deserve;
    }

    public void setDeserve(Double deserve) {
        this.deserve = deserve;
    }

    public CurrencyPost getCurrencyPost() {
        return currencyPost;
    }

    public void setCurrencyPost(CurrencyPost currencyPost) {
        this.currencyPost = currencyPost;
    }
}
