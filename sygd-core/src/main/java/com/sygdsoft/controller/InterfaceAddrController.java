package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.InterfaceAddr;
import com.sygdsoft.service.InterfaceAddrService;
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
public class InterfaceAddrController {
    @Autowired
    InterfaceAddrService interfaceAddrService;

    @RequestMapping(value = "interfaceAddrAdd")
    public void interfaceAddrAdd(@RequestBody InterfaceAddr interfaceAddr) throws Exception {
        interfaceAddrService.add(interfaceAddr);
    }

    @RequestMapping(value = "interfaceAddrDelete")
    @Transactional(rollbackFor = Exception.class)
    public void interfaceAddrDelete(@RequestBody List<InterfaceAddr> interfaceAddrList) throws Exception {
        interfaceAddrService.delete(interfaceAddrList);
    }

    @RequestMapping(value = "interfaceAddrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void interfaceAddrUpdate(@RequestBody List<InterfaceAddr> interfaceAddrList) throws Exception {
        if (interfaceAddrList.size() > 1) {
            if (interfaceAddrList.get(0).getId().equals(interfaceAddrList.get(interfaceAddrList.size() / 2).getId())) {
                interfaceAddrService.update(interfaceAddrList.subList(0, interfaceAddrList.size() / 2));
                return;
            }
        }
        interfaceAddrService.update(interfaceAddrList);
    }

    @RequestMapping(value = "interfaceAddrGet")
    public List<InterfaceAddr> interfaceAddrGet(@RequestBody Query query) throws Exception {
        return interfaceAddrService.get(query);
    }
}
