package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.List;

/**
 * Created by 舒展 on 2016-07-14.
 * 餐饮桌台定义
 */
public class Desk extends BaseEntity{
    private String name;
    private String pointOfSale;
    private String seat;
    private String category;
    private Integer deskOrder;//排序
    @Transient
    private DeskIn deskIn;
    @Transient
    private List<DeskBook> deskBookList;

    public Desk() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeskIn getDeskIn() {
        return deskIn;
    }

    public void setDeskIn(DeskIn deskIn) {
        this.deskIn = deskIn;
    }

    public List<DeskBook> getDeskBookList() {
        return deskBookList;
    }

    public void setDeskBookList(List<DeskBook> deskBookList) {
        this.deskBookList = deskBookList;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDeskOrder() {
        return deskOrder;
    }

    public void setDeskOrder(Integer deskOrder) {
        this.deskOrder = deskOrder;
    }
}
