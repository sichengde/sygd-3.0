package com.sygdsoft.sqlProvider;

import com.sygdsoft.util.Util;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class BookRoomSql {
    public BookRoomSql() {
    }

    public String setOpened(Map<String, Object> parameters){
        Util util=new Util();
        String bookSerial= (String) parameters.get("bookSerial");
        String roomIdList= (String) parameters.get("roomIdList");
        return "update book_room set opened=1 where book_serial="+util.wrapWithBrackets(bookSerial)+" and room_id in ("+roomIdList+")";
    }
}
