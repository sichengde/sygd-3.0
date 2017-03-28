package com.sygdsoft.model;

/**
 * Created by 舒展 on 2017-03-28.
 */
public class CardMapCity extends BaseEntity{
    private String card;//身份证号前几位
    private String city;//城市

    public CardMapCity() {
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
