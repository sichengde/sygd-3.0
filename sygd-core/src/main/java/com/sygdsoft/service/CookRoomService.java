package com.sygdsoft.service;

import com.sygdsoft.mapper.CookRoomMapper;
import com.sygdsoft.model.CookRoom;
import com.sygdsoft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-04.
 */
@Service
@SzMapper(id = "cookRoom")
public class CookRoomService extends BaseService<CookRoom>{
    private static final Logger logger = LoggerFactory.getLogger(CookRoomService.class);
    @Autowired
    CookRoomMapper cookRoomMapper;
    @Autowired
    Util util;
    @Autowired
    HotelService hotelService;
    /**
     * 通过厨房名获取
     */
    public List<CookRoom> getByCookName(String cookName){
        CookRoom cookRoomQuery=new CookRoom();
        cookRoomQuery.setCookName(cookName);
        return cookRoomMapper.select(cookRoomQuery);
    }

    public List<CookRoom> getByCookName(String[] cookName){
        return cookRoomMapper.getByCookNameString(util.arrayToString(cookName,true));
    }

    /**
     * 定时循环打印机httpClient，防止挂起
     */
    @Scheduled(fixedRate = 900000)
    public void checkCookRoomIp() throws Exception {
        List<CookRoom> cookRoomList=get(null);
        for (CookRoom cookRoom : cookRoomList) {
            if(cookRoom.getNotNullUPort()) {
                hotelService.postJSON("http://" + cookRoom.getPrinterIp() + "/checkPrint", "{}");
            }
        }
    }
}
