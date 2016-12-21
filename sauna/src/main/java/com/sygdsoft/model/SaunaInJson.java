package com.sygdsoft.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public class SaunaInJson {
    private String inCategory;//账单类型
    private List<SaunaInRow> saunaInRowList;//手牌数组

    public SaunaInJson() {
    }

    public String getInCategory() {
        return inCategory;
    }

    public void setInCategory(String inCategory) {
        this.inCategory = inCategory;
    }

    public List<SaunaInRow> getSaunaInRowList() {
        return saunaInRowList;
    }

    public void setSaunaInRowList(List<SaunaInRow> saunaInRowList) {
        this.saunaInRowList = saunaInRowList;
    }
}
