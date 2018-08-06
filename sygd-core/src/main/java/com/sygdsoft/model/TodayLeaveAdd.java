package com.sygdsoft.model;

public class TodayLeaveAdd extends BaseEntity {
    private String reachTimeBegin;
    private String reachTimeEnd;
    private String leaveTimeBegin;
    private String leaveTimeEnd;
    private Double multiple;
    private Double staticMoney;

    public TodayLeaveAdd() {
    }

    public String getReachTimeBegin() {
        return reachTimeBegin;
    }

    public void setReachTimeBegin(String reachTimeBegin) {
        this.reachTimeBegin = reachTimeBegin;
    }

    public String getReachTimeEnd() {
        return reachTimeEnd;
    }

    public void setReachTimeEnd(String reachTimeEnd) {
        this.reachTimeEnd = reachTimeEnd;
    }

    public String getLeaveTimeBegin() {
        return leaveTimeBegin;
    }

    public void setLeaveTimeBegin(String leaveTimeBegin) {
        this.leaveTimeBegin = leaveTimeBegin;
    }

    public String getLeaveTimeEnd() {
        return leaveTimeEnd;
    }

    public void setLeaveTimeEnd(String leaveTimeEnd) {
        this.leaveTimeEnd = leaveTimeEnd;
    }

    public Double getMultiple() {
        return multiple;
    }

    public void setMultiple(Double multiple) {
        this.multiple = multiple;
    }

    public Double getStaticMoney() {
        return staticMoney;
    }

    public void setStaticMoney(Double staticMoney) {
        this.staticMoney = staticMoney;
    }
}
