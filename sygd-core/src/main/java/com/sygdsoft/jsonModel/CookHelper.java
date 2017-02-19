package com.sygdsoft.jsonModel;

/**
 * Created by Administrator on 2017/2/19 0019.
 */
public class CookHelper {
    private String title;//营业部门/桌台/类别
    private String name;//菜品名
    private String num;//数量
    private String cooked;//做没做

    public CookHelper() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCooked() {
        return cooked;
    }

    public void setCooked(String cooked) {
        this.cooked = cooked;
    }
}
