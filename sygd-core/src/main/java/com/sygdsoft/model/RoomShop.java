package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-05-19.
 */
public class RoomShop extends BaseEntity{
    private String item;//品种名称
    private String category;//品种类别
    private Double price;//品种单价
    private String pointOfSale;//营业部门
    private String unit;//单位
    private Boolean cargo;//是否参与库存
    private String pointOfSaleShop;//商品部门

    public RoomShop() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Boolean getCargo() {
        return cargo;
    }

    public void setCargo(Boolean cargo) {
        this.cargo = cargo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPointOfSaleShop() {
        return pointOfSaleShop;
    }

    public void setPointOfSaleShop(String pointOfSaleShop) {
        this.pointOfSaleShop = pointOfSaleShop;
    }
}
