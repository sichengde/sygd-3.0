package com.sygdsoft.model.room;

import com.sygdsoft.model.CountryGuestRow;

import java.util.List;

/**
 * Created by Administrator on 2017-04-12.
 */
public class CountryGuestReturn {
    private List<CountryGuestRow> countryGuestRowList;
    private String remark;

    public CountryGuestReturn() {
    }

    public List<CountryGuestRow> getCountryGuestRowList() {
        return countryGuestRowList;
    }

    public void setCountryGuestRowList(List<CountryGuestRow> countryGuestRowList) {
        this.countryGuestRowList = countryGuestRowList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
