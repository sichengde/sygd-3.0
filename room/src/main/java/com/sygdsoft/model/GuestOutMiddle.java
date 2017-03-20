package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import java.util.List;

/**
 * Created by 舒展 on 2017-01-16.
 * 中间结算实体类
 */
public class GuestOutMiddle {
    private String groupAccount;
    private List<String> roomIdList;
    private List<CurrencyPost> currencyPayList;
    private String remark;
    private Double payMoney;
    private String again;//补打
    private String paySerial;//结账序列号，补打时有用
    private List<Debt> debtList;//账务明细，根据账务中间结算时用
    private Boolean real;//真实结算，针对于辽阳宾馆这种喜欢结账之后再确认的

    public GuestOutMiddle() {
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

    public List<String> getRoomIdList() {
        return roomIdList;
    }

    public void setRoomIdList(List<String> roomIdList) {
        this.roomIdList = roomIdList;
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

    public List<Debt> getDebtList() {
        return debtList;
    }

    public void setDebtList(List<Debt> debtList) {
        this.debtList = debtList;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Boolean getReal() {
        return real;
    }

    public void setReal(Boolean real) {
        this.real = real;
    }
}
