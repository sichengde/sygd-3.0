package com.sygdsoft.model.room;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-27.
 */
public class ExchangeUserSmallJQReturn {
    private List<ExchangeUserSmallJQRow> exchangeUserSmallJQRowList;
    private String remark;

    public ExchangeUserSmallJQReturn() {
    }

    public List<ExchangeUserSmallJQRow> getExchangeUserSmallJQRowList() {
        return exchangeUserSmallJQRowList;
    }

    public void setExchangeUserSmallJQRowList(List<ExchangeUserSmallJQRow> exchangeUserSmallJQRowList) {
        this.exchangeUserSmallJQRowList = exchangeUserSmallJQRowList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
