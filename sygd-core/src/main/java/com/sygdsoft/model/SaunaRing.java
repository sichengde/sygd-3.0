package com.sygdsoft.model;

import javax.persistence.Transient;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaRing extends BaseEntity{
    private String number;//手牌号
    private String sex;//男女牌
    private Boolean repair;//维修中
    @Transient
    private SaunaIn saunaIn;
    @Transient
    private String state;

    public SaunaRing() {
    }
    public Boolean getNotNullRepair() {
        if(repair==null){
            return false;
        }else {
            return repair;
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public SaunaIn getSaunaIn() {
        return saunaIn;
    }

    public void setSaunaIn(SaunaIn saunaIn) {
        this.saunaIn = saunaIn;
    }

    public Boolean getRepair() {
        return repair;
    }

    public void setRepair(Boolean repair) {
        this.repair = repair;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
