package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import javax.persistence.Transient;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class DeskOut {
    private String pointOfSale;
    private String desk;
    private String deskGuestSource;
    private Double discount;
    private Double finalPrice;
    private List<CurrencyPost> currencyPostList;
    private DeskBook deskBook;
    private Boolean groupDetail;
    @Transient
    private List<DeskDetail> deskDetailList;

    public DeskOut() {
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public List<CurrencyPost> getCurrencyPostList() {
        return currencyPostList;
    }

    public void setCurrencyPostList(List<CurrencyPost> currencyPostList) {
        this.currencyPostList = currencyPostList;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public DeskBook getDeskBook() {
        return deskBook;
    }

    public void setDeskBook(DeskBook deskBook) {
        this.deskBook = deskBook;
    }

    public Boolean getGroupDetail() {
        return groupDetail;
    }

    public void setGroupDetail(Boolean groupDetail) {
        this.groupDetail = groupDetail;
    }

    public List<DeskDetail> getDeskDetailList() {
        return deskDetailList;
    }

    public void setDeskDetailList(List<DeskDetail> deskDetailList) {
        this.deskDetailList = deskDetailList;
    }

    public String getDeskGuestSource() {
        return deskGuestSource;
    }

    public void setDeskGuestSource(String deskGuestSource) {
        this.deskGuestSource = deskGuestSource;
    }
}
