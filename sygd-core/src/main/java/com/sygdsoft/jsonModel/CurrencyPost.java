package com.sygdsoft.jsonModel;

import com.sygdsoft.model.DebtPay;

/**
 * Created by Administrator on 2016/8/14 0014.
 */
public class CurrencyPost {
    private String currency;
    private String currencyAdd;
    private Double money;
    private Double deposit;
    private Boolean changeDeposit;//是否转移押金

    public CurrencyPost() {
    }

    /**
     * 通过结账分单数组生成，用于补打结账单
     * @param debtPay
     */
    public CurrencyPost(DebtPay debtPay){
        this.currency=debtPay.getCurrency();
        this.currencyAdd=debtPay.getCurrencyAdd();
        this.money=debtPay.getDebtMoney();
    }

    public Boolean getNotNullChangeDeposit(){
        if(changeDeposit==null){
            return false;
        }else {
            return changeDeposit;
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyAdd() {
        return currencyAdd;
    }

    public void setCurrencyAdd(String currencyAdd) {
        this.currencyAdd = currencyAdd;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Boolean getChangeDeposit() {
        return changeDeposit;
    }

    public void setChangeDeposit(Boolean changeDeposit) {
        this.changeDeposit = changeDeposit;
    }
}
