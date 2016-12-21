package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-07-26.
 */
public class Freeman extends BaseEntity{
    private String freeman;//可免单人
    private String consume;//免单金额
    private String phone;//电话
    private String remark;//备注

    public Freeman() {
    }

    public String getFreeman() {
        return freeman;
    }

    public void setFreeman(String freeman) {
        this.freeman = freeman;
    }


    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
