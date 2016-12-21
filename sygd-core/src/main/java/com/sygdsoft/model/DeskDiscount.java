package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-10-27.
 */
public class DeskDiscount extends BaseEntity {
    private String discountName;
    private String discountValue;

    public DeskDiscount() {
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
