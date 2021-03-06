package com.sygdsoft.model;

import com.sygdsoft.util.NullJudgement;

import javax.persistence.Transient;

/**
 * Created by 舒展 on 2016-04-13.
 * 单位签单人
 */
public class CompanyLord extends BaseEntity{
    private String company;//单位编码
    private String name;//签单人姓名
    private Double debt;//签单人欠款
    @Transient
    private Double roomDebt;
    @Transient
    private Double eatDebt;

    public CompanyLord() {
    }

    public Double getNotNullLordConsume() {
        return NullJudgement.nullToZero(debt);
    }


    /**
     * GAS
     */

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getRoomDebt() {
        return roomDebt;
    }

    public void setRoomDebt(Double roomDebt) {
        this.roomDebt = roomDebt;
    }

    public Double getEatDebt() {
        return eatDebt;
    }

    public void setEatDebt(Double eatDebt) {
        this.eatDebt = eatDebt;
    }
}
