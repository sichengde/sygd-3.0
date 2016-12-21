package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-03-30.
 * 房间类别实体
 */
public class RoomCategory extends BaseEntity {
    private String category;

    public RoomCategory() {
    }

    public RoomCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
