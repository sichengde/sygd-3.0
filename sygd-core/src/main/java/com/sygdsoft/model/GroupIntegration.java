package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-08.
 */
public class GroupIntegration extends BaseEntity{
    private Date reachTime;

    public GroupIntegration() {
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
    }
}
