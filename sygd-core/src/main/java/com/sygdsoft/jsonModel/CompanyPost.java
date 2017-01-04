package com.sygdsoft.jsonModel;

/**
 * Created by 舒展 on 2016-09-28.
 */
public class CompanyPost {
    private String companyName ;
    private Double total ;
    private CurrencyPost currencyPost ;//币种信息

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

    public CurrencyPost getCurrencyPost() {
        return currencyPost;
    }

    public void setCurrencyPost(CurrencyPost currencyPost) {
        this.currencyPost = currencyPost;
    }
}
