package com.sygdsoft.model;

/**
 * Created by Administrator on 2017/7/31.
 */
public class CloudBookProtocol extends BaseEntity{
    private String web;
    private String protocol;

    public CloudBookProtocol() {
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
