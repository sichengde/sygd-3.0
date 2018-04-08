package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 * 操作员日志
 */
public class UserLog extends BaseEntity{
    private String userId;//操作员号
    private String action;//操作内容
    private String module;//操作大类，也就是模块
    private String category;//操作类别,用来检索是什么操作
    private Date doTime;//操作时间
    private String ipAddress;//ip地址
    private String keyWord;//关键字，用来弹出索引，界面上不显示，例如如果一条结账操作，那么这里就填结账序列号
    private String groupBy;//操作员分组

    public UserLog() {
    }

    public UserLog(String userId, String action, String category, Date doTime) {
        this.userId = userId;
        this.action = action;
        this.category = category;
        this.doTime = doTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
}
