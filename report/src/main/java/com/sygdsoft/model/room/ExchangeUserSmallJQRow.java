package com.sygdsoft.model.room;

/**
 * Created by 舒展 on 2017-02-27.
 */
public class ExchangeUserSmallJQRow {
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private Boolean shop;//是不是房吧需要用的，给别的表用，前端不显示

    public ExchangeUserSmallJQRow() {
    }

    public Boolean getNotNullShop(){
        if(shop==null){
            return false;
        }else {
            return shop;
        }
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public Boolean getShop() {
        return shop;
    }

    public void setShop(Boolean shop) {
        this.shop = shop;
    }
}
