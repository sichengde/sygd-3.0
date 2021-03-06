package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-19.
 * 离店结算实体类
 */
public class GuestOut {
    private String checkOutSerialCategory;//结账序列号类别
    private String groupAccount;
    private List<String> roomIdList;
    private List<CurrencyPost> currencyPayList;
    private List<CheckOutPayBack> checkOutPayBackList;
    private String remark;
    private List<Debt> debtAddList;//额外的加收房租
    private String again;//补打
    private String paySerial;//结账序列号，补打时有用
    private String checkOutSerial;//离店序列号，补打时有用
    private Boolean real;//真实结算，针对于辽阳宾馆这种喜欢结账之后再确认的
    private Double fpMoney;//手写发票金额，仅用于打印结账单和补打
    private Boolean changeDetail;//是否转移押金

    public GuestOut() {
    }

    public Boolean getNotNullChangeDetail(){
        if(changeDetail==null){
            return false;
        }else {
            return changeDetail;
        }
    }

    public Boolean getNotNullReal(){
        if(real==null){
            return false;
        }else {
            return real;
        }
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public List<CurrencyPost> getCurrencyPayList() {
        return currencyPayList;
    }

    public void setCurrencyPayList(List<CurrencyPost> currencyPayList) {
        this.currencyPayList = currencyPayList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Debt> getDebtAddList() {
        return debtAddList;
    }

    public void setDebtAddList(List<Debt> debtAddList) {
        this.debtAddList = debtAddList;
    }

    public List<String> getRoomIdList() {
        return roomIdList;
    }

    public void setRoomIdList(List<String> roomIdList) {
        this.roomIdList = roomIdList;
    }

    public String getAgain() {
        return again;
    }

    public void setAgain(String again) {
        this.again = again;
    }

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public String getCheckOutSerial() {
        return checkOutSerial;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }

    public Boolean getReal() {
        return real;
    }

    public void setReal(Boolean real) {
        this.real = real;
    }

    public List<CheckOutPayBack> getCheckOutPayBackList() {
        return checkOutPayBackList;
    }

    public void setCheckOutPayBackList(List<CheckOutPayBack> checkOutPayBackList) {
        this.checkOutPayBackList = checkOutPayBackList;
    }

    public String getCheckOutSerialCategory() {
        return checkOutSerialCategory;
    }

    public void setCheckOutSerialCategory(String checkOutSerialCategory) {
        this.checkOutSerialCategory = checkOutSerialCategory;
    }

    public Double getFpMoney() {
        return fpMoney;
    }

    public void setFpMoney(Double fpMoney) {
        this.fpMoney = fpMoney;
    }

    public Boolean getChangeDetail() {
        return changeDetail;
    }

    public void setChangeDetail(Boolean changeDetail) {
        this.changeDetail = changeDetail;
    }
}
