package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaDiscount extends BaseEntity{
    private String discountName;
    private String discountValue;

    public SaunaDiscount() {
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }
}
