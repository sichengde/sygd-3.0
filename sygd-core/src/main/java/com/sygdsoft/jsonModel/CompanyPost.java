package com.sygdsoft.jsonModel;

/**
 * Created by 舒展 on 2016-09-28.
 */
public class CompanyPost {
    private String companyName ;
    private Double total ;//实际抵扣的挂账款
    private Double deposit ;//预付款
    private Double pay ;//实际支付的结算款
    private String currency;

    public CompanyPost() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
