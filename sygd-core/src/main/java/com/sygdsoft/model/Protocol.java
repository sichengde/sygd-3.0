package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-13.
 * 协议价
 */
public class Protocol extends BaseEntity {
    private String protocol;//协议名称
    private String roomCategory;//房类
    private String roomPriceCategory;//房租方式
    private Double roomPrice;//基本房价（日租的房间价格，会议的床位价，小时房基础价）
    private String base;//基本时长
    private String step;//超时时长
    private Double stepPrice;//超时房费
    private Double maxPrice;//最高房费
    private String breakfast;//早餐
    private String special;//特殊协议，该协议只能通过选择会员或者单位来触发，不选择单位和会员时，只出现特殊协议为n的协议
    private Boolean temp;//临时协议，自定义房价时会产生

    public Protocol() {
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public String getRoomPriceCategory() {
        return roomPriceCategory;
    }

    public void setRoomPriceCategory(String roomPriceCategory) {
        this.roomPriceCategory = roomPriceCategory;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Double getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(Double stepPrice) {
        this.stepPrice = stepPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public Boolean getTemp() {
        return temp;
    }

    public void setTemp(Boolean temp) {
        this.temp = temp;
    }
}
