package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class Cargo extends BaseEntity{
    private String name;//名称
    private String category;//类别
    private String alias;//别名
    private String supplier;//供应商
    private String unit;//单位
    private String specification;//规格
    private String model;//型号
    private String groupUnit;//包装单位
    private Integer groupNum;//包装数量
    private Integer upperLimit;//库存上限
    private Integer lowerLimit ;//库存下限
    private String calculateStrategy;//出库计价
    private String remark;//备注

    public Cargo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGroupUnit() {
        return groupUnit;
    }

    public void setGroupUnit(String groupUnit) {
        this.groupUnit = groupUnit;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getCalculateStrategy() {
        return calculateStrategy;
    }

    public void setCalculateStrategy(String calculateStrategy) {
        this.calculateStrategy = calculateStrategy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
