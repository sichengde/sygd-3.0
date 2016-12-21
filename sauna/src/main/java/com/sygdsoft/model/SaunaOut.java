package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-05.
 */
public class SaunaOut {
    private List<String> ringList;
    private List<CurrencyPost> currencyPayList;

    public SaunaOut() {
    }

    public List<String> getRingList() {
        return ringList;
    }

    public void setRingList(List<String> ringList) {
        this.ringList = ringList;
    }

    public List<CurrencyPost> getCurrencyPayList() {
        return currencyPayList;
    }

    public void setCurrencyPayList(List<CurrencyPost> currencyPayList) {
        this.currencyPayList = currencyPayList;
    }
}
