package com.sygdsoft.jsonModel;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-24.
 */
public class Query {
    private String condition;//查询条件
    private Integer num;//查询数量
    private Integer page;//查询页数
    private String[] orderByList;//排序（正序）
    private String[] orderByListDesc;//排序（倒序）
    private String[] otherParam;//其他灵活参数

    public Query() {
    }

    public Query(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String[] getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(String[] orderByList) {
        this.orderByList = orderByList;
    }

    public String[] getOrderByListDesc() {
        return orderByListDesc;
    }

    public void setOrderByListDesc(String[] orderByListDesc) {
        this.orderByListDesc = orderByListDesc;
    }

    public String[] getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String[] otherParam) {
        this.otherParam = otherParam;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
