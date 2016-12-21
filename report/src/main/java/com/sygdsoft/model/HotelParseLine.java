package com.sygdsoft.model;

import com.sygdsoft.jsonModel.HotelParseLineRow;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class HotelParseLine {
    private List<HotelParseLineRow> hotelParseLineRowList;
    private List<HotelParseLineRow> hotelParseLineRowListHistory;

    public HotelParseLine() {
    }

    public List<HotelParseLineRow> getHotelParseLineRowList() {
        return hotelParseLineRowList;
    }

    public void setHotelParseLineRowList(List<HotelParseLineRow> hotelParseLineRowList) {
        this.hotelParseLineRowList = hotelParseLineRowList;
    }

    public List<HotelParseLineRow> getHotelParseLineRowListHistory() {
        return hotelParseLineRowListHistory;
    }

    public void setHotelParseLineRowListHistory(List<HotelParseLineRow> hotelParseLineRowListHistory) {
        this.hotelParseLineRowListHistory = hotelParseLineRowListHistory;
    }
}
