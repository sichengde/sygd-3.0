package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;

public class GuestSnapshot extends BaseEntity{
    private Date reportTime;
    private Integer come;
    private Integer exist;
    @Transient
    private Integer sumCome;
    @Transient
    private Integer sumExist;

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

    public Integer getSumCome() {
        return sumCome;
    }

    public void setSumCome(Integer sumCome) {
        this.sumCome = sumCome;
    }

    public Integer getSumExist() {
        return sumExist;
    }

    public void setSumExist(Integer sumExist) {
        this.sumExist = sumExist;
    }
}
