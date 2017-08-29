package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
public class CleanRoomMan extends BaseEntity{
    private String user;
    private String password;

    public CleanRoomMan() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
