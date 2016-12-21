package com.sygdsoft.mapper;

import com.sygdsoft.model.CookRoom;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-04.
 */
public interface CookRoomMapper extends MyMapper<CookRoom>{
    /**
     * 打印机名
     */
    @Select("select * from cook_room where cook_name in (${cookName})")
    @Results(value = {
            @Result(property = "cookName", column = "cook_name"),
            @Result(property = "printerIp", column = "printer_ip"),
            @Result(property = "usbPort", column = "usb_port"),
    })
    List<CookRoom> getByCookNameString(@Param("cookName") String cookName);
}
