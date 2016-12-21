package com.sygdsoft.jsonModel.report;

/**
 * Created by 舒展 on 2016-11-15.
 */
public class DeskCategoryOut {
    private String category;//类别
    private Double total;//金额
    private String percent;//占比

    public DeskCategoryOut() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
