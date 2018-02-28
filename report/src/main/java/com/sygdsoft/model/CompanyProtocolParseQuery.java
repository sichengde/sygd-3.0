package com.sygdsoft.model;

import java.util.Date;
import java.util.List;

public class CompanyProtocolParseQuery {
    private Date beginTime;
    private Date endTime;
    private String type;
    private List<String> chooseCompanies;
    private List<String> chooseProtocols;

    public CompanyProtocolParseQuery() {
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getChooseCompanies() {
        return chooseCompanies;
    }

    public void setChooseCompanies(List<String> chooseCompanies) {
        this.chooseCompanies = chooseCompanies;
    }

    public List<String> getChooseProtocols() {
        return chooseProtocols;
    }

    public void setChooseProtocols(List<String> chooseProtocols) {
        this.chooseProtocols = chooseProtocols;
    }
}
