package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.InterfaceDoor;
import com.sygdsoft.service.InterfaceDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2017-01-12.
 */
@RestController
public class InterfaceDoorController {
    @Autowired
    InterfaceDoorService interfaceDoorService;

    @RequestMapping(value = "interfaceDoorAdd")
    public void interfaceDoorAdd(@RequestBody InterfaceDoor interfaceDoor) throws Exception {
        interfaceDoorService.add(interfaceDoor);
    }

    @RequestMapping(value = "interfaceDoorDelete")
    @Transactional(rollbackFor = Exception.class)
    public void interfaceDoorDelete(@RequestBody List<InterfaceDoor> interfaceDoorList) throws Exception {
        interfaceDoorService.delete(interfaceDoorList);
    }

    @RequestMapping(value = "interfaceDoorUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void interfaceDoorUpdate(@RequestBody List<InterfaceDoor> interfaceDoorList) throws Exception {
        if (interfaceDoorList.size() > 1) {
            if (interfaceDoorList.get(0).getId().equals(interfaceDoorList.get(interfaceDoorList.size() / 2).getId())) {
                interfaceDoorService.update(interfaceDoorList.subList(0, interfaceDoorList.size() / 2));
                return;
            }
        }
        interfaceDoorService.update(interfaceDoorList);
    }

    @RequestMapping(value = "interfaceDoorGet")
    public List<InterfaceDoor> interfaceDoorGet(@RequestBody Query query) throws Exception {
        return interfaceDoorService.get(query);
    }
}
