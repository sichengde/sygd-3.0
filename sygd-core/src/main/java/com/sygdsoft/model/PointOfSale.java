package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-16.
 * 营业部门
 */
public class PointOfSale extends BaseEntity{
    private String module;
    private String firstPointOfSale;//一级营业部门
    private String secondPointOfSale;//二级营业部门
    private String house;//仓库
    private String virtualTarget;//虚拟营业部门目标
    private Boolean ifVirtual;//是否虚拟

    public PointOfSale() {
    }


    public Boolean getNotNullIfVirtual() {
        if(ifVirtual==null){
            return false;
        }else {
            return ifVirtual;
        }
    }
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFirstPointOfSale() {
        return firstPointOfSale;
    }

    public void setFirstPointOfSale(String firstPointOfSale) {
        this.firstPointOfSale = firstPointOfSale;
    }

    public String getSecondPointOfSale() {
        return secondPointOfSale;
    }

    public void setSecondPointOfSale(String secondPointOfSale) {
        this.secondPointOfSale = secondPointOfSale;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getVirtualTarget() {
        return virtualTarget;
    }

    public void setVirtualTarget(String virtualTarget) {
        this.virtualTarget = virtualTarget;
    }

    public Boolean getIfVirtual() {
        return ifVirtual;
    }

    public void setIfVirtual(Boolean ifVirtual) {
        this.ifVirtual = ifVirtual;
    }
}
