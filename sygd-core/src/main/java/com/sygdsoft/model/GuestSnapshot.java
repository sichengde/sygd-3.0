package com.sygdsoft.model;

import java.util.Date;

public class GuestSnapshot extends BaseEntity{
    private Date reportTime;
    private Integer come;
    private Integer exist;

    public GuestSnapshot() {
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getCome() {
        return come;
    }

    public void setCome(Integer come) {
        this.come = come;
    }

    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }
}
