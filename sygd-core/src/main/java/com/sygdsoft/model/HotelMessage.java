package com.sygdsoft.model;

/**
 * Created by Administrator on 2017/8/2.
 * 酒店名称-辽阳宾馆
 * 联系电话-123456
 * 位置-和平区17号
 * 简介-啦啦啦啦啦
 * 餐厅-y
 * WIFI-y
 * 会议室-y
 * 健身房-y
 * 接机服务-y
 * 停车场-y
 * 游泳池-y
 * 洗衣服务-y
 * 购物-一方广场
 * 餐饮-汉堡王
 * 景点-大帅府1
 * 酒店图片-http://omzl4hqcc.bkt.clouddn.com/1.jpeg,http://omzl4hqcc.bkt.clouddn.com/2.jpeg
 * 注意事项-1.退房时间12:00.
 2.请电话确认
 */
public class HotelMessage extends BaseEntity{
    private String name;
    private String value;

    public HotelMessage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
