package com.sygdsoft.service;

import com.sygdsoft.mapper.InterfaceDoorMapper;
import com.sygdsoft.model.InterfaceDoor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 舒展 on 2017-01-12.
 */
@Service
@SzMapper(id = "interfaceDoor")
public class InterfaceDoorService extends BaseService<InterfaceDoor> {
    @Autowired
    InterfaceDoorMapper interfaceDoorMapper;
    /**
     * 根据电话号返回房号
     */
    public String getRoomIdByTelId(String telId){
        InterfaceDoor interfaceDoorQuery=new InterfaceDoor();
        interfaceDoorQuery.setTelId(telId);
        InterfaceDoor interfaceDoor=interfaceDoorMapper.selectOne(interfaceDoorQuery);
        if(interfaceDoor==null){
            return "";
        } else {
          return interfaceDoor.getRoomId();
        }
    }

    /**
     * 根据房号获取门锁号
     */
    public String getDoorIdByRoomId(String roomID){
        InterfaceDoor interfaceDoorQuery=new InterfaceDoor();
        interfaceDoorQuery.setTelId(roomID);
        return interfaceDoorMapper.selectOne(interfaceDoorQuery).getDoorId();
    }
}
