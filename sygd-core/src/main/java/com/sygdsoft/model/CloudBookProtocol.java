package com.sygdsoft.model;

/**
 * Created by Administrator on 2017/7/31.
 */
public class CloudBookProtocol extends BaseEntity{
    private String guestSource;
    private String protocol;

    public CloudBookProtocol() {
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
